package rlNethack.burlapdomain;

import java.io.Serializable;
import java.util.*;

import org.projectxy.iv4xrLib.NethackWrapper;
import org.projectxy.iv4xrLib.NethackWrapper.Interact;
import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import A.B.Bow;
import A.B.PlayerStatus;
import A.B.Screen;
import A.B.Tile;
import A.B.Wall;
import eu.iv4xr.framework.mainConcepts.WorldEntity;
import eu.iv4xr.framework.mainConcepts.WorldModel;
import eu.iv4xr.framework.spatial.Vec3;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.state.State;
import rlNethack.BurlapEnv4Nethack;
import rlNethack.MyBurlapAbstractState; 

public class NHActionType implements ActionType {
	
	
//	public WorldModel wom ;
//	NethackWrapper nhw;
//	Screen nethack;
//	PlayerStatus ps;


    public String typeName() {
        return "Nethack Actions" ;
    }

    // not sure what this does... but maybe we don't need it.
    public Action associatedAction(String strRep) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Incomplete implementation, see the code ..
     */
    public List<Action> allApplicableActions(State s) {
        
        MyBurlapAbstractState s_ = (MyBurlapAbstractState) s ;
        
        List<Action> possibleActions = new LinkedList<>() ;
        
        if(!s_.isAlive()) return possibleActions ;
        
        // Add actions that are possible when the agent is in the state s.
        
        // Wait turn for 1 move. This action is always available
        possibleActions.add(new NHDoNothing());
        
        //possibleActions.add(new NHMove(Movement.DONOTHING));
        
        
        // Unfortunately, just from the wom i can't figure out which moves would 
        // hit a wall, so i add all moves as options:
        
        if (moveUpAvailable(s_)) {
        	possibleActions.add(new NHMove(Movement.UP)) ; 
        }
        
        if (moveDownAvailable(s_)) {
        	possibleActions.add(new NHMove(Movement.DOWN)) ;
        }
        
        if (moveLeftAvailable(s_)) {
        	possibleActions.add(new NHMove(Movement.LEFT)) ;
        }
        
        if (moveRightAvailable(s_)) {
        	possibleActions.add(new NHMove(Movement.RIGHT)) ;
        }
        
        
        //possibleActions.add(new NHMove(Movement.DONOTHING)) ;
        
        

        // check if picking up an item is possible:
        if(s_.wom.elements.values().stream()
                .anyMatch(e -> ! e.id.equals(s_.wom.agentId) && e.position!=null
                               && e.position.equals(s_.wom.position))) {
            possibleActions.add(new NHPickup()) ;
        }
        
        // check if using a healing item is possible:
        // the logic below is not complete, you can complete it :)
        
        WorldEntity inv = s_.wom.getElement("Inventory") ; 	// we create  WorldEntity inv so we can check in it for the needed items (health, bow, sword,..)
        
        
        // HealthPotion
        if(inv.elements.values().stream().anyMatch(e -> e.type.equals("HealthPotion"))	||
        		inv.elements.values().stream().anyMatch(e -> e.type.equals("Food"))	||
        		inv.elements.values().stream().anyMatch(e -> e.type.equals("Water"))) {
            possibleActions.add(new NHUseHeal()) ;
        }
        
	//        // Food
	//        if(inv.elements.values().stream().anyMatch(e -> e.type.equals("Food"))) {	// Implement NHUseFood in the domain
	//            possibleActions.add(new NHUseHeal()) ;
	//        }
	//        
	//        
	//        // Water
	//        if(inv.elements.values().stream().anyMatch(e -> e.type.equals("Water"))) { // // Implement NHUseWater in the domain
	//            possibleActions.add(new NHUseHeal()) ;
	//        }
        
        
        
        // you can do something similar to check if other actions can be added
        // ..
        // ..
        
        //WorldEntity inv = s_.wom.getElement("Inventory") ;
        
        // Equip Bow
        if(inv.elements.values().stream().anyMatch(e -> e.type.equals("Bow"))) {
            possibleActions.add(new NHEquipBow()) ;
        }
        
        // Aim with Bow
        String equippedWeap = s_.wom.getElement(s_.wom.agentId).getProperty("equippedWeaponName").toString();
        if(equippedWeap.contains("Bow")) {
            possibleActions.add(new NHAimWithBow()) ;
        }
     
        
        
        // Sword
        if(inv.elements.values().stream().anyMatch(e -> e.type.equals("Sword"))) {
            possibleActions.add(new NHEquipMeleeWeapon()) ;
        }
        
        
        // Attack with Bow
        
        
        Serializable weap = s_.wom.getElement(s_.wom.agentId).getProperty("equippedWeaponName");
        
        
        if ( equippedWeap.contains("Bow") ) {							//find out a way to choose the direction
        	
        	possibleActions.add(new NHBowAttack(Movement.UP));
        	possibleActions.add(new NHBowAttack(Movement.DOWN));
        	possibleActions.add(new NHBowAttack(Movement.LEFT));
        	possibleActions.add(new NHBowAttack(Movement.RIGHT));
        	
        	
        }
        
        
        
        
      
        return possibleActions ;
    }
    
    
    
    
    public boolean moveUpAvailable(MyBurlapAbstractState s_ ){
    	
    	int ax = (int) s_.wom.position.x; 	//agent's x position
    	int ay = (int) s_.wom.position.y; 	//agent's y position		
    	
    	
    	if (s_.tiles[ax][ay-1] instanceof Wall) { //if the tile above agent is wall then move up is not an available move, else it is doable
    		
    		return false;
    		
    	}
    	else {
    		
    		return true;
    		
    	}
    	
    	
    }
    
    
    
	public boolean moveDownAvailable(MyBurlapAbstractState s_){
	    	
	    	int ax = (int) s_.wom.position.x; 	//agent's x position
	    	int ay = (int) s_.wom.position.y; 	//agent's y position		
	    	
	    	
	    	if (s_.tiles[ax][ay+1] instanceof Wall) { //if the tile below agent is wall then move up is not an available move, else it is doable
	    		
	    		return false;
	    		
	    	}
	    	else {
	    		
	    		return true;
	    		
	    	}
	    	
	    	
	    }
	
	
	
	
	public boolean moveLeftAvailable(MyBurlapAbstractState s_){
	    	
	    	int ax = (int) s_.wom.position.x; 	//agent's x position
	    	int ay = (int) s_.wom.position.y; 	//agent's y position		
	    	
	    	
	    	if (s_.tiles[ax-1][ay] instanceof Wall) { //if the tile left of the agent is wall then move up is not an available move, else it is doable
	    		
	    		return false;
	    		
	    	}
	    	else {
	    		
	    		return true;
	    		
	    	}
	    	
	    	
	    }
	
	
	
	public boolean moveRightAvailable(MyBurlapAbstractState s_){
		
		int ax = (int) s_.wom.position.x; 	//agent's x position
		int ay = (int) s_.wom.position.y; 	//agent's y position		
		
		
		if (s_.tiles[ax+1][ay] instanceof Wall) { //if the tile right of the agent is wall then move up is not an available move, else it is doable
			
			return false;
			
		}
		else {
			
			return true;
			
		}
		
		
	}

}
