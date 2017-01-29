package beans;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.RequestScoped;

@Maximun(value = 6)
@RequestScoped
public class Dice6 implements Dice, Serializable {

    private Integer n;

    @Override
    public Integer playDice() {
        return n = new Random().nextInt(6) + 1;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }
}
