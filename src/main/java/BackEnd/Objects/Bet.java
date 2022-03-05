package BackEnd.Objects;

import java.util.Arrays;

public class Bet {
    private Gambler gambler;
    private Double amount;
    private int[] horses;
    private boolean isValid;
    
    public Bet() {
    }

    public Bet(Gambler gambler, Double amount, int[] horses) {
        this.gambler = gambler;
        this.amount = amount;
        this.horses = horses;
        this.isValid = false;
    }

    // GETTERS AND SETTERS
    public Gambler getGambler() {
        return gambler;
    }

    public void setGambler(Gambler gambler) {
        this.gambler = gambler;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int[] getHorses() {
        return horses;
    }

    public void setHorses(int[] horses) {
        this.horses = horses;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "Bet [amount=" + amount + ", gambler=" + gambler + ", horses=" + Arrays.toString(horses) + ", isValid="
                + isValid + "]";
    }

}
