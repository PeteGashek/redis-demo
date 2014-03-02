package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by lars on 3/1/14.
 */
public class Contribution implements Serializable {

    private final String who;
    private final int amount;

    @JsonCreator
    public Contribution(@JsonProperty(value = "Who", required = true) String who, @JsonProperty(value = "Amount", required = true) int amount) {
        this.who = who;
        this.amount = amount;
    }

    public String getWho() {
        return who;
    }

    public int getAmount() {
        return amount;
    }


    public String getAmountFormatted() {
        return DataContext.FORMAT.format(getAmount());
    }
}
