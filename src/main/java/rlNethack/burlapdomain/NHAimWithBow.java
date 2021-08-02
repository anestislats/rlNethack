package rlNethack.burlapdomain;

import burlap.mdp.core.action.Action;

public class NHAimWithBow implements Action {
	
	@Override
    public String actionName() {
        return "AimWithBow" ;
    }

    @Override
    public Action copy() {
        return new NHAimWithBow() ;
    }
	
	

}
