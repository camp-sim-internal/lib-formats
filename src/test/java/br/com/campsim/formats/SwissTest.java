package br.com.campsim.formats;

import br.com.campsim.domain.PrintResults;
import br.com.campsim.domain.Result;
import br.com.campsim.domain.Team;
import br.com.campsim.game.GameSimulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SwissTest {

    @Test
    void test1() {
        List<Team<Integer>> teams = new ArrayList<>();
        teams.add(new Team<>("t1", 1));
        teams.add(new Team<>("t2", 2));
        teams.add(new Team<>("t3", 3));
        teams.add(new Team<>("t4", 4));
        teams.add(new Team<>("t5", 5));
        teams.add(new Team<>("t6", 6));
        teams.add(new Team<>("t7", 7));
        teams.add(new Team<>("t8", 8));
        teams.add(new Team<>("t9", 9));
        teams.add(new Team<>("t10", 10));
        teams.add(new Team<>("t11", 11));
        teams.add(new Team<>("t12", 12));
        teams.add(new Team<>("t13", 13));
        teams.add(new Team<>("t14", 14));
        teams.add(new Team<>("t15", 15));
        teams.add(new Team<>("t16", 16));

        Swiss<Integer> swiss = new Swiss<>(teams, new PrintResults(true, false, true), new GameSimulatorImpl());

        for (int i = 0; i < 5; i++)
            swiss.simulateRound();

        swiss.simulateRound().forEach(x -> System.out.println(x.getName()));
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