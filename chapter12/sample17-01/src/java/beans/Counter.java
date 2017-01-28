package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Counter implements Serializable {

    private Integer n = 0;

    public Integer countup() {
        return ++n;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

}
