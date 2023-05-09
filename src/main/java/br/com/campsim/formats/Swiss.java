package br.com.campsim.formats;

import br.com.campsim.domain.*;
import br.com.campsim.exception.InvalidNumberOfTeamsException;
import br.com.campsim.formats.interfaces.Format;
import br.com.campsim.game.GameSimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Swiss<T> implements Format<T> {

    private final List<TeamLeague<T>> teams;
    private final PrintResults printResults;
    private final GameSimulator<T> gameSimulator;
    private final List<List<GameLeague<T>>> games;
    private int round = 1;

    public Swiss(List<Team<T>> teams, PrintResults printResults, GameSimulator<T> gameSimulator) {
        if (teams.size() != 16)
            throw new InvalidNumberOfTeamsException();

        this.teams = teams.stream().map(TeamLeague::new).toList();
        this.printResults = printResults;
        this.gameSimulator = gameSimulator;
        this.games = new ArrayList<>();
    }

    @Override
    public List<Team<T>> simulateAll(){
        List<Team<T>> clfTeams = new ArrayList<>();
        while (isThereGamesYet()){
            clfTeams = simulateRound();
        }
        return clfTeams;
    }

    private boolean isThereGamesYet() {
        return teams.stream().anyMatch(team -> team.getWins() < 3 && team.getLoses() < 3);
    }

    private List<Team<T>> getClfTeams() {
        return teams.stream().filter(teams -> teams.getWins() == 3).map(x -> (Team<T>) x).toList();
    }

    @Override
    public List<Team<T>> simulateRound(){
        if (!isThereGamesYet())
            return getClfTeams();

        if (printResults.isPrintGameResult() || printResults.isPrintGameHistoric())
            System.out.println("ROUND " + round++);

        List<GameLeague<T>> gameDividedByWins = new ArrayList<>();
        games.add(gameDividedByWins);
        for (int wins = 0; wins < 3; wins++) {
            Integer finalWins = wins;
            List<TeamLeague<T>> teamsByRound = new ArrayList<>(teams.stream().filter(x -> finalWins.equals(x.getWins()) && x.getLoses() < 3).toList());
            if (teamsByRound.isEmpty())
                continue;

            Collections.shuffle(teamsByRound);
            int numberOfTeams = teamsByRound.size();
            int bOx = teamsByRound.get(0).getWins() == 2 || teamsByRound.get(0).getLoses() == 2 ? 3 : 1;
            for(int indexUp = 0, indexDown = numberOfTeams - 1; indexUp < numberOfTeams/2; indexUp ++, indexDown --)
                gameDividedByWins.add(new GameLeague<>(teamsByRound.get(indexUp), teamsByRound.get(indexDown), bOx, printResults, false));
        }

        gameDividedByWins.forEach(x -> x.internalRuleWinner(gameSimulator));

        return getClfTeams();
    }
}
