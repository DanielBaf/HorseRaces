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

    public ResBoardCalculator() {
        this.timer = new Timer();
        resetSteps();
    }

    /**
     * This method add points to bet's Gambler based on the result of the horses
     * positon, if Gambler guesses correctly the 1st horse gains 10 points, if
     * guesses correctly the 2nd horse wins
     *
     * @param bets the NodeList of bets to analyze
     * @param horsesPosition the name of the horses sorted by positon from 1st
     * to last
     * @return
     */
    public ReportStatus calculate(NodeList<Bet> list, int[] horsePositions) {
        boolean error = false;
        resetSteps();
        Node<Bet> current = list.getTail();

        while (current != null) {
            this.timer.run();
            try {
                setPoints(current.getData(), horsePositions, 0);
            } catch (Exception e) {
                error = true;
                addRealSteps(1);
            }
            this.timer.stopTimer();
            this.time += this.timer.getTotalTime();
            addSteps(1, 1);// run timer, if, stop timer, get time, return
            current = current.getNext();
        }
        // promedium
        this.steps = this.steps / list.getSize();
        this.realSteps = this.realSteps / list.getSize();
        this.time = this.time / list.getSize();
        return error ? ReportStatus.SUCCESS : ReportStatus.SUCCESS;

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
            addSteps(1, 2); // end of recursivity and change pointer
            next.getGambler().setPoints(next.getGambler().getPoints() + 10 - index);
        }
        if (index == next.getHorses().length - 1) {
            addRealSteps(1); // if and return
            return;
        } else {
            index++;
            addSteps(1, 2); // index increase and recursive call
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
    }

}
