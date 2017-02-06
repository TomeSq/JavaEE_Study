package beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb2 {

    @EJB
    private GlobalMemo bean2;

    public String next() {
        return "index";
    }

    public GlobalMemo getBean2() {
        return bean2;
    }

    public void setBean2(GlobalMemo bean2) {
        this.bean2 = bean2;
    }

}
