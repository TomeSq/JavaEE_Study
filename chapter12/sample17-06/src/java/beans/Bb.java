package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class Bb implements Serializable {

    private Integer n;
    @Inject
    @Maximun(value = 6)
    private Dice dice;

    public String next() {
        n = dice.playDice();
        return null;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }
}
