package br.com.campsim.game;

import br.com.campsim.domain.Result;
import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;

public interface GameSimulator<T> {

    Result simulate(Team<T> teamA, Team<T> teamB, boolean simulateWithDrawCase, boolean printGameHistoric);

    Result simulateDrawCase(Team<T> teamA, Team<T> teamB, boolean printGameHistoric);

    default ResultList simulate(Team<T> teamA, Team<T> teamB, int bestOf, boolean simulateWithDrawCase, boolean printGameHistoric, boolean inniciateWithWin) {
        ResultList resultList = new ResultList(bestOf);

        if (inniciateWithWin)
            resultList.addResult(new Result(teamA.getName(), 0.1, teamB.getName(), 0.09, null));

        while(resultList.isNotOver()) {
            resultList.addResult(this.simulate(teamA, teamB, simulateWithDrawCase, printGameHistoric));
        }

        if (printGameHistoric)
            resultList.getContent().forEach(Result::printResult);

        return resultList;
    }
}
