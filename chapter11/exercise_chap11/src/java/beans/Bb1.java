package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb1 {

    private boolean mail;

    public boolean isMail() {
        return mail;
    }

    public void setMail(boolean mail) {
        this.mail = mail;
    }

}
