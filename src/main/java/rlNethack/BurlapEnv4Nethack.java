package rlNethack;

import org.projectxy.iv4xrLib.NethackWrapper;
import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import eu.iv4xr.framework.mainConcepts.WorldModel;






public class BurlapEnv4Nethack implements Environment{

	NethackWrapper nhw;
	
	double lastReward;
	

    BurlapEnv4Nethack(NethackWrapper nhw) { 
    	
    	this.nhw = nhw;
    	
    }
    
    

    
      //@Override
      public State currentObservation() {  // State --> Burlap state
         WorldModel wom = nhw.observe();
         return (new MyBurlapAbstractState(wom)) ;
         
      }
      
      
      

      //@Override
      public EnvironmentOutcome executeAction(Action a) {
           WorldModel wom = Observe();
           
           
           State s0 = new MyBurlapAbstractState(wom);
           
           // decide how to interpret a
           
           Action a1 = (Action) nhw.move(Movement.UP) ; // need to create actions in a right way
           Action a2 = (Action) nhw.move(Movement.DOWN) ;
           Action a3 = (Action) nhw.move(Movement.RIGHT) ;
           Action a4 = (Action) nhw.move(Movement.LEFT) ;
           
           // need to create more actions than movements 
           
           
           
           wom = Observe();
           
           State s1 = new MyBurlapAbstractState(wom);
           
           double reward = 10; // need to change
           
           EnvironmentOutcome eo = new EnvironmentOutcome(s0, a1,s1,reward, isInTerminalState() ) ;
           
           lastReward = reward;
           return eo ;
      }
      
      
      
      
      
      public boolean isInTerminalState() {
         return false ;
      }
      
      public WorldModel Observe() {
    	  return nhw.observe();
      }
      
      
      
	public double lastReward() {
		
		return lastReward;   
	}




	public void resetEnvironment() {
		nhw.restartGame();
		
	}

      
  }
