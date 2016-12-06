package beans;

import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private Integer[] cities;
    private static SelectItem[] items = {
        new SelectItem(1, "東京"),
        new SelectItem(2, "大阪"),
        new SelectItem(3, "福岡")
    };

    public Integer[] getCities() {
        return cities;
    }

    public String next() {
        return "output.xhtml";
    }

    @Override
    public String toString() {
        return Arrays.toString(cities);
    }

    public void setCities(Integer[] cities) {
        this.cities = cities;
    }

    public SelectItem[] getItems() {
        return items;
    }
}
