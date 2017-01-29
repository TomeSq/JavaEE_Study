package beans;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.Dependent;

@Max32
@Dependent
public class Dice32 implements Dice, Serializable {

    private Integer n;

    @Override
    public Integer playDice() {
        return n = new Random().nextInt(32) + 1;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }
}
