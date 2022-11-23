package br.com.campsim.game;

import br.com.campsim.domain.Result;
import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;

public interface GameSimulator {

    Result simulate(Team teamA, Team teamB);
    ResultList simulate(Team teamA, Team teamB, int bestOf);
}
