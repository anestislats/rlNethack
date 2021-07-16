package rlNethack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.projectxy.iv4xrLib.NethackWrapper;

import A.B.PlayerStatus;
import burlap.mdp.core.state.State;
import eu.iv4xr.framework.mainConcepts.WorldModel;



public class MyBurlapAbstractState implements State {
	
	
	 WorldModel wom ;
	 NethackWrapper nhw;
	 PlayerStatus ps;
	 
	 // Maybe need to add our exact features (?) 
	 int f1; // Come up with better names
	 int f2;
	 int f3;
	   
	 
	   public MyBurlapAbstractState(WorldModel wom) {
	       this.wom = wom ;
	       this.ps = ps; 		//maybe we need to create a ps parameter for MyBurlapAbstractState
	       
	       f1 = (int) wom.position.x ; //come up with better names
	       f2 = (int) wom.position.y ;
	       f2 = (int) ps.health ;
	   }
	
	
	
	

	public List<Object> variableKeys() {
		
		
	    int[] varKey = {f1, f2, f3};
	    
	    	    
	    List<Object> keys = new ArrayList<Object>();
	    
	    keys.add(varKey);
	    	    
	    return keys ;
	    
	}
	    
	    
	

	public Object get(Object keys) { 				//??
		
		 

     }

	
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
	
	
	

	
	public State copy() { // Not sure about this one
		
		
		try {
			return (State) wom.deepclone();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
