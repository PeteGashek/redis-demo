package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by lars on 3/1/14.
 */
public class DataContext {

    public final static DecimalFormat FORMAT = new DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Locale.forLanguageTag("no")));

    private final List<Contribution> last;
    private final Set<Contribution> topList;
    private final long totalAmount;
    private final long totalCnt;

    public DataContext(List<Contribution> last, Set<Contribution> topList, long totalAmount, long totalCnt) {
        this.last = last;
        this.topList = topList;
        this.totalAmount = totalAmount;
        this.totalCnt = totalCnt;
    }

    public List<Contribution> getLast() {
        return last;
    }

    public Set<Contribution> getTopList() {
        return topList;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public long getTotalCnt() {
        return totalCnt;
    }

    public String getTotalAmountFormatted() {
        return FORMAT.format(getTotalAmount());
    }
}
