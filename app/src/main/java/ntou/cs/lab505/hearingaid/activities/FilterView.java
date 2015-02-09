package ntou.cs.lab505.hearingaid.activities;

/**
 * Created by alan on 2/9/15.
 */
public class FilterView {

    public int lowBand;
    public int highBand;

    public FilterView() {
        super();
    }

    public FilterView(int lowBand, int highBand) {
        super();
        this.lowBand = lowBand;
        this.highBand = highBand;
    }
}