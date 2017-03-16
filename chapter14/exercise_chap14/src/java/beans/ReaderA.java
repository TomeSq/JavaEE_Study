package beans;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@RequestScoped
public class ReaderA {

    @Inject
    transient Logger log;
    // 以下にreadメソッドを作成する
    public void read(@Observes String weatherNews)
    {
        log.info(weatherNews);
    }
}
