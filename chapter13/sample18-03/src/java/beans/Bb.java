/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private String mail;	// 送信先メールアドレス
    private String title;	// メールタイトル
    private String msg;	// メール本文

    // 実際にメールを送信するにはMailSenderクラスにGoogle mailのid, password, メールアドレスを記入してください。
    @EJB
    MailSender sender;

    // メールの送信
    public String next() {
        sender.send(mail, title, msg);
        return "output";
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
