package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

@Named
@RequestScoped
public class Bb implements Serializable {

    @NotNull
    @Size(min = 2, max = 5)
    private String code;
    @Min(100)
    @Max(10000)
    private Integer amount;
    @DecimalMax("20.5")
    @DecimalMin("0.5")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal rate;
    @Past
    private Date date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
