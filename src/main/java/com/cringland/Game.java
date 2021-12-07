package com.cringland;

public class Game {

    private String timeControl;
    private boolean rated;
    private short whiteElo;
    private short blackElo;
    private String tempDate;
    private String tempTime;
    private String eco;
    private String opening;
    private String termination;
    private String result;

    public void setTimeControl(final String timeControl) {
        this.timeControl = timeControl;
    }

    public String getTimeControl() {
        return timeControl;
    }

    public void setRated(final boolean rated) {
        this.rated = rated;
    }

    public void setWhiteElo(final short whiteElo) {
        this.whiteElo = whiteElo;
    }

    public void setBlackElo(final short blackElo) {
        this.blackElo = blackElo;
    }

    public void setTempDate(final String tempDate) {
        this.tempDate = tempDate;
    }

    public void setTempTime(final String tempTime) {
        this.tempTime = tempTime;
    }

    public void setEco(final String eco) {
        this.eco = eco;
    }

    public void setOpening(final String opening) {
        this.opening = opening;
    }

    public void setTermination(final String termination) {
        this.termination = termination;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public String toString() {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%sT%s\",\"%s\",\"%s\",\"%s\",\"%s\"", timeControl, rated, whiteElo, blackElo, tempDate, tempTime, eco, opening, termination, result);
        //timeControl,rated,whiteElo,blackElo,date,eco,opening,termination,result
    }
}

