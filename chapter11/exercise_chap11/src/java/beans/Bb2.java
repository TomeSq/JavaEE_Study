package beans;

import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb2 {

    private int[] cities;

    public String next() {
        return "output.xhtml";
    }

    @Override
    public String toString() {
        return Arrays.toString(cities);
    }

    public int[] getCities() {
        return cities;
    }

    public void setCities(int[] cities) {
        this.cities = cities;
    }

}
