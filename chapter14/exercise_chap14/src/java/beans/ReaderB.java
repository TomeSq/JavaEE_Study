package beans;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@RequestScoped
public class ReaderB {

    @Inject
    Logger log;
    @EJB
    MailSender sender;
    // 以下にreadメソッドを作成する
    public void read(@Observes String weatherNews)
    {
        sender.send("", "yahoo天気・災害・東京都の天気", weatherNews);
    }
    
}
