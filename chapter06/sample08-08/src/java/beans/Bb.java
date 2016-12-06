package beans;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private Integer city;
    private static Map<String, Integer> items;

    static {
        items = new LinkedHashMap<>();
        items.put("東京", 1);
        items.put("大阪", 2);
        items.put("福岡", 3);
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public String next() {
        return "output.xhtml";
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

}
