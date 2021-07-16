package rlNethack;

import org.projectxy.iv4xrLib.MyEnv;
import org.projectxy.iv4xrLib.NethackWrapper;
import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import eu.iv4xr.framework.mainConcepts.WorldModel;
import rlNethack.burlapdomain.NHMove;
import rlNethack.burlapdomain.NHPickup;



public class BurlapEnv4Nethack implements Environment{

	// NethackWrapper nhw;
    MyEnv nhenv ;
	
	double lastReward;
	

    BurlapEnv4Nethack(MyEnv nhenv) { 
    	this.nhenv = nhenv;
    }
    
    

      public State currentObservation() {  // State --> Burlap state
         WorldModel wom = nhenv.observe();
         return (new MyBurlapAbstractState(wom)) ;
         
      }
      
      
      

      //@Override
      public EnvironmentOutcome executeAction(Action a) {
           WorldModel wom = Observe();
           State s0 = new MyBurlapAbstractState(wom);
           
           if(a instanceof NHMove) {
               NHMove a_ = (NHMove) a ;
               nhenv.move(a_.direction) ;
           }
           else if (a instanceof NHPickup) {
               // ok so now you need to instruct NH to pickup whatever the item in the current sq
           }
           // implement other actions
           
           wom = Observe();
           
           State s1 = new MyBurlapAbstractState(wom);
           
           double reward = 10; // need to change
           
           EnvironmentOutcome eo = new EnvironmentOutcome(s0, a,s1,reward, isInTerminalState() ) ;
           
           lastReward = reward;
           return eo ;
      }
      
      
      
      
      
      public boolean isInTerminalState() {
         return false ;
      }
      
      public WorldModel Observe() {
    	  return nhenv.observe();
      }
      
      
      
	public double lastReward() {
		return lastReward;   
	}




	public void resetEnvironment() {
	    nhenv.restartGame();
		
	}

      
  }
