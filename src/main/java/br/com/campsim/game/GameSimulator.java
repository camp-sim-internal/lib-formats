package br.com.campsim.game;

import br.com.campsim.domain.Result;
import br.com.campsim.domain.Team;

import java.util.ArrayList;
import java.util.List;

public class GameSimulator {

    private GameSimulator(){}

    public static Result simulate(Team teamA, Team teamB){
        return Result.builder()
                .scoreA((double) teamA.getPower())
                .scoreB((double) teamB.getPower()).build();
    }

    public static List<Result> simulate(Team teamA, Team teamB, int bestOf){
        List<Result> resultList = new ArrayList<>();

        while (isNotOver(resultList, bestOf)){
            resultList.add(simulate(teamA, teamB));
        }

        return resultList;
    }

    private static boolean isNotOver(List<Result> resultList, int bOx){
        int vitTeamA = 0;
        int vitTeamB = 0;
        int maxResult = (bOx/2) + 1;

        for(Result result : resultList){
            if(result.isAWinner())
                vitTeamA++;
            else
                vitTeamB++;
        }

        return vitTeamA == maxResult || vitTeamB == maxResult;
    }
}
