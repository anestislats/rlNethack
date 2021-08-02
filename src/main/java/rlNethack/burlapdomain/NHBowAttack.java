package rlNethack.burlapdomain;

import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import burlap.mdp.core.action.Action;

/**
 * Representing Nethack-action attack with 
 * @author iswbprasetya
 *
 */
public class NHBowAttack implements Action {
    
    public Movement direction ;
    
    public NHBowAttack(Movement dir) { direction = dir ; } 

    @Override
    public String actionName() {
        return "shoot " + direction ;
    }

    @Override
    public Action copy() {
        return new NHBowAttack(direction) ;
    }

}
 