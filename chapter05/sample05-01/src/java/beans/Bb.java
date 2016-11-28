package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private String data = "<input type='text' value='田中宏'/>";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
