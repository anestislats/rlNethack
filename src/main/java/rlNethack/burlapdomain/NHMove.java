package rlNethack.burlapdomain;

import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import burlap.mdp.core.action.Action;

/**
 * Representing Nethack-actions "move", to 4 possible directions.
 */

public class NHMove implements Action {
    
    public Movement direction ;
    
    public NHMove(Movement dir) { direction = dir ; }

    public String actionName() {
        return "move " + direction ;
    }

    public Action copy() {
        return new NHMove(direction) ; 
    }

}
