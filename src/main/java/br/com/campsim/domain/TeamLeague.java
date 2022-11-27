package br.com.campsim.domain;

import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.rightPad;

@Getter
public class TeamLeague extends Team implements Comparable<TeamLeague>{

    private int pontuation;
    private int wins;
    private int loses;
    private int draws;
    private double drawCondition;

    public TeamLeague(Team team){
        super(team.getName(), team.getPower());

        this.wins = 0;
        this.loses = 0;
        this.draws = 0;
        this.drawCondition = 0.0;
    }

    public void winner(double drawCondition){
        this.pontuation += 3;
        this.wins++;
        this.drawCondition += drawCondition;
    }

    public void loser(double drawCondition){
        this.loses++;
        this.drawCondition += drawCondition;
    }

    public void draw(){
        this.pontuation += 1;
        this.draws++;
    }

    public String getLineTable() {
        return String.format("%s\t|%d| %d %d %d\t%.1f%n", rightPad(getName(), 4, ' ').substring(0, 4), pontuation, wins, draws, loses, drawCondition);
    }

    @Override
    public int compareTo(TeamLeague o) {

        if(this.pontuation > o.pontuation)
            return -1;
        else if(this.pontuation < o.pontuation)
            return 1;
        else {
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
}
