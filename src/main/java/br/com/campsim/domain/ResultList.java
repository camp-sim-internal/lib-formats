package br.com.campsim.domain;

import br.com.campsim.exception.SerieIsNotOverException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResultList {

    private final int bOx;
    private final List<Result> content;

    private int winner;

    public ResultList(int bestOfX){
        this.content = new ArrayList<>();
        this.bOx = bestOfX;
        this.winner = 0;
    }

    public void addResult(Result result){
        if(this.winner != 0) return;

        content.add(result);
        verifyIsOver();
    }

    private void verifyIsOver(){
        int vitTeamA = 0;
        int vitTeamB = 0;
        int maxResult = (bOx/2) + 1;

        for(Result result : this.content){
            if(result.isAWinner())
                vitTeamA++;
            else
                vitTeamB++;
        }

        if(vitTeamA == maxResult) {
            this.winner = 1;
        }
        if(vitTeamB == maxResult) {
            this.winner = -1;
        }
    }

    public boolean isNotOver(){
        return this.winner == 0;
    }

    public boolean isTeamAWinner(){
        if(this.winner == 0)
            throw new SerieIsNotOverException();

        return this.winner == 1;
    }

    public double getAllScore(boolean isTeamA){
        if(isTeamA)
            return content.stream().mapToDouble(Result::getScoreA).sum();
        else
            return content.stream().mapToDouble(Result::getScoreB).sum();
    }

    public double getAllDifferenceScoreAtA(){
        return content.stream().mapToDouble(Result::getScoreA).sum() - content.stream().mapToDouble(Result::getScoreB).sum();
    }

    public double getAllDifferenceScoreAtB(){
        return content.stream().mapToDouble(Result::getScoreB).sum() - content.stream().mapToDouble(Result::getScoreA).sum();
    }

    public void printSimplifiedResult() {
        if (isTeamAWinner())
            System.out.printf("%s |V| X |D| %s%n", content.get(0).getNameA(), content.get(0).getNameB());
        else
            System.out.printf("%s |D| X |V| %s%n", content.get(0).getNameA(), content.get(0).getNameB());
    }
}
