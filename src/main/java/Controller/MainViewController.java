package Controller;

import BackEnd.Bets.BetChecker;
import BackEnd.Bets.ResBoardCalculator;
import BackEnd.Objects.Bet;
import BackEnd.Objects.List.NodeList;
import BackEnd.Reports.Report;
import BackEnd.Reports.ReportManager;
import BackEnd.Reports.ReportStatus;
import BackEnd.Utilities.FileAnalyzer;
import BackEnd.Utilities.Sorter;

public class MainViewController {

    private final FileAnalyzer fileAnalyzer;
    private final NodeList<Bet> bets;
    private final BetChecker betChecker;
    private final ResBoardCalculator betCalculator;
    private final Sorter sorter;
    private final ReportManager reportManager;

    public MainViewController() {
        this.fileAnalyzer = new FileAnalyzer();
        this.betChecker = new BetChecker();
        this.betCalculator = new ResBoardCalculator();
        this.sorter = new Sorter();
        this.bets = new NodeList<>();
        this.reportManager = new ReportManager();
    }

    /**
     * Validate bets and add a report if action is success
     *
     * @return
     */
    public ReportStatus validateBets() {
        if (!this.bets.isEmpty()) {
            ReportStatus tmp = this.betChecker.validateBets(this.bets);
            this.reportManager.addReport(new Report(this.betChecker.getTime(), this.betChecker.getSteps(), this.betChecker.getRealSteps(), ReportStatus.VALIDATE_BETS));
            return tmp;
        } else {
            return ReportStatus.LIST_EMPTY;
        }
    }

    /**
     * Sort bets and add a report in case of success
     *
     * @param status SORT BY POINTS OR SORT BY GAMBLER NAME
     * @return
     */
    public ReportStatus sortBets(ReportStatus status) {
        if (!this.bets.isEmpty()) {
            ReportStatus tmp = switch (status) {
                case SORT_BY_NAME ->
                    this.sorter.sortByGambler(this.bets);
                case SORT_BY_POINTS ->
                    this.sorter.sortByPoints(this.bets);
                default ->
                    ReportStatus.UNKNOWN_ACTION;
            };
            if (tmp != ReportStatus.FAILURE && tmp != ReportStatus.UNKNOWN_ACTION) {
                this.reportManager.addReport(new Report(this.sorter.getTime(), this.sorter.getSteps(), this.sorter.getRealSteps(), status));
            }
            return tmp;
        } else {
            return ReportStatus.LIST_EMPTY;
        }
    }

    /**
     * Read data from a file and create objects in the list
     *
     * @param path the file path
     * @return
     */
    public ReportStatus readFile(String path) {
        return this.fileAnalyzer.analyzeFile(path, this.bets);
    }

    public ReportStatus processRace(ReportStatus sort, int[] horsesPodium) {
        if (validateBets() == ReportStatus.SUCCESS) {// verify bets
            ReportStatus status = this.betCalculator.calculate(this.bets, horsesPodium);
            if (status == ReportStatus.SUCCESS) { // calculate points
                status = sortBets(sort);
                if (status == ReportStatus.SUCCESS) { // well sorted
                    // info ready to be shown in frontend
                    this.reportManager.addReport(new Report(this.betCalculator.getTime(), this.betCalculator.getSteps(), this.betCalculator.getRealSteps(), ReportStatus.CALCULATE_POINTS));
                    return ReportStatus.SUCCESS;
                }
                return status;
            }
            return status;
        }
        return ReportStatus.FAILURE;
    }

    public ReportStatus insertBet(Bet bet) {
        try {
            this.bets.addAtHead(bet);
            return ReportStatus.SUCCESS;
        } catch (Exception e) {
            return ReportStatus.FAILURE;
        }

    }

    public ReportManager getReportManager() {
        return reportManager;
    }

}
