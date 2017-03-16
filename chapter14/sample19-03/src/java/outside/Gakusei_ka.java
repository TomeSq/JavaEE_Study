package outside;

import beans.Student;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@RequestScoped
public class Gakusei_ka {

    @Inject
    transient private Logger log;

    public void receive(@Observes Student st) {
        log.info("■学生課での処理：" + st.toString());
    }
}
