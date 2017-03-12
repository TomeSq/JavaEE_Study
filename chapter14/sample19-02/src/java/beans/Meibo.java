package beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.interceptor.Interceptors;

@Interceptors(Tracer.class)
@SessionScoped
public class Meibo implements Serializable {

    private Map<Integer, String> meibo;

    public Meibo() {
        meibo = new LinkedHashMap<>();
    }

    public String add(Integer number, String name) {
        return meibo.put(number, name);
    }
}
