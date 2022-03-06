package BackEnd.Reports;

import BackEnd.Objects.List.NodeList;

public class ReportManager {

    private final NodeList<Report> reportsOK;

    public ReportManager() {
        this.reportsOK = new NodeList<>();
    }

    public NodeList<Report> getReportsOK() {
        return reportsOK;
    }

    public void addReport(Report data) {
        this.reportsOK.addAtHead(data);
    }

}
