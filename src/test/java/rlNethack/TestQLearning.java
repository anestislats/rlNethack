package rlNethack;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.projectxy.iv4xrLib.MyAgentState;
import org.projectxy.iv4xrLib.MyEnv;
import org.projectxy.iv4xrLib.NethackConfiguration;
import org.projectxy.iv4xrLib.NethackWrapper;

import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.behavior.singleagent.Episode; 
import eu.iv4xr.framework.mainConcepts.TestAgent;
import rlNethack.burlapdomain.NHDomain;

public class TestQLearning {
    
   @Test
   public void test1() {
       
       NethackWrapper driver = new NethackWrapper();
       driver.launchNethack(new NethackConfiguration());
       MyEnv env_ = new MyEnv(driver);
       BurlapEnv4Nethack env = new BurlapEnv4Nethack(env_) ;
       
       QLearning qAgent = new QLearning(new NHDomain(),
               0.99, 
               new SimpleHashableStateFactory(),
               0.1,
               0.1) ;
       
       List<Episode> episodes = new ArrayList<Episode>(10) ;
       // learning 10 episides...
       for (int k=0; k<100; k++) {
           episodes.add(qAgent.runLearningEpisode(env)) ;
           env.resetEnvironment(); 
       }
       
       // have not figured out yet how to use the learned policy,..,.
       
   }

}
