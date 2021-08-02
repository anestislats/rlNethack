package rlNethack.burlapdomain;


import org.projectxy.iv4xrLib.NethackWrapper.Interact;

import burlap.mdp.core.action.Action;


public class NHDoNothing implements Action {

	public String actionName() {
        return "wait for 1 move" ;
    }

    public Action copy() {
        return new NHDoNothing() ;
    }
	
	
	
}
