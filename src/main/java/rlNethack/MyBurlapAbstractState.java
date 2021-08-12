package rlNethack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
//import java.util.Vector;

import org.projectxy.iv4xrLib.NethackWrapper;

import A.B.PlayerStatus;
import A.B.Tile;
import burlap.mdp.core.state.State;
import eu.iv4xr.framework.mainConcepts.WorldEntity;
import eu.iv4xr.framework.mainConcepts.WorldModel;



public class MyBurlapAbstractState implements State {
	
	
	 public WorldModel wom ;
	 //public WorldEntity equippedWeap = wom.getElement("equippedWeaponName");
	 public Tile[][] tiles;
	 public String equippedWeap;
	 
	 
	 //NethackWrapper nhw;
	 //PlayerStatus ps;
	 
	 public MyBurlapAbstractState(WorldModel wom, String equippedWeap, Tile[][] tiles) {
	       this.wom = wom ;
	       this.equippedWeap = equippedWeap;
	       //this.equippedWeap = equippedWeap;
	       this.tiles = tiles;
	 }
	
	
	/**
	 * The names of the features: 
	 */
	public List<Object> variableKeys() {
	    String[] varKeys = {"x", "y", "health", "equippedWeapon", "tileMap", "currentLevel", "stairPositionX", "stairPositionY", "xDistanceFromStair", "yDistanceFromStair"}  ;	    	    
	    List<Object> keys = new ArrayList<Object>();
	    for(int k=0; k<varKeys.length; k++) keys.add(varKeys[k]) ;
	    return keys ;
	}
	    
	    
	/**
	 * Getting the features value:
	 */
	public Object get(Object key) {
	    switch((String) key) {
	       case "x" : return wom.position.x ;
	       case "y" : return wom.position.y ;
	       case "health" : return getPlayer().getProperty("health") ;
	       case "equippedWeapon" : return equippedWeap;
	       case "tileMap" : return tiles;
	       case "currentLevel" : return getPlayer().getIntProperty("currentLevel");
	       
	       case "stairPositionX" :{
	    	   WorldEntity stairs = wom.getElement("Stairs") ;
	    	   return stairs.position.x;
	       }
	       
	       case "stairPositionY" :{
	    	   WorldEntity stairs = wom.getElement("Stairs") ;
	    	   return stairs.position.y;
	       }
	       
	       case "xDistanceFromStair":{
	    	   
	    	   WorldEntity stairs = wom.getElement("Stairs") ;
	    	   int stairX = (int) stairs.position.x;
	    	   int agentX = (int) wom.position.x;
	    	   
	    	   int dx = Math.abs(agentX - stairX);
	    	   
	    	   return dx;
	       }
	       case "yDistanceFromStair":{
	    	   
	    	   WorldEntity stairs = wom.getElement("Stairs") ;
	    	   int stairY = (int) stairs.position.y;
	    	   int agentY = (int) wom.position.y;
	    	   
	    	   int dy = Math.abs(agentY - stairY);
	    	   
	    	   return dy;
	       }
	       
	       
	    }
	    throw new IllegalArgumentException() ;
     }

	/* maybe we don't have to implement this; i think Burlap will only keep track
	  the features set above.
	  
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector other = (Vector) obj;
        if (!Arrays.equals(values, other.values))
            return false;
        return true;

    }
	*/
	
	
	
	
	public State copy() { // Not sure about this one
		try {
			WorldModel womCopied = (WorldModel) cloneIt(wom) ;
		    String equippedWeapCopied = "" + equippedWeap ;
	        Tile[][] tilesCopied = (Tile[][]) cloneIt(tiles); 
	        return new MyBurlapAbstractState(womCopied, equippedWeapCopied, tilesCopied) ;							// Add equippedWeap and tiles
		}
		catch(Exception e) {
			e.printStackTrace();;
		    return null ;
		}
	}
	
	
	static public Object cloneIt(WorldModel wom) throws ClassNotFoundException, IOException {
		WorldModel wom2 = new WorldModel() ;
		wom2.agentId = "" + wom.agentId ;
		wom2.timestamp = 0 + wom.timestamp ;
		if (wom.position!=null) wom2.position = wom.position.copy() ;
		if (wom.extent != null) wom2.extent = wom.extent.copy() ;
		if (wom.velocity != null) wom2.velocity = wom.velocity.copy() ;
		for(var e : wom.elements.entrySet()) {
			wom2.elements.put(e.getKey(),(WorldEntity) cloneIt(e.getValue())) ;
		}
		return wom2 ;
	}
	
	static public Object cloneIt(WorldEntity e) throws IOException, ClassNotFoundException {
		
		WorldEntity e2 = new WorldEntity(e.id,e.type,e.dynamic) ;
		if(e.position != null) e2.position = e.position.copy() ;
		if(e.extent != null) e2.extent = e.extent.copy() ;
		
		Object[] props = new Object[2*e.properties.size()] ;
		int i=0 ;
		for(var p : e.properties.entrySet()) {
			props[i] = p.getKey() ;
			props[i+1] = p.getValue() ;
			i += 2 ;
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(props);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream in = new ObjectInputStream(bis);		
		Object[] props_ = (Object[]) in.readObject();

		i = 0 ;
		while (i < props_.length) {
			e2.properties.put((String) props_[i], (Serializable) props_[i+1]) ;
			i += 2 ;
		}
		
		for (var f : e.elements.entrySet()) {
			e2.elements.put(f.getKey(), (WorldEntity) cloneIt(f.getValue())) ;
		}
		return e2 ;		
	}
	
	
	public Object cloneIt(Tile[][] map) throws IOException, ClassNotFoundException {
		// clone the tile map
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(map);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream in = new ObjectInputStream(bis);
		Tile[][] mapCopied = (Tile[][]) in.readObject();
		return mapCopied;
	}
	

	
	
	
	public WorldEntity getPlayer() {
	    return wom.getElement(wom.agentId) ; 
	}
	
	public boolean isAlive() {
        WorldEntity playerStatus = wom.getElement(wom.agentId) ;
        return playerStatus.getBooleanProperty("isAlive") ;        
    }
	
	
	public boolean isAiming() {
        WorldEntity playerStatus = wom.getElement(wom.agentId) ;
        return playerStatus.getBooleanProperty("aimingBow") ;        
    }
	

}
