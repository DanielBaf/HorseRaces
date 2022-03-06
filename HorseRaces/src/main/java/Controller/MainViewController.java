package Controller;

import BackEnd.Bets.BetChecker;
import BackEnd.Bets.ResBoardCalculator;
import BackEnd.Objects.Bet;
import BackEnd.Objects.List.NodeList;
import BackEnd.Reports.Report;
import BackEnd.Reports.ReportManager;
import BackEnd.Reports.ReportStatus;
import BackEnd.Utilities.CSVExporter;
import BackEnd.Utilities.FileAnalyzer;
import BackEnd.Utilities.Sorter;
import javax.swing.JOptionPane;

public class MainViewController {

    private final FileAnalyzer fileAnalyzer;
    private NodeList<Bet> bets;
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
        if (!this.bets.isEmpty()) { // if there are bets
            ReportStatus tmp = this.betChecker.validateBets(this.bets); // validate and add report
            this.reportManager.addReport(new Report(this.betChecker.getTime(), this.betChecker.getSteps(),
                    this.betChecker.getRealSteps(), ReportStatus.VALIDATE_BETS,
                    this.betChecker.getMostSteps(), this.betChecker.getLessSteps()));
            // export no valids to CSV
            if (tmp == ReportStatus.SOME_BETS_INVALID) {
                JOptionPane.showMessageDialog(null, "Algunas apuestas son invalidas\nPor favor selecciona un archivo para exportar las apuestas invalidas");
                CSVExporter csvE = new CSVExporter();
                csvE.exportCSV(false, this.bets);
            }
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
        if (!this.bets.isEmpty()) { // if there are bets
            ReportStatus tmp = switch (status) { // sort
                case SORT_BY_NAME ->
                    this.sorter.sortByGambler(this.bets);
                case SORT_BY_POINTS ->
                    this.sorter.sortByPoints(this.bets);
                default ->
                    ReportStatus.UNKNOWN_ACTION;
            };
            if (tmp != ReportStatus.FAILURE && tmp != ReportStatus.UNKNOWN_ACTION) { // add report if success
                this.reportManager.addReport(new Report(this.sorter.getTime(), this.sorter.getSteps(),
                        this.sorter.getRealSteps(), status, this.sorter.getMostSteps(), this.sorter.getLessSteps()));
            }
            return tmp;
        } else {
            return ReportStatus.LIST_EMPTY;
        }
    }

    public ReportStatus calculatePoints() {
        return null;
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
        ReportStatus status = validateBets(); // 1st step is validate bets
        if (status == ReportStatus.SUCCESS || status == ReportStatus.SOME_BETS_INVALID) {
            status = this.betCalculator.calculate(this.bets, horsesPodium);
            if (status == ReportStatus.SUCCESS) { // 2nd step is calculate bets points
                status = sortBets(sort);
                if (status == ReportStatus.SUCCESS) { // 3rd step is sort bets
                    // info ready to be shown in frontend, add report of sortgin
                    this.reportManager.addReport(new Report(this.betCalculator.getTime(), this.betCalculator.getSteps(),
                            this.betCalculator.getRealSteps(), ReportStatus.CALCULATE_POINTS,
                            this.betCalculator.getMostSteps(), this.betCalculator.getLessSteps()));
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

    public NodeList<Bet> getBets() {
        return this.bets;
    }

    public void resetBet() {
        this.bets = new NodeList<>();
    }

}
