package br.com.campsim.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Result {

    private Double scoreA;
    private Double scoreB;

    public boolean isAWinner(){
        return scoreA > scoreB;
    }
}
