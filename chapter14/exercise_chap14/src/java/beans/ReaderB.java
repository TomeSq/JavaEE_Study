package beans;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ReaderB {

    @Inject
    Logger log;
    @EJB
    MailSender sender;

	// 以下にreadメソッドを作成する
}
