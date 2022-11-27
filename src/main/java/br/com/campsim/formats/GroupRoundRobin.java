package br.com.campsim.formats;

import br.com.campsim.domain.*;
import br.com.campsim.exception.InvalidConditionToRunException;
import br.com.campsim.game.GameSimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupRoundRobin {

    private final List<TeamLeague> teamLeagues;

    private final int bOx;
    private final int rounds;
    private final boolean simulateWithDrawCase;
    private final PrintResults printResults;
    private final GameSimulator gameSimulator;


    public GroupRoundRobin(List<Team> teams, int bOx, int rounds, GameSimulator gameSimulator, boolean simulateWithDrawCase, PrintResults printResults){
        if (bOx > 1 && !simulateWithDrawCase)
            throw new InvalidConditionToRunException();

        this.teamLeagues = new ArrayList<>();
        teams.forEach(team -> teamLeagues.add(new TeamLeague(team)));

        this.printResults = printResults;
        this.simulateWithDrawCase = simulateWithDrawCase;
        this.bOx = bOx;
        this.rounds = rounds;
        this.gameSimulator = gameSimulator;
    }

    public List<Team> simulate(){

        for(int r = 0; r < rounds; r++){

            for(int casa = 0; casa < teamLeagues.size(); casa++){
                for(int fora = casa + 1; fora < teamLeagues.size(); fora++)
                    internalRuleWinner(teamLeagues.get(casa), teamLeagues.get(fora));
            }
        }
        Collections.sort(teamLeagues);
        printTable();

        return new ArrayList<>(teamLeagues);
    }

    public List<TeamLeague> getTeamLeagues(){
        return this.teamLeagues;
    }

    private void internalRuleWinner(TeamLeague teamA, TeamLeague teamB){
        if (bOx == 1) {
            Result result = gameSimulator.simulate(teamA, teamB, simulateWithDrawCase, printResults.isPrintGameHistoric());
            printResult(result);

            if (result.isAWinner()) {
                teamA.winner(result.getScoreA() - result.getScoreB());
                teamB.loser(result.getScoreB() - result.getScoreA());
            }
            else if (result.isBWinner()) {
                teamB.winner(result.getScoreB() - result.getScoreA());
                teamA.loser(result.getScoreA() - result.getScoreB());
            } else {
                teamA.draw();
                teamB.draw();
            }

        }  else {
            ResultList resultList = gameSimulator.simulate(teamA, teamB, bOx, simulateWithDrawCase, printResults.isPrintGameHistoric());
            resultList.getContent().forEach(this::printResult);

            if(resultList.isTeamAWinner()){
                teamA.winner(resultList.getAllDifferenceScoreAtA());
                teamB.loser(resultList.getAllDifferenceScoreAtB());
            }
            else{
                teamB.winner(resultList.getAllDifferenceScoreAtB());
                teamA.loser(resultList.getAllDifferenceScoreAtA());
            }
        }
    }

    private void printResult(Result result) {
        if (printResults.isPrintGameResult()) {
            result.printResult();
        }
    }

    private void printTable() {
        if (printResults.isPrintChampionshipResult()) {
            System.out.println("NOME\t|P| V E D\tdes");
            teamLeagues.forEach(x -> System.out.println(x.getLineTable()));
        }
    }
}
