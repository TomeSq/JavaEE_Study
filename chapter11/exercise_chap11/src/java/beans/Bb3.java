package beans;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb3 {

    private Integer city;
    private static Map<String, Integer> items;

    static {
        items = new LinkedHashMap<>();
        items.put("東京", 1);	// ラベルは「東京」、値は「1」
        items.put("大阪", 2);
        items.put("福岡", 3);
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String next() {
        return "output.xhtml";
    }

    public Integer getCity() {
        return city;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

}
