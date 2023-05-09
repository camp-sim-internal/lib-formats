package br.com.campsim.domain;

import br.com.campsim.game.GameSimulator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameLeague<T> {

    private TeamLeague<T> teamA;
    private TeamLeague<T> teamB;
    private int bOx;
    private PrintResults printResults;
    private boolean simulateWithDrawCase;

    public void internalRuleWinner(GameSimulator<T> gameSimulator){
        if (bOx == 1) {
            Result result = gameSimulator.simulate(teamA, teamB, simulateWithDrawCase, printResults.isPrintGameHistoric());

            if (printResults.isPrintGameResult())
                result.printSimplifiedResult();

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
            ResultList resultList = gameSimulator.simulate(teamA, teamB, bOx, simulateWithDrawCase, printResults.isPrintGameHistoric(), false);

            if (printResults.isPrintGameResult())
                resultList.printSimplifiedResult();

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
}
