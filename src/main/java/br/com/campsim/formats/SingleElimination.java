package br.com.campsim.formats;

import br.com.campsim.domain.PrintResults;
import br.com.campsim.domain.Result;
import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;
import br.com.campsim.exception.InvalidNumberOfTeamsException;
import br.com.campsim.game.GameSimulator;

import java.util.List;

import static br.com.campsim.utils.NumberUtils.isMultipleOfTwo;

public class SingleElimination<T> {

    private final List<Team<T>> teams;
    private final int bOx;
    private final PrintResults printResults;
    private final GameSimulator<T> gameSimulator;

    public SingleElimination(List<Team<T>> teams, int bOx, GameSimulator<T> gameSimulator, PrintResults printResults){
        if(!isMultipleOfTwo(teams.size()))
            throw new InvalidNumberOfTeamsException();

        this.printResults = printResults;
        this.teams = teams;
        this.bOx = bOx;
        this.gameSimulator = gameSimulator;
    }

   public List<Team<T>> simulate(){
       int numberOfTeams = teams.size();

       while (numberOfTeams > 1){
            for(int indexUp = 0, indexDown = numberOfTeams - 1; indexUp < numberOfTeams/2; indexUp ++, indexDown --)
                processGame(indexUp, indexDown);

            if (printResults.isPrintChampionshipResult())
                System.out.println("");

           numberOfTeams /= 2;
       }

       return teams;
   }

    private void processGame(int indexOne, int indexTwo){
        Team<T> teamOne = teams.get(indexOne);
        Team<T> teamTwo = teams.get(indexTwo);

        ResultList resultList = gameSimulator.simulate(teamOne, teamTwo, bOx, true, printResults.isPrintGameHistoric());
        if(!resultList.isTeamAWinner()){
            teams.set(indexOne, teamTwo);
            teams.set(indexTwo, teamOne);
        }

        if (printResults.isPrintChampionshipResult())
            resultList.printSimplifiedResult();

        if (printResults.isPrintGameResult())
            resultList.getContent().forEach(Result::printResult);
    }
}
