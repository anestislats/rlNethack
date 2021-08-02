package rlNethack.burlapdomain;

import org.projectxy.iv4xrLib.NethackWrapper.Interact;

import burlap.mdp.core.action.Action;

public class NHUseWater implements Action {

	
	public String actionName() {
        return "drink water" ;
    }

    public Action copy() {
        return new NHUseWater() ;
    }
	
	
}
