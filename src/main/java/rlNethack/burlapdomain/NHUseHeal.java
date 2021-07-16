package rlNethack.burlapdomain;

import org.projectxy.iv4xrLib.NethackWrapper.Interact;

import burlap.mdp.core.action.Action;

/**
 * Representing Nethack-action using a healing-item.
 */
public class NHUseHeal implements Action {
    
    
    public String actionName() {
        return "heal" ;
    }

    public Action copy() {
        return new NHUseHeal() ;
    }

}
