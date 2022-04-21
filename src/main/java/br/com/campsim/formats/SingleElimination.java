package br.com.campsim.formats;

import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;
import br.com.campsim.exception.InvalidNumberOfTeamsException;
import br.com.campsim.game.GameSimulator;

import java.util.List;

import static br.com.campsim.utils.NumberUtils.isMultipleOfTwo;

public class SingleElimination {

    private final List<Team> teams;
    private final int bOx;

    public SingleElimination(List<Team> teams, int bOx){
        if(!isMultipleOfTwo(teams.size()))
            throw new InvalidNumberOfTeamsException();

        this.teams = teams;
        this.bOx = bOx;
    }

   public List<Team> simulate(){
       int numberOfTeams = teams.size();

       while (numberOfTeams > 1){

            for(int indexUp = 0, indexDown = numberOfTeams - 1; indexUp < numberOfTeams; indexUp ++, indexDown ++)
                processGame(indexUp, indexDown);

           numberOfTeams /= 2;
       }

       return teams;
   }

    private void processGame(int indexOne, int indexTwo){
        Team teamOne = teams.get(indexOne);
        Team teamTwo = teams.get(indexTwo);

        if(!GameSimulator.simulate(teamOne, teamTwo, bOx).isTeamAWinner()){
            teams.set(indexOne, teamTwo);
            teams.set(indexTwo, teamOne);
        }
    }
}
