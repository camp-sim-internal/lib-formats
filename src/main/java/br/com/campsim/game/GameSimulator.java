package br.com.campsim.game;

import br.com.campsim.domain.Result;
import br.com.campsim.domain.ResultList;
import br.com.campsim.domain.Team;
import org.apache.commons.lang3.RandomUtils;


public class GameSimulator {

    private GameSimulator(){}

    public static Result simulate(Team teamA, Team teamB){
        return Result.builder()
                .scoreA((double) teamA.getPower() * RandomUtils.nextInt(0, 11))
                .scoreB((double) teamB.getPower() * RandomUtils.nextInt(0, 11)).build();
    }

    public static ResultList simulate(Team teamA, Team teamB, int bestOf){
        ResultList resultList = new ResultList(bestOf);

        while (resultList.isNotOver()) {
            resultList.addResult(simulate(teamA, teamB));
        }

        return resultList;
    }
}
