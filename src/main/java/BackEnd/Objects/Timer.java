package BackEnd.Objects;

public class Timer extends Thread {

    private long startTime;
    private long endTime;
    private long totalTime;
    private boolean isRunning;

    public Timer() {
        this.startTime = 0;
        this.endTime = 0;
        this.totalTime = 0;
        this.isRunning = false;
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

    public void stopTimer() {
        this.endTime = System.currentTimeMillis();
        this.totalTime = this.endTime - this.startTime;
        this.isRunning = false;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void run() {
        this.startTimer();
    }

}
