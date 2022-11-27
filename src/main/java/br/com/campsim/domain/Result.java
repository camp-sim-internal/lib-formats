package br.com.campsim.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
public class Result {

    private Double scoreA;
    private Double scoreB;
    private Result resultDraw;

    public boolean isAWinner(){
        return nonNull(resultDraw) ? resultDraw.isAWinner() : scoreA > scoreB;
    }

    public boolean isBWinner(){
        return nonNull(resultDraw) ? resultDraw.isBWinner() : scoreB > scoreA;
    }

    public boolean isDraw(){
        return Objects.equals(scoreA, scoreB);
    }

    public boolean thereIsResultDraw() {
        return nonNull(resultDraw);
    }

    public void printResult() {
        if (isDraw()) {
            if (isAWinner())
                System.out.printf("TIME A VENCEU NO DESEMPATE! | PLACAR: TIME A %.0f X %.0f TIME B%nPLACAR DESEMPATE: TIME A %.0f X %.0f TIME B%n", scoreA, scoreB, resultDraw.scoreA, resultDraw.getScoreB());
            else if (isBWinner())
                System.out.printf("TIME B VENCEU NO DESEMPATE! | PLACAR: TIME A %.0f X %.0f TIME B%nPLACAR DESEMPATE: TIME A %.0f X %.0f TIME B%n", scoreA, scoreB, resultDraw.scoreA, resultDraw.getScoreB());
            else
                System.out.printf("EMPATE TÉCNICO | PLACAR: TIME A %.0f X %.0f TIME B%n", scoreA, scoreB);
        } else {
            if (isAWinner())
                System.out.printf("TIME A VENCE O JOGO | PLACAR: TIME A %.0f X %.0f TIME B%n", scoreA, scoreB);
            else if (isBWinner())
                System.out.printf("TIME B VENCE O JOGO | PLACAR: TIME A %.0f X %.0f TIME B%n", scoreA, scoreB);
        }
    }
}
