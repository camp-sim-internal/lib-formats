package br.com.campsim.game;

import br.com.campsim.domain.Result;
import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;

public interface GameSimulator<T> {

    Result simulate(Team<T> teamA, Team<T> teamB, boolean simulateWithDrawCase, boolean printGameHistoric);
    ResultList simulate(Team<T> teamA, Team<T> teamB, int bestOf, boolean simulateWithDrawCase, boolean printGameHistoric);

    Result simulateDrawCase(Team<T> teamA, Team<T> teamB, boolean printGameHistoric);
}
