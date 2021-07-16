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
import burlap.mdp.core.state.State;
import eu.iv4xr.framework.mainConcepts.WorldEntity;
import eu.iv4xr.framework.mainConcepts.WorldModel;



public class MyBurlapAbstractState implements State {
	
	
	 public WorldModel wom ;
	 //NethackWrapper nhw;
	 //PlayerStatus ps;
	 
	 public MyBurlapAbstractState(WorldModel wom) {
	       this.wom = wom ;
	 }
	
	
	/**
	 * The names of the features: 
	 */
	public List<Object> variableKeys() {
	    String[] varKeys = {"x", "y", "health"}  ;	    	    
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
	        WorldModel copied = (WorldModel) in.readObject();
	        return new MyBurlapAbstractState(copied) ;
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

}
