package br.com.campsim.formats;

import br.com.campsim.domain.PrintResults;
import br.com.campsim.domain.Result;
import br.com.campsim.domain.Team;
import br.com.campsim.game.GameSimulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DoubleEliminationMinortTest {

    @Test
    void test1() {
        List<Team<Integer>> teams = new ArrayList<>();
        teams.add(new Team<>("PAIN", 1));
        teams.add(new Team<>("LOUD", 2));
        teams.add(new Team<>("RED", 3));
        teams.add(new Team<>("INTZ", 4));
        teams.add(new Team<>("FLUXO", 5));
        teams.add(new Team<>("KEYD", 6));

        DoubleEliminationMinor<Integer> doubleElimination = new DoubleEliminationMinor<>(teams, 3, new PrintResults(true, true, true), new GameSimulatorImpl(), 5);
        for (int i = 0; i < 6; i++) {
            doubleElimination.simulateRound();
        }
        doubleElimination.simulateRound().forEach(x -> System.out.println(x.getName()));
    }


    public static class GameSimulatorImpl implements GameSimulator<Integer> {

        @Override
        public Result simulate(Team<Integer> teamA, Team<Integer> teamB, boolean simulateWithDrawCase, boolean printGameHistoric) {
            return new Result(teamA.getName(), teamA.getPower().doubleValue(), teamB.getName(), teamB.getPower().doubleValue(),  null);
        }

        @Override
        public Result simulateDrawCase(Team<Integer> teamA, Team<Integer> teamB, boolean printGameHistoric) {
            return null;
        }
    }
}