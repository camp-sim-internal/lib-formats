package br.com.campsim.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Team <T>{

    private String name;
    private T power;

    public T getPower() {
        return this.power;
    }
}
