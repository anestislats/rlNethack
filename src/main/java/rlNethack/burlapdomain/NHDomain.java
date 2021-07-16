package rlNethack.burlapdomain;

import burlap.mdp.singleagent.SADomain;

public class NHDomain extends SADomain {
    
    public NHDomain() {
        super() ;
        this.addActionType(new NHActionType()) ;
    }

}
