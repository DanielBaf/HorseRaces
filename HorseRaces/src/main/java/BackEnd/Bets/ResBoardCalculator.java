package BackEnd.Bets;

import BackEnd.Objects.Bet;
import BackEnd.Objects.Timer;
import BackEnd.Objects.List.Node;
import BackEnd.Objects.List.NodeList;
import BackEnd.Reports.ReportStatus;

public class ResBoardCalculator {

    private final Timer timer;
    private long time;
    private long steps;
    private long realSteps;
    private long lessSteps;
    private long mostSteps;
    private int stepLoop = 0;
    private int realStepLoop = 0;

    public ResBoardCalculator() {
        this.timer = new Timer();
        resetSteps();
    }

    /**
     * This method add points to bet's Gambler based on the result of the horses
     * positon, if Gambler guesses correctly the 1st horse gains 10 points, if
     * guesses correctly the 2nd horse wins
     *
     * @param bets           the NodeList of bets to analyze
     * @param horsesPosition the name of the horses sorted by positon from 1st
     *                       to last
     * @return
     */
    public ReportStatus calculate(NodeList<Bet> list, int[] horsePositions) {
        // variables
        boolean error = false;
        Node<Bet> current = list.getTail();
        resetSteps();

        while (current != null) {
            this.timer.run();
            this.stepLoop++;
            this.realStepLoop += 2;
            try {
                setPoints(current.getData(), horsePositions, 0);
            } catch (Exception e) {
                error = true;
                realStepLoop++;
            }
            this.timer.stopTimer();
            this.time += this.timer.getTotalTime();
            calcMostLessSteps(stepLoop);
            addSteps(stepLoop, realStepLoop);// run timer, if, stop timer, get time, return
            current = current.getNext();
            this.stepLoop = 0; // reset loop vals
            this.realStepLoop = 0;
        }
        // promedium
        this.steps = this.steps / list.getSize();
        this.realSteps = this.realSteps / list.getSize();
        this.time = this.time / list.getSize();
        return error ? ReportStatus.FAILURE : ReportStatus.SUCCESS;

    }

    /**
     * This method set the points to the bet gambler, only if the name of the
     * horse matches with the result horse postion
     *
     * @param next
     * @param horsePositions
     * @param index
     */
    private void setPoints(Bet next, int[] horsePositions, int index) {
        if (next.getHorses()[index] == horsePositions[index]) {
            this.realStepLoop += 2;
            this.stepLoop++;
            next.getGambler().setPoints(next.getGambler().getPoints() + 10 - index);
        }
        if (index == next.getHorses().length - 1) {
            this.realStepLoop++; // end or rec
            return;
        } else {
            index++;
            this.stepLoop++;
            this.realStepLoop += 2; // index increase and recursive call
            setPoints(next, horsePositions, index);
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

    private void resetSteps() {
        this.steps = 0;
        this.realSteps = 0;
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
