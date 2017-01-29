package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private int val;
    private String s;
    /* ここにBinをインジェクトする */
    
    @Inject
    private Bin bin;

    public String next() {
        setS(bin.toBin(getVal()));
        return null;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

}
