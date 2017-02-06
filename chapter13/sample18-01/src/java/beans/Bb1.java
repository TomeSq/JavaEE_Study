package beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb1 {

    @EJB
    private GlobalMemo bean1;

    public String next() {
        return "index2";
    }

    public GlobalMemo getBean1() {
        return bean1;
    }

    public void setBean1(GlobalMemo bean1) {
        this.bean1 = bean1;
    }

}
