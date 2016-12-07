package beans;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

@Named
@RequestScoped
public class Bb {

    @Max(30)
    private String title;
    @Pattern(regexp="^a[0-9]{4}")
    private String code;
    @Past
    private Date date;
    @Max(30000)
    private Integer amount;

    public String next() {
        return "output";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
