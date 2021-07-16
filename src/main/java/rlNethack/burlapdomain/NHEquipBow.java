package rlNethack.burlapdomain;

import burlap.mdp.core.action.Action;

public class NHEquipBow implements Action {

    @Override
    public String actionName() {
        return "equipbow" ;
    }

    @Override
    public Action copy() {
        return new NHEquipBow() ;
    }

}
