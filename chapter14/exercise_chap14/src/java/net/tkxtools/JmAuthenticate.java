/*
 POP3サーバーに接続してみて正規ユーザーかどうか調べる
　　
 jmAuthenticate.authenticate(host,user,passwd)
 REURN:  ture   成功
 false　失敗

 */
package net.tkxtools;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class JmAuthenticate {

    private Store store;

    //
    //POP 認証

    public static boolean authenticate(String host, String user, String password) {
        try {
            JmAuthenticate jm = new JmAuthenticate();
            jm.connect(host, user, password);
            jm.disconnect();
        } catch (NoSuchProviderException e1) {
            return false;
        } catch (MessagingException e2) {
            return false;
        }
        return true;
    }

    // コンストラクタ

    public JmAuthenticate() throws NoSuchProviderException {
        Session session = Session.getInstance(new Properties(), null);
        store = session.getStore("pop3");
    }

    // 接　続

    public synchronized void connect(String host,
            String user,
            String password) throws MessagingException {
        store.connect(host, -1, user, password);
    }

    // 切　断

    public synchronized void disconnect() {
        try {
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        disconnect();
    }
}
