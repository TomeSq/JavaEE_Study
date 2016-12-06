package beans;

import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private Integer[] cities;

    public String next() {
        return "output.xhtml";
    }

    @Override
    public String toString() {
        return Arrays.toString(cities);
    }

    public Integer[] getCities() {
        return cities;
    }

    public void setCities(Integer[] cities) {
        this.cities = cities;
    }

}
