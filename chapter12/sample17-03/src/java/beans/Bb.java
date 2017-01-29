package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class Bb implements Serializable {

    @Inject
    FileUtil fu;

    public String getText() {
        return fu.getText("/resources/data/toshishun.txt");
    }
}
