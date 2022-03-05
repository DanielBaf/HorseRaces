package BackEnd.Bets;

import BackEnd.Objects.Bet;
import BackEnd.Objects.Timer;
import BackEnd.Objects.List.Node;
import BackEnd.Objects.List.NodeList;
import BackEnd.Reports.ReportStatus;
import BackEnd.Utilities.ItemRepeatedValidator;
import Controller.MainViewController;

public class BetChecker {

    private final ItemRepeatedValidator repeatValidator;
    private final Timer timer;
    private long time;
    private long steps;
    private long realSteps;

    MainViewController controller;

    public BetChecker() {
        this.repeatValidator = new ItemRepeatedValidator();
        timer = new Timer();
        resetSteps();
    }

    /**
     * Validates the bets, if the bet is valid, the info of the bet is stored in
     * the bet object is changed to false if any of the horses are repeated,
     * otherwise, the bet is valid
     *
     * @param bets the bets to analyze
     * @return
     */
    public ReportStatus validateBets(NodeList<Bet> bets) {
        // varibales
        resetSteps();
        boolean error = false;
        Node<Bet> current = bets.getTail();
        while (current != null) {
            addSteps(1, 4);
            try {
                // variables
                this.repeatValidator.resetSteps();
                this.timer.run();
                // save valid info
                if (current.getData().getHorses().length != 10 || current.getData().getAmount() < 0) {
                    current.getData().setValid(false);
                    addRealSteps(1);
                } else {
                    current.getData().setValid(this.repeatValidator.isNumRepeat(current.getData().getHorses()));
                    addRealSteps(1);
                }
                // add values to promedium
                this.timer.stopTimer();
                this.time += this.timer.getTotalTime();
                addSteps(this.repeatValidator.getSteps(), this.repeatValidator.getRealSteps() + 7);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                error = true;
                addRealSteps(1);
            }
            current = current.getNext();
            addRealSteps(1);
        }
        this.time = this.time / bets.getSize();
        this.steps = this.steps / bets.getSize();
        this.realSteps = this.realSteps / bets.getSize();
        return error ? ReportStatus.FAILURE : ReportStatus.SUCCESS;
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
        this.repeatValidator.resetSteps();
        this.steps = 0;
        this.realSteps = 0;
    }

}
