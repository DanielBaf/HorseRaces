package BackEnd.Utilities;

import BackEnd.Objects.Bet;
import BackEnd.Objects.Timer;
import BackEnd.Objects.List.Node;
import BackEnd.Objects.List.NodeList;
import BackEnd.Reports.ReportStatus;

public class Sorter {

    private final Timer timer;
    private long time;
    private long steps;
    private long realSteps;
    private long lessSteps;
    private long mostSteps;

    public Sorter() {
        this.timer = new Timer();
        resetSteps();
    }

    /**
     * method to sort an array of Strings the array is part of an Object named
     * "bets" the method sorts the array by the String "gambler"
     *
     * @param list the bet list
     * @return the status of the action
     */
    public ReportStatus sortByGambler(NodeList<Bet> list) {
        resetSteps();
        try {
            this.timer.run();
            Node<Bet> current = list.getTail();
            while (current != null) {
                addSteps(1, 2); // while loop and assignation
                Node<Bet> comparing = current;
                while (comparing != null) {
                    addSteps(1, 2); // while loop and assignation
                    if (comparing.getData().getGambler().getName()
                            .compareTo(current.getData().getGambler().getName()) > 0) {
                        Bet tmp = comparing.getData();
                        comparing.setData(current.getData());
                        current.setData(tmp);
                        addRealSteps(4);
                    }
                    comparing = comparing.getNext();
                    addRealSteps(1);
                }
                current = current.getNext();
                addRealSteps(1);
            }
            this.timer.stopTimer();
            this.time = this.timer.getTotalTime();
            addSteps(1, 4); // start timer and assignation
            // promedium
            this.steps = this.steps / list.getSize();
            this.realSteps = this.realSteps / list.getSize();
            return ReportStatus.SUCCESS;
        } catch (Exception e) {
            addRealSteps(1);
            return ReportStatus.FAILURE;
        }
    }

    /**
     * method to sort an array of Doubles the array is part of an Object named
     * "bets" the method sorts the array by the Double "amount"
     *
     * @param bets the bet list
     * @return
     */
    public ReportStatus sortByPoints(NodeList<Bet> list) {
        try {
            this.timer.run();
            Node<Bet> current = list.getTail();
            // loop for item
            while (current != null) {
                int stepByLoop = 1;
                int realStepByLoop = 2;        
                Node<Bet> comparing = current;
                while (comparing != null) {
                    stepByLoop++;
                    realStepByLoop++;
                    if (((Bet) comparing.getData()).getGambler().getPoints() > current.getData().getGambler()
                            .getPoints()) {
                        Bet tmp = (Bet) comparing.getData();
                        comparing.setData(current.getData());
                        current.setData(tmp);
                        realStepByLoop += 4;
                    }
                    comparing = comparing.getNext();
                    realStepByLoop++;
                }
                current = current.getNext();
                calcMostLessSteps(stepByLoop);
                addSteps(stepByLoop, realStepByLoop);
            }
            this.timer.stopTimer();
            this.time = this.timer.getTotalTime();
            // promedium
            this.steps = this.steps / list.getSize();
            this.realSteps = this.realSteps / list.getSize();
            addSteps(1, 4);
            return ReportStatus.SUCCESS;
        } catch (Exception e) {
            return ReportStatus.FAILURE;
        }

    }

    public long getTime() {
        return time;
    }

    public long getSteps() {
        return steps;
    }

    public long getRealSteps() {
        return realSteps;
    }

    public long getLessSteps() {
        return lessSteps;
    }

    public long getMostSteps() {
        return mostSteps;
    }

    private void addSteps(long step, long realSteps) {
        this.steps += step;
        this.realSteps += realSteps;
    }

    private void addRealSteps(long number) {
        this.realSteps += number;
    }

    private void resetSteps() {
        this.steps = 0;
        this.realSteps = 0;
        this.lessSteps = 0;
        this.mostSteps = 0;
    }

    private void calcMostLessSteps(int steps) {
        if (this.mostSteps == this.lessSteps && this.mostSteps == 0) {
            this.mostSteps = this.lessSteps = steps;
        } else {
            if (steps > this.mostSteps) {
                this.mostSteps = steps;
            } else if (steps < this.lessSteps) {
                this.lessSteps = steps;
            }
        }
    }
}
