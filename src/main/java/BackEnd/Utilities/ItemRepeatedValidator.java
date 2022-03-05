package BackEnd.Utilities;

public class ItemRepeatedValidator {

    private boolean isValid;
    private long steps;
    private long realSteps;

    public ItemRepeatedValidator() {
        this.isValid = true;
    }

    /**
     * This method validate if a String is repeated, if the string is repeated,
     *
     * @param values the sting of values to be validated
     * @return true if there's nos tring repeated, this method is Case sensitive
     */
    public boolean isNumRepeat(int[] values) {
        intDeclared(values, 0, values[values.length - 1], 0);
        boolean tmp = this.isValid; // save data
        this.isValid = true; // reset value of object
        addRealSteps(1); // if, bool asign, valid asign, return
        return tmp;
    }

    /**
     * validate if a String is actually defined, if we have a conj {A,B,C,D} it
     * works comparing A with B, A with C, A with D, B with C, B with D, C with
     * D...
     *
     * @param values the values array of ints
     * @param pos the position of the actual value to compare, when call 1st
     * time send 0
     * @param toCompare the value to compare, when call the method, sand the
     * last item on the array
     * @param index the number of main groups changed, when call 1st time, send
     * 0
     * @return the value compared
     */
    private int intDeclared(int[] values, int pos, int toCompare, int index) {
        // end of recursivity
        if (index >= values.length - 1) {
            addSteps(1, 1); // if, return
            return values[pos];
        } else {
            try {
                // validate if the actual char to compare is the last in the array
                if (pos >= values.length - index - 1) {
                    pos = 0; // reset the position
                    index++; // increases the group index analyzed
                    toCompare = values[values.length - index - 1]; // next value to analyze
                    addRealSteps(1);
                }
            } catch (Exception e) {
                // TODO validate error
                addRealSteps(1); // error management
            }
            if (values[pos] == toCompare && index < values.length - 1) { // at this point A always is the same as A, so
                // validate index isn't the last
                this.isValid = false;
                addRealSteps(1); // if, set valid
            }

            // recursive call
            addSteps(1, 1); // recursive call
            return intDeclared(values, pos + 1, toCompare, index);
        }

    }

    public boolean isValid() {
        return isValid;
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

    private void addSteps(int step, int realSteps) {
        this.steps += step;
        this.realSteps += realSteps;
    }

    private void addRealSteps(int number) {
        this.realSteps += number;
    }

    public void resetSteps() {
        this.steps = 0;
        this.realSteps = 0;
    }

}
