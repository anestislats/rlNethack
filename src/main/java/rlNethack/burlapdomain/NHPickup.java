package rlNethack.burlapdomain;

import burlap.mdp.core.action.Action;

/**
 * Representing the Nethack action to pickup the item in the current location.
 */

public class NHPickup implements Action {
    
    @Override
    public String actionName() {
        return "pickup" ;
    }

    @Override
    public Action copy() {
        return new NHPickup() ;
    }

}
