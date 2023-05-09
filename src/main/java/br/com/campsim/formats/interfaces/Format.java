package br.com.campsim.formats.interfaces;

import br.com.campsim.domain.Team;

import java.util.List;

public interface Format<T> {

    List<Team<T>> simulateAll();
    List<Team<T>> simulateRound();
}
