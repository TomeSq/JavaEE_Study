package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MeiboBean {
    private Integer number;
    private String name;

    public String next()
    {
        System.out.println("★number=" + this.number + " / name=" + this.name);
        return "output.xhtml";
    }
    
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
