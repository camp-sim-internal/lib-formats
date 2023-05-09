package br.com.campsim.formats;

import br.com.campsim.domain.*;
import br.com.campsim.exception.InvalidNumberOfTeamsException;
import br.com.campsim.formats.interfaces.Format;
import br.com.campsim.game.GameSimulator;

import java.util.ArrayList;
import java.util.List;

import static br.com.campsim.utils.NumberUtils.isMultipleOfTwo;

public class SingleElimination<T> implements Format<T> {

    private final List<Team<T>> teams;
    private final int bOx;
    private final PrintResults printResults;
    private final GameSimulator<T> gameSimulator;
    private final List<List<Game<T>>> games;
    private int numberOfTeams;
    private int round;

    public SingleElimination(List<Team<T>> teams, int bOx, GameSimulator<T> gameSimulator, PrintResults printResults){
        if(!isMultipleOfTwo(teams.size()))
            throw new InvalidNumberOfTeamsException();

        this.printResults = printResults;
        this.teams = teams;
        this.bOx = bOx;
        this.gameSimulator = gameSimulator;
        this.numberOfTeams = teams.size();
        this.games = new ArrayList<>();
        this.round = 1;
    }

    @Override
    public List<Team<T>> simulateAll(){
       int numberOfTeams = teams.size();

       while (numberOfTeams > 1){
           if (printResults.isPrintGameHistoric() || printResults.isPrintGameResult())
               System.out.println("ROUND " + round);

            List<Game<T>> gamesRound = new ArrayList<>();
            for(int indexUp = 0, indexDown = numberOfTeams - 1; indexUp < numberOfTeams/2; indexUp ++, indexDown --)
                gamesRound.add(new Game<>(teams.get(indexUp), teams.get(indexDown), bOx, printResults, false));

            this.games.add(gamesRound);
            gamesRound.forEach(x -> x.internalRuleWinner(gameSimulator, teams));
            numberOfTeams /= 2;
            round++;
       }

       return teams;
    }

    @Override
    public List<Team<T>> simulateRound(){
        if (numberOfTeams <= 1)
            return teams;

        if (printResults.isPrintGameHistoric() || printResults.isPrintGameResult())
            System.out.println("ROUND " + round);

        List<Game<T>> gamesRound = new ArrayList<>();
        for(int indexUp = 0, indexDown = numberOfTeams - 1; indexUp < numberOfTeams/2; indexUp ++, indexDown --)
            gamesRound.add(new Game<>(teams.get(indexUp), teams.get(indexDown), bOx, printResults, false));

        this.games.add(gamesRound);
        gamesRound.forEach(x -> x.internalRuleWinner(gameSimulator, teams));
        numberOfTeams /= 2;

        round++;
        return teams;
    }
}
