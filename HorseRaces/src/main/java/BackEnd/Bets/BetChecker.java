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
    private int stepsByLoop;
    private long realSteps;
    private int realStepsByLoop;
    private long lessSteps;
    private long mostSteps;
    private int noValids;
    private boolean error;

    MainViewController controller;

    public BetChecker() {
        this.realStepsByLoop = 0;
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
        // actions
        resetSteps(); // reset vals for report
        // varibales
        validateSub(bets.getTail());
        // create a promedium
        this.time = this.time / bets.getSize();
        this.steps = this.steps / bets.getSize();
        this.realSteps = this.realSteps / bets.getSize();
        return this.error ? ReportStatus.FAILURE
                : (this.noValids > 0 ? ReportStatus.SOME_BETS_INVALID : ReportStatus.SUCCESS);
    }

    /**
     * Submethdo to validate all bets 1 by 1 recursively
     * 
     * @param next the node to validate
     */
    private void validateSub(Node<Bet> next) {
        if (next == null) {
            // end
        } else {
            int status = validate(next);
            // if the bet is valid, the info of the bet is stored in the bet
            if (status == 1) {
                this.noValids++;
            } else if (status == -1) {
                this.error = true;
            }
            // step section -> add the steps into the method validate
            calcMostLessSteps();
            addSteps(this.stepsByLoop, this.realStepsByLoop);
            // recursivity
            validateSub(next.getNext());
        }
    }

    /**
     * This method validate a sibgle node and see if bet is valid
     * 
     * @param current the node to analyze
     * @return 0 if no error, 1 if no valid, -1 if error
     */
    private int validate(Node<Bet> current) {
        int valid = 0;
        try {
            // variables
            this.repeatValidator.resetSteps();
            this.timer.run();
            // save valid info
            if (current.getData().getHorses().length != 10 || current.getData().getAmount() < 0) {
                current.getData().setValid(false);
                valid = 1;
                addSteps(0, 1);
            } else if (current.getData().getGambler().getName().isEmpty()
                    || current.getData().getGambler().getName().isBlank()) {
                valid = 1;
            } else {
                current.getData().setValid(this.repeatValidator.isNumRepeat(current.getData().getHorses()));
                if (!current.getData().isValid()) {
                    valid = 1;
                    addStepLoop(0, 1);
                }
                // add steps from method numrepeat
                addStepLoop(this.repeatValidator.getSteps(), this.repeatValidator.getRealSteps() + 1);
            }
            // add values to promedium
            this.timer.stopTimer();
            this.time += this.timer.getTotalTime();
        } catch (Exception e) {
            valid = -1;
        }
        addStepLoop(1, 7);
        return valid;
    }

    private void addSteps(long step, long realSteps) {
        this.steps += step;
        this.realSteps += realSteps;
        this.stepsByLoop = 0;
        this.realStepsByLoop = 0;
    }

    private void addStepLoop(long step, long realSteps) {
        this.stepsByLoop += step;
        this.realStepsByLoop += realSteps;
    }

    private void resetSteps() {
        this.repeatValidator.resetSteps();
        this.steps = 0;
        this.realSteps = 0;
        this.mostSteps = 0;
        this.lessSteps = 0;
        this.stepsByLoop = 0;
        this.realStepsByLoop = 0;
        this.noValids = 0;
        this.error = false;
    }

    /**
     * Calculates if the actual steps are more or less than the saved
     *
     * @param steps
     */
    private void calcMostLessSteps() {
        if (this.mostSteps == this.lessSteps && this.mostSteps == 0) {
            this.mostSteps = this.lessSteps = this.stepsByLoop;
        } else {
            if (this.stepsByLoop > this.mostSteps) {
                this.mostSteps = steps;
            } else if (this.stepsByLoop < this.lessSteps) {
                this.lessSteps = steps;
            }
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

}
