package rlNethack.burlapdomain;

import burlap.mdp.core.action.Action;

public class NHEquipMeleeWeapon implements Action {

    @Override
    public String actionName() {
        return "equipmelee" ;
    }

    @Override
    public Action copy() {
        return new NHEquipMeleeWeapon() ;
    }

}
