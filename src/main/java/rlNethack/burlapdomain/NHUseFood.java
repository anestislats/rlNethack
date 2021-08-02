package rlNethack.burlapdomain;

import org.projectxy.iv4xrLib.NethackWrapper.Interact;

import burlap.mdp.core.action.Action;

public class NHUseFood implements Action {
	
	
	public String actionName() {
        return "eat food" ;
    }

    public Action copy() {
        return new NHUseFood() ;
    }
	

}
