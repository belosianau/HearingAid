package ntou.cs.lab505.hearingaid.activities;

/**
 * Created by alan on 2/9/15.
 */
public class FilterView {

    public int lowBand;
    public int highBand;

    /**
     *
     * @param lowBand
     * @param highBand
     */
    public FilterView(int lowBand, int highBand) {
        this.lowBand = lowBand;
        this.highBand = highBand;
    }
}