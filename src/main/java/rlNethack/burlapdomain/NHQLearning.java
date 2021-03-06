package rlNethack.burlapdomain;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.options.EnvironmentOptionOutcome;
import burlap.behavior.singleagent.options.Option;
import burlap.behavior.valuefunction.ConstantValueFunction;
import burlap.behavior.valuefunction.QFunction;
import burlap.behavior.valuefunction.QValue;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.statehashing.HashableState;
import burlap.statehashing.HashableStateFactory;

public class NHQLearning extends QLearning {
	
	
	public NHQLearning (SADomain domain, double gamma, HashableStateFactory hashingFactory,
			double qInit, double learningRate) {
		this.QLInit (domain, gamma, hashingFactory, new ConstantValueFunction(qInit), learningRate, new EpsilonGreedy(this, 0.1), Integer.MAX_VALUE);
	}


	
	
	
	@Override
	public Episode runLearningEpisode(Environment env, int maxSteps) {

		State initialState = env.currentObservation();

		Episode ea = new Episode(initialState);
		HashableState curState = this.stateHash(initialState);
		eStepCounter = 0;

		maxQChangeInLastEpisode = 0.;
		while(!env.isInTerminalState() && (eStepCounter < maxSteps || maxSteps == -1)){

			Action action = learningPolicy.action(curState.s());
			QValue curQ = this.getQ(curState, action);



			EnvironmentOutcome eo;
			if(!(action instanceof Option)){
				eo = env.executeAction(action);
			}
			else{
				eo = ((Option)action).control(env, this.gamma);
			}



			HashableState nextState = this.stateHash(eo.op);
			double maxQ = 0.;

			if(!eo.terminated){
				maxQ = this.getMaxQ(nextState);
			}

			//manage option specifics
			double r = eo.r;
			double discount = eo instanceof EnvironmentOptionOutcome ? ((EnvironmentOptionOutcome)eo).discount : this.gamma;
			int stepInc = eo instanceof EnvironmentOptionOutcome ? ((EnvironmentOptionOutcome)eo).numSteps() : 1;
			eStepCounter += stepInc;

			if(!(action instanceof Option) || !this.shouldDecomposeOptions){
				ea.transition(action, nextState.s(), r);
			}
			else{
				ea.appendAndMergeEpisodeAnalysis(((EnvironmentOptionOutcome)eo).episode);
			}



			double oldQ = curQ.q;

			//update Q-value
			curQ.q = curQ.q + this.learningRate.pollLearningRate(this.totalNumberOfSteps, curState.s(), action) * (r + (discount * maxQ) - curQ.q);

			double deltaQ = Math.abs(oldQ - curQ.q);
			if(deltaQ > maxQChangeInLastEpisode){
				maxQChangeInLastEpisode = deltaQ;
			}

			//move on polling environment for its current state in case it changed during processing
			curState = this.stateHash(env.currentObservation());
			this.totalNumberOfSteps++;


		}


		return ea;

	}
	
	
	
	

}
