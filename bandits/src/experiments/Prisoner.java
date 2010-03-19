package experiments;
import domain.RNRange;
import domain.RealVector;
import bandit.BinomialBandit;
import bandit.ComposedRealBandit;
import bandit.NoisyBandit;
import bandit.PolynomialRealBandit;
import bandit.RNDistanceBandit;
import bandit.multiplayer.PrisonerBandit;
import bandit.tictactoe.FullTicTacToeBandit;
import bandit.tictactoe.TicTacToeBandit;
import bandits.MultiplayerTrial;
import algorithm.*;
import algorithm.zoom.*;
import algorithm.discrete.*;
import algorithm.xArm.*;
import java.lang.System;

public class Prisoner {

    // Just testing stuff out for now.
    public static void main(String[] args) {
        // For timing purposes, get current time.
        long startTime = System.nanoTime();
        
        PrisonerBandit prison = new PrisonerBandit();
        prison.setRewardMax(new double[] {1, 1});
        
        
        // Make algorithm
        int arms = 1000;
        Algorithm test_alg1 = new XArm(1);
        Algorithm test_alg2 = new XArm(1);

        // Run
        int num_plays = 10000;
        MultiplayerTrial test_run = new MultiplayerTrial(
                new Algorithm[] {test_alg1, test_alg2}, prison, num_plays);
        test_run.setWriteInterval(1);
        test_run.run();
        
        // Calculate timing
        long endTime = System.nanoTime();
        double seconds = (endTime - startTime) / (double) 1000000000; // ns -> s
        System.out.println("Time taken (s): " + seconds);
    }

}
