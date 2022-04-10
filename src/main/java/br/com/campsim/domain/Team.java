package br.com.campsim.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Team {

    private String name;
    private Integer power;
}
