package rlNethack;

import org.projectxy.iv4xrLib.MyEnv;
import org.projectxy.iv4xrLib.NethackWrapper;
import org.projectxy.iv4xrLib.NethackWrapper.Movement;

import A.B.PlayerStatus;
import A.B.Screen;
import A.B.Tile;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import eu.iv4xr.framework.mainConcepts.WorldEntity;
import eu.iv4xr.framework.mainConcepts.WorldModel;
import rlNethack.burlapdomain.NHAimWithBow;
import rlNethack.burlapdomain.NHDoNothing;
import rlNethack.burlapdomain.NHMove;
import rlNethack.burlapdomain.NHPickup;



public class BurlapEnv4Nethack implements Environment{

	// NethackWrapper nhw;
    MyEnv nhenv ;
    PlayerStatus ps; 
    Screen nethack;
    
    
	public String equippedWeap;
	public Tile[][] tiles;
	double lastReward;
	

    BurlapEnv4Nethack(MyEnv nhenv) { 
    	this.nhenv = nhenv;
    	this.nethack = nhenv.nethackUnderTest.nethack;
    	
    }
    
    
      public State currentObservation() {  				// State --> Burlap state
         WorldModel wom = nhenv.observe();
         //equippedWeap = ps.weap.toString(); 			// the equipped weapon of the player
         tiles= nethack.tiles; 							// the tiles of the map
         
         //System.out.println(">>>"+ wom.getElement(wom.agentId));
         
         equippedWeap = wom.getElement(wom.agentId).getProperty("equippedWeaponName").toString();
         
         return (new MyBurlapAbstractState(wom, equippedWeap, tiles   )) ;
         
      }
      
      
      void delay(int t) {
          try {
       	   Thread.sleep(t);
          }
          catch(Exception e) {
       	   
          }
      }
      

      //@Override
      public EnvironmentOutcome executeAction(Action a) {
    	  
    	  MyBurlapAbstractState s = (MyBurlapAbstractState) currentObservation();
//           WorldModel wom = Observe();
//           equippedWeap = wom.getElement("equippedWeaponName").toString();							// the equipped weapon of the player
//           tiles= nethack.tiles; 																	// the tiles of the map
           
    	   State s0 = s.copy();
    	   
           //State s0 = new MyBurlapAbstractState(wom, equippedWeap, tiles).copy();
           
           
           
           if(a instanceof NHMove) {
               NHMove a_ = (NHMove) a ;
               nhenv.move(a_.direction) ;
               
           }
           else if (a instanceof NHDoNothing) { 													// maybe we should include the DoNothing move into Moves
        	   
        	   nhenv.move(Movement.DONOTHING);
        	   
           }
           else if (a instanceof NHPickup) {
        	   NHPickup a_ = (NHPickup) a;
        	   //playerID = MyBurlapAbstractState.
        	   
        	   nhenv.interact("Player", null, NethackWrapper.Interact.PickupItem);						// the 2 null values are for AgentId and TargetId. 
        	  
           }
           else if (a instanceof NHAimWithBow) {
        	   
        	   nhenv.interact("Player", null, NethackWrapper.Interact.AimWithBow);
        	   
           }
           

           // putting a deliberate delay here to give time to NH to update its state
           delay(30) ;
           
           
           
           // implement other actions
           
           s = (MyBurlapAbstractState) currentObservation();
           
           State s1 = s.copy();
           
       	System.out.println(">>> executeAction is invoked: " + a) ;

           
           
           
//           wom = Observe();
//           
//           equippedWeap = wom.getElement("equippedWeaponName").toString(); 		// the equipped weapon of the player
//           tiles= nethack.tiles; 												// the tiles of the map
//           
//           State s1 = new MyBurlapAbstractState(wom, equippedWeap, tiles).copy();
//           
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
