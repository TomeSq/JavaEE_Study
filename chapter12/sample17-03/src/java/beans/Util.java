package beans;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class Util {

    @Produces
    private FileUtil ftool() {
        FileUtil fu = new FileUtil();
        return fu;
    }
}
