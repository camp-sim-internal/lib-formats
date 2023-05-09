package br.com.campsim.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Getter
@Setter
@AllArgsConstructor
public class Result {

    private String nameA;
    private Double scoreA;

    private String nameB;
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
        String vencedor = isAWinner() ? nameA : nameB;
        if (isDraw()) {
            if (isAWinner() || isBWinner())
                System.out.printf("%s VENCEU NO DESEMPATE! | PLACAR: %s %.0f X %.0f %s%nPLACAR DESEMPATE: %s %.0f X %.0f %s%n", vencedor, nameA, scoreA, scoreB, nameB, nameA, resultDraw.scoreA, resultDraw.scoreB, nameB);
            else
                System.out.printf("EMPATE TÉCNICO | PLACAR: %s %.0f X %.0f %s%n", nameA, scoreA, scoreB, nameB);
        } else {
            System.out.printf("%s VENCE O JOGO | PLACAR: %s %.0f X %.0f %s%n", vencedor, nameA, scoreA, scoreB, nameB);
        }
    }

    public void printSimplifiedResult() {
        if (isAWinner())
            System.out.printf("%s |V| X |D| %s%n", nameA, nameB);
        else
            System.out.printf("%s |D| X |V| %s%n", nameA, nameB);
    }
}
