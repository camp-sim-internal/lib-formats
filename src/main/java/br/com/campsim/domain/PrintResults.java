package br.com.campsim.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintResults {

    private boolean printGameHistoric;
    private boolean printGameResult;
    private boolean printChampionshipResult;
}
