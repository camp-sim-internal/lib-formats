package br.com.campsim.domain;

import lombok.Getter;

@Getter
public class TeamLeague extends Team implements Comparable<TeamLeague>{

    private int wins;
    private double drawCondition;

    public TeamLeague(Team team){
        super(team.getName(), team.getPower());
        this.wins = 0;
        this.drawCondition = 0.0;
    }

    public void winner(double drawCondition){
        this.wins++;
        this.drawCondition += drawCondition;
    }

    @Override
    public int compareTo(TeamLeague o) {

        if(this.wins > o.wins)
            return -1;
        else if(this.wins < o.wins)
            return 1;
        else {
            if(this.drawCondition > o.drawCondition)
                return -1;
            else
                return 1;
        }
    }
}
