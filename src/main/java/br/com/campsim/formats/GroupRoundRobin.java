package br.com.campsim.formats;

import br.com.campsim.domain.*;
import br.com.campsim.exception.InvalidConditionToRunException;
import br.com.campsim.formats.interfaces.Format;
import br.com.campsim.game.GameSimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class GroupRoundRobin<T> implements Format<T> {

    private final List<TeamLeague<T>> teamLeagues;
    private final PrintResults printResults;
    private final GameSimulator<T> gameSimulator;
    private List<List<GameLeague<T>>> games;
    private int actualRound;

    public GroupRoundRobin(List<Team<T>> teams, int bOx, int turns, GameSimulator<T> gameSimulator, boolean simulateWithDrawCase, PrintResults printResults){
        if (bOx > 1 && !simulateWithDrawCase)
            throw new InvalidConditionToRunException();

        this.gameSimulator = gameSimulator;
        this.teamLeagues = new ArrayList<>();
        teams.forEach(team -> teamLeagues.add(new TeamLeague<>(team)));

        this.printResults = printResults;
        createGames(turns, bOx, simulateWithDrawCase);
        this.actualRound = 0;
    }

    private void createGames(int turns, int bOx, boolean simulateWithDrawCase) {
        games = new ArrayList<>();
        int numberTeams = this.teamLeagues.size();
        if (numberTeams % 2 != 0) {
            this.teamLeagues.add(null);
            numberTeams++;
        }


        int rounds = numberTeams - 1;

        for (int turn = 0; turn < turns; turn++) {
            for (int round = 0; round < rounds; round++) {
                List<GameLeague<T>> roundGames = new ArrayList<>();

                for (int teamIndex = 0; teamIndex < numberTeams / 2; teamIndex++) {
                    if (isNull(this.teamLeagues.get(teamIndex)) || isNull(this.teamLeagues.get(numberTeams - 1 - teamIndex)))
                        continue;
                    if (turn % 2 == 0)
                        roundGames.add(new GameLeague<>(this.teamLeagues.get(teamIndex), this.teamLeagues.get(numberTeams - 1 - teamIndex), bOx, printResults, simulateWithDrawCase));
                    else
                        roundGames.add(new GameLeague<>(this.teamLeagues.get(numberTeams - 1 - teamIndex), this.teamLeagues.get(teamIndex), bOx, printResults, simulateWithDrawCase));
                }

                Collections.rotate(this.teamLeagues.subList(1, numberTeams), 1);

                Collections.shuffle(roundGames);
                games.add(roundGames);
            }
        }

        this.teamLeagues.remove(null);
    }

    @Override
    public List<Team<T>> simulateAll(){
        for (List<GameLeague<T>> gamesRound : this.games)
            gamesRound.forEach(x -> x.internalRuleWinner(gameSimulator));

        Collections.sort(teamLeagues);
        printTable();

        return new ArrayList<>(teamLeagues);
    }

    @Override
    public List<Team<T>> simulateRound() {
        this.games.get(actualRound).forEach(x -> x.internalRuleWinner(gameSimulator));

        Collections.sort(teamLeagues);
        printTable();
        actualRound++;

        return new ArrayList<>(teamLeagues);
    }

    public List<TeamLeague<T>> getTeamLeagues(){
        return this.teamLeagues;
    }


    private void printTable() {
        if (printResults.isPrintChampionshipResult()) {
            System.out.println("NOME\t|P| V E D\tdes");
            teamLeagues.forEach(x -> System.out.println(x.getLineTable()));
        }
    }
}
