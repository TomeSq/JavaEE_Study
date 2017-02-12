package beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TimerService {

    @Inject
    transient Logger log;

    @Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
    public void myTimer() {
        SimpleDateFormat fmt = new SimpleDateFormat("M/dd hh:mm:ss");
        log.fine("■タイマーサービス■" + fmt.format(new Date()));
    }

    @PostConstruct
    public void check() {
        log.info("★オブジェクトを構築しました");
    }
}
