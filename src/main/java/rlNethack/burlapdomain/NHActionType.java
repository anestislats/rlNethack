package rlNethack.burlapdomain;

import java.util.*;

import org.projectxy.iv4xrLib.NethackWrapper.Interact;
import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import eu.iv4xr.framework.mainConcepts.WorldEntity;
import eu.iv4xr.framework.mainConcepts.WorldModel;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.state.State;

import rlNethack.MyBurlapAbstractState; 

public class NHActionType implements ActionType {

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
        // Unfortunately, just from the wom i can't figure out which moves would 
        // hit a wall, so i add all moves as options:
        possibleActions.add(new NHMove(Movement.UP)) ;
        possibleActions.add(new NHMove(Movement.DOWN)) ;
        possibleActions.add(new NHMove(Movement.LEFT)) ;
        possibleActions.add(new NHMove(Movement.RIGHT)) ;
        

        // check if picking up an item is possible:
        if(s_.wom.elements.values().stream()
                .anyMatch(e -> ! e.id.equals(s_.wom.agentId) 
                               && e.position.equals(s_.wom.position))) {
            possibleActions.add(new NHPickup()) ;
        }
        
        // check if using a healing item is possible:
        // the logic below is not complete, you can complete it :)
        WorldEntity inv = s_.wom.getElement("Inventory") ;
        if(inv.elements.values().stream().anyMatch(e -> e.type.equals("HealthPotion"))) {
            possibleActions.add(new NHUseHeal()) ;
        }
        
        // you can do something similar to check if other actions can be added
        // ..
        // ..
      
        return possibleActions ;
    }

}
