package BackEnd.Objects;

public class Gambler {

    private String name;
    private int points;

    public Gambler() {
    }

    public Gambler(String name) {
        this.name = name;
        this.points = 0;
    }

    public Gambler(String name, int points) {
        this.name = name;
        this.points = points;
    }

    // GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Gambler [name=" + name + ", points=" + points + "]";
    }

}
