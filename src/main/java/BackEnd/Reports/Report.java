package BackEnd.Reports;

public class Report {

    private ReportStatus status;
    private long processingTIme;
    private long steps;
    private long realSteps;

    public Report() {
    }

    public Report(long processingTIme, long steps, long realSteps, ReportStatus status) {
        this.processingTIme = processingTIme;
        this.steps = steps;
        this.realSteps = realSteps;
        this.status = status;
    }

    public long getProcessingTIme() {
        return processingTIme;
    }

    public void setProcessingTIme(long processingTIme) {
        this.processingTIme = processingTIme;
    }

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public long getRealSteps() {
        return realSteps;
    }

    public void setRealSteps(long realSteps) {
        this.realSteps = realSteps;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Report [processingTIme=" + processingTIme + ", realSteps=" + realSteps + ", status=" + status
                + ", steps=" + steps + "]";
    }
    
}
