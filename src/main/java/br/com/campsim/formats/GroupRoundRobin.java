package br.com.campsim.formats;

import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;
import br.com.campsim.domain.TeamLeague;
import br.com.campsim.game.GameSimulator;
import br.com.campsim.game.impl.GameSimulatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupRoundRobin {

    private final List<TeamLeague> teamLeagues;

    private final int bOx;
    private final int rounds;
    private final GameSimulator gameSimulator;

    public GroupRoundRobin(List<Team> teams, int bOx, int rounds){
        this.teamLeagues = new ArrayList<>();
        teams.forEach(team -> teamLeagues.add(new TeamLeague(team)));

        this.bOx = bOx;
        this.rounds = rounds;
        this.gameSimulator = new GameSimulatorImpl();
    }

    public List<Team> simulate(){

        for(int r = 0; r < rounds; r++){

            for(int casa = 0; casa < teamLeagues.size(); casa++){
                for(int fora = casa + 1; fora < teamLeagues.size(); fora++)
                    internalRuleWinner(teamLeagues.get(casa), teamLeagues.get(fora));
            }
        }
        Collections.sort(teamLeagues);

        return new ArrayList<>(teamLeagues);
    }

    public List<TeamLeague> getTeamLeagues(){
        return this.teamLeagues;
    }

    private void internalRuleWinner(TeamLeague teamA, TeamLeague teamB){
        ResultList resultList = gameSimulator.simulate(teamA, teamB, bOx);

        if(resultList.isTeamAWinner()){
            teamA.winner(resultList.getAllScore(true));
        }
        else{
            teamB.winner(resultList.getAllScore(false));
        }
    }
}
