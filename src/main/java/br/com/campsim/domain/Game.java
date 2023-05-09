package br.com.campsim.domain;

import br.com.campsim.game.GameSimulator;
import lombok.Getter;

import java.util.List;

@Getter
public class Game <T> {

    private Team<T> teamA;
    private Team<T> teamB;
    private int bOx;
    private PrintResults printResults;
    private Team<T> winner;
    private Team<T> loser;
    private boolean inniciateWithWin;
    private boolean alreadyOcurred;

    public Game(Team<T> teamA, Team<T> teamB, int bOx, PrintResults printResults) {
        construct(teamA, teamB, bOx, printResults, false);
    }

    public Game(Team<T> teamA, Team<T> teamB, int bOx, PrintResults printResults, boolean inniciateWithWin) {
        construct(teamA, teamB, bOx, printResults, inniciateWithWin);
    }

    private void construct(Team<T> teamA, Team<T> teamB, int bOx, PrintResults printResults, boolean inniciateWithWin) {
        this.bOx = bOx;
        this.teamB = teamB;
        this.teamA = teamA;
        this.winner = teamA;
        this.loser = teamB;
        this.printResults = printResults;
        this.inniciateWithWin = inniciateWithWin;
    }

    public void internalRuleWinner(GameSimulator<T> gameSimulator, List<Team<T>> teams) {
        if (alreadyOcurred)
            return;

        ResultList resultList = simulate(gameSimulator);

        if(!resultList.isTeamAWinner()){
            int indexA = teams.indexOf(teamA);
            int indexB = teams.indexOf(teamB);
            teams.set(indexA, teamB);
            teams.set(indexB, teamA);
        }
    }

    public ResultList simulate(GameSimulator<T> gameSimulator) {
        if (alreadyOcurred)
            return null;

        ResultList resultList = gameSimulator.simulate(teamA, teamB, bOx, true, printResults.isPrintGameHistoric(), inniciateWithWin);

        if (resultList.isTeamAWinner()) {
            winner = teamA;
            loser = teamB;
        } else {
            winner = teamB;
            loser = teamA;
        }

        if (printResults.isPrintGameResult())
            resultList.printSimplifiedResult();

        alreadyOcurred = true;
        return resultList;
    }
}
