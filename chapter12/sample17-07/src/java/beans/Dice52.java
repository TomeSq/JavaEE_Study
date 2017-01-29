package beans;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@RequestScoped
public class Dice52 implements Dice, Serializable {

    private Integer n;

    @Override
    public Integer playDice() {
        return n = new Random().nextInt(52) + 1;
    }

    @Override
    public String toString() {
        return "最大値52のサイコロ";
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }
}
