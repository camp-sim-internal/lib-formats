package br.com.campsim.formats;

import br.com.campsim.domain.Game;
import br.com.campsim.domain.PrintResults;
import br.com.campsim.domain.Team;
import br.com.campsim.formats.interfaces.Format;
import br.com.campsim.game.GameSimulator;

import java.util.*;

public class DoubleElimination<T> implements Format<T> {

    private final List<Team<T>> teamsClassification;
    private final List<Team<T>> teams;
    private final Map<Integer, List<Game<T>>> games;
    private final GameSimulator<T> gameSimulator;
    private final PrintResults printResults;
    private final int bOx;
    private final int bOxFinal;
    private int round;

    public DoubleElimination(List<Team<T>> teams, int bOx, PrintResults printResults, GameSimulator<T> gameSimulator, int bOxFinal) {
        this.teamsClassification = new ArrayList<>();
        this.teams = teams;
        this.gameSimulator = gameSimulator;
        this.bOx = bOx;
        this.round = 1;
        this.bOxFinal = bOxFinal;
        this.printResults = printResults;
        this.games = new HashMap<>();
    }

    @Override
    public List<Team<T>> simulateAll() {
        if (round == 7)
            return teamsClassification;

        for (int i = 0; i < 7; i++)
            simulateRound();

        return teamsClassification;
    }

    @Override
    public List<Team<T>> simulateRound() {
        if (round == 7)
            return teamsClassification;

        if (printResults.isPrintGameHistoric() || printResults.isPrintGameResult())
            System.out.println("ROUND " + round);

        List<Game<T>> roundGames = new ArrayList<>();
        switch (round) {
            case 1 -> {
                roundGames.add(new Game<>(teams.get(0), teams.get(7), bOx, printResults));
                roundGames.add(new Game<>(teams.get(1), teams.get(6), bOx, printResults));
                roundGames.add(new Game<>(teams.get(2), teams.get(5), bOx, printResults));
                roundGames.add(new Game<>(teams.get(3), teams.get(4), bOx, printResults));
            }
            case 2 -> {
                roundGames.add(new Game<>(games.get(1).get(0).getLoser(), games.get(1).get(1).getLoser(), bOx, printResults));
                roundGames.add(new Game<>(games.get(1).get(2).getLoser(), games.get(1).get(3).getLoser(), bOx, printResults));
                roundGames.add(new Game<>(games.get(1).get(0).getWinner(), games.get(1).get(1).getWinner(), bOx, printResults));
                roundGames.add(new Game<>(games.get(1).get(2).getWinner(), games.get(1).get(3).getWinner(), bOx, printResults));
            }
            case 3 -> {
                roundGames.add(new Game<>(games.get(2).get(3).getLoser(), games.get(2).get(0).getWinner(), bOx, printResults));
                roundGames.add(new Game<>(games.get(2).get(2).getLoser(), games.get(2).get(1).getWinner(), bOx, printResults));
                roundGames.add(new Game<>(games.get(2).get(2).getWinner(), games.get(2).get(3).getWinner(), bOx, printResults));
            }
            case 4 -> roundGames.add(new Game<>(games.get(3).get(0).getWinner(), games.get(3).get(1).getWinner(), bOx, printResults));
            case 5 -> roundGames.add(new Game<>(games.get(3).get(2).getLoser(), games.get(4).get(0).getWinner(), bOx, printResults));
            case 6 -> roundGames.add(new Game<>(games.get(3).get(2).getWinner(), games.get(5).get(0).getWinner(), bOxFinal, printResults, true));
        }

        roundGames.forEach(x -> x.simulate(gameSimulator));
        games.put(round, roundGames);

        for (int i = 0; round != 1 && i < Math.ceil(roundGames.size()/2.0); i++)
            teamsClassification.add(0, roundGames.get(i).getLoser());

        if (round == 6)
            teamsClassification.add(0, roundGames.get(0).getWinner());

        round++;
        return teamsClassification;
    }

}
