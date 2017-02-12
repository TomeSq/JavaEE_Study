package beans;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import net.tkxtools.JmSender;

@Stateless
public class MailSender {

    String mail_id = ""; // あなたのGoogleのメールIDを記入してください
    String mail_pw = ""; // あなたのGoogleのメールパスワードを記入してください。
    String from    = ""; // あなたのGoogleのメールアドレスを記入してください。
    String host = "smtp.gmail.com";
    int port = 587;

    @Asynchronous
    public void send(String mail, String subject, String body) {
        try {
            JmSender.send(mail_id, mail_pw, host, port, mail, from, subject, body);
        } catch (Exception e) {
        }
    }
}
