package rlNethack.burlapdomain;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

public class NHRewardFunction implements RewardFunction{
	
	int goalx;
	int goaly;
	int agentx;
	int agenty;

	public NHRewardFunction(State s, int goalX, int goalY, int agentX, int agentY){
		this.goalx = goalX;
		this.goaly = goalY;
		this.agentx = agentX;
		this.agenty = agentY;
	}

	@Override
	public double reward(State s0, Action a, State s1) {
		
		
		int dx = Math.abs(agentx-goalx) ;
		int dy = Math.abs(agenty-goaly) ;

		//int ax = agentx;
		//int ay = agenty;
		
		

		
		//are they at goal location?
		if(dx + dy == 1){
			return 100.;
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>" + goalx + "," + goaly);

		System.out.println(">>>>>>>>>>>>>>>>>>>" + dx + "," + dy);

		return -1;
	}
	
	
	

}
