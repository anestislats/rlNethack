package rlNethack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
	    String[] varKeys = {"x", "y", "health", "equippedWeapon", "tileMap"}  ;	    	    
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
		    // deep cloning the wom:
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(bos);
	        out.writeObject(wom);
	        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
	        ObjectInputStream in = new ObjectInputStream(bis);
	       
	        
	        WorldModel womCopied = (WorldModel) in.readObject();
	        String equippedWeapCopied = cloneEquippedWeapon(equippedWeap);
	        Tile[][] tilesCopied = cloneMap(tiles);
	        
	        //Tile[][] tilesCopied = tiles.clone();
	        
	        return new MyBurlapAbstractState(womCopied, equippedWeapCopied, tilesCopied) ;							// Add equippedWeap and tiles
		}
		catch(Exception e) {
		    return null ;
		}
	}
	
	
	
	
	
	public Tile[][] cloneMap(Tile[][] map) {
		try {
			//clone the tile map
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ObjectOutputStream out = new ObjectOutputStream(bos);
		    out.writeObject(map);
		    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		    ObjectInputStream in = new ObjectInputStream(bis);
		    Tile[][] mapCopied = (Tile[][]) in.readObject();
	
		    return mapCopied;
		}
		catch(Exception e) {
		    return null ;
		}

	}
	
	
	public String cloneEquippedWeapon(String equippedWeap) {
		try {
			//clone the equipped Weapon
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ObjectOutputStream out = new ObjectOutputStream(bos);
		    out.writeObject(equippedWeap);
		    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		    ObjectInputStream in = new ObjectInputStream(bis);
		    String equippedWeapCopied = (String) in.readObject();
	
		    return equippedWeapCopied;
		}
		catch(Exception e) {
		    return null ;
		}

		
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
