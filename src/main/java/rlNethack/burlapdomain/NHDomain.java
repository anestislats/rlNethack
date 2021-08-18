package rlNethack.burlapdomain;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.SampleModel;

public class NHDomain extends SADomain {
	
//	int goalx = 10;
//	int goaly = 10;
	
	
	static class NHModel implements SampleModel {

		@Override
		public EnvironmentOutcome sample(State s, Action a) {
			throw new UnsupportedOperationException() ;
		}

		@Override
		public boolean terminal(State s) {
			return false;
		}
		
	}
	
//	public void setGoalLocation(int goalx, int goaly){
//		this.goalx = goalx;
//		this.goaly = goaly;
//	}
	
	
	//RewardFunction rf = new NHRewardFunction(s, this.goalx, this.goaly);
	
    
    public NHDomain() {
        super() ;
        this.addActionType(new NHActionType()) ;
        this.setModel(new NHModel());
    }

}
