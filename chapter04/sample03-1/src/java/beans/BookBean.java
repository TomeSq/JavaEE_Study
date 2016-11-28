package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class BookBean {

    public String next() {
        return null;
    }
}
