package net.tkxtools;

/**
 * 電子メール送信ユーティリティ
 *
 * 電子メールを送信するスタティックメソッド 添付ファイル付きの送信も可能 送信元はWindowsと前提してCP932のコード変換に対応している
 * 日本語添付ファイル名への対応に ozacc-mail library　を使用
 * http://sourceforge.jp/projects/spring-ext/
 *
 * (C)Takashi KAWABA
 *
 * 2006.1.27 SMTP＿AUTH に対応するよう処理を追加した 2012.5　ポートを指定できるようにした。また日本語添付ファイル名に対応した
 *
 */
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class JmSender {

    private Session session;
    private Transport transport;
    private AuthenticatorUtil authutil;

    //

    /**
     * pop-before-smtp対応のコンストラクタ 事前に、JmAuthenticatorでサーバーと接続しておく
     */
    public JmSender() throws NoSuchProviderException {

        session = Session.getInstance(new Properties(), null);
        transport = session.getTransport("smtp");
    }

    /**
     * SmtpAuth対応のコンストラクタ (2006.1.27～2012.5)
     *
     * @param mode
     * @throws NoSuchProviderException
     */
    public JmSender(String smtpUser, String smtpPassword) throws MessagingException {
        if ((smtpUser == null) || (smtpPassword == null)) {
            throw new MessagingException("ユーザー情報がない");

        } else {
            /*
             * smtpAuthのためのsessionを作成する
             */
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); // 追加

            authutil = new AuthenticatorUtil(smtpUser, smtpPassword);
            session = Session.getInstance(prop, authutil);
        }
        transport = session.getTransport("smtp");

    }

    /**
     * 接続
     *
     * @param host
     * @throws MessagingException
     */
    public void connect(String host) throws MessagingException {
        connect(host, -1);
    }

    /**
     * 接続 (ポートも指定） SMTP_AUTH の場合は、session からuser, passwordが自動的に
     * 採取されるので引数の最後の2つはnullのままでよい
     *
     * @param host
     * @param port
     * @throws MessagingException
     */
    public void connect(String host, int port) throws MessagingException {
        transport.connect(host, port, null, null);
    }

    /**
     * 切断
     *
     */
    public synchronized void disconnect() {
        try {
            transport.close();
        } catch (MessagingException e) {
        }
    }

    /**
     * 送信
     *
     * @param to
     * @param from
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws AddressException
     */
    public void send(String to,
            String from,
            String subject,
            String body)
            throws MessagingException, AddressException {
        MimeMessage msg = createMessage();
        try {
            msg.setSubject(MimeUtility.encodeText(Win2Jis(subject), "iso-2022-jp", "B"));
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String mailbody = body + " ";// ? が付くのをフィックスした
        msg.setText(Win2Jis(mailbody), "iso-2022-jp");
        setHeaders(msg, to, from);
        send(msg);
    }

    /**
     * 送信（複数の添付ファイル付き）
     *
     * @param to
     * @param from
     * @param subject
     * @param body
     * @param fileDir
     * @param flist
     * @throws MessagingException
     * @throws AddressException
     * @throws IOException
     */
    public void sendWF(String to,
            String from,
            String subject,
            String body,
            String fileDir, // ファイル名を含まないパス（読み出し用）
            ArrayList<String> flist) // ファイル名のみ（添付ファイル名となる）
            throws MessagingException, AddressException, IOException {
        MimeMessage msg = createMessage();
        setHeaders(msg, to, from);
        try {
            msg.setSubject(MimeUtility.encodeText(Win2Jis(subject), "ISO-2022-JP", "B"));
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body += " ";// ? が付くのをフィックスした
        msg.setContent(createAttachmentParts(Win2Jis(body), fileDir, flist));
        msg.saveChanges();
        send(msg);
    }

    /**
     * HTMLメール送信
     *
     * @param host
     * @param to
     * @param from
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws AddressException
     */
    public void sendHtml(String host,
            String to,
            String from,
            String subject,
            String body)
            throws MessagingException, AddressException {

        MimeMessage msg = createMessage();
        setHeaders(msg, to, from);
        try {
            msg.setSubject(MimeUtility.encodeText(Win2Jis(subject), "ISO-2022-JP", "B"));
        } catch (java.io.UnsupportedEncodingException e) {
            //  		何もしない
        }
        body += " ";// ? が付くのをフィックスした
        msg.setContent(Win2Jis(body), "text/html; charset=iso-2022-jp");	// html
        msg.saveChanges();
        send(msg);
    }

    //////////////////////////////////////////////////////////////    	
    ///////////// クラスメソッド　　　　//////////////////////////
    //////////////////////////////////////////////////////////////
    /**
     * 即時送信 /POP-BEFORE-SMPT対応、ポート指定
     *
     * @param smtpUser
     * @param smtpPassword
     * @param host
     * @param port
     * @param to
     * @param from
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws AddressException
     */
    public static void sendPBS(String smtpUser,
            String smtpPassword,
            String host,
            int port,
            String to,
            String from,
            String subject,
            String body) throws MessagingException, AddressException {

        if (!JmAuthenticate.authenticate(host, smtpUser, smtpPassword)) {
            throw new MessagingException("Pop認証できない");
        }
        JmSender js = new JmSender();
        js.connect(host, port);
        js.send(to, from, subject, body);
        js.disconnect();
    }

    /**
     * 即時送信 / SMTP-AUTH対応
     *
     * @param smtpUser
     * @param smtpPassword
     * @param host
     * @param to
     * @param from
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws AddressException
     */
    public static void send(String smtpUser,
            String smtpPassword,
            String host,
            String to,
            String from,
            String subject,
            String body) throws MessagingException, AddressException {
        send(smtpUser, smtpPassword, host, -1, to, from, subject, body);

    }

    /**
     * 即時送信 / SMTP-AUTH対応、ポート指定
     *
     * @param smtpUser
     * @param smtpPassword
     * @param host
     * @param port
     * @param to
     * @param from
     * @param subject
     * @param body
     * @throws MessagingException
     * @throws AddressException
     */
    public static void send(String smtpUser,
            String smtpPassword,
            String host,
            int port,
            String to,
            String from,
            String subject,
            String body) throws MessagingException, AddressException {

        JmSender js = new JmSender(smtpUser, smtpPassword);
        js.connect(host, port);
        js.send(to, from, subject, body);
        js.disconnect();
    }

    /**
     * 即時送信 / SMTP-AUTH対応、添付ファイル付き
     *
     * @param smtpUser
     * @param smtpPassword
     * @param host
     * @param to
     * @param from
     * @param subject
     * @param body
     * @param fileDir
     * @param flist
     * @throws MessagingException
     * @throws AddressException
     * @throws IOException
     */
    public static void sendWF(String smtpUser,
            String smtpPassword,
            String host,
            String to,
            String from,
            String subject,
            String body,
            String fileDir, // ファイルのあるフォルダ名
            ArrayList<String> flist) // ファイル名のみ（添付するファイル名、複数指定可）
            throws MessagingException, AddressException, IOException {
        sendWF(smtpUser, smtpPassword, host, -1, to, from, subject, body, fileDir, flist);

    }

    /**
     * 即時送信 / SMTP-AUTH対応、ポート指定、添付ファイル付き
     *
     * @param smtpUser
     * @param smtpPassword
     * @param host
     * @param port
     * @param to
     * @param from
     * @param subject
     * @param body
     * @param fileDir
     * @param flist
     * @throws MessagingException
     * @throws AddressException
     * @throws IOException
     */
    public static void sendWF(String smtpUser,
            String smtpPassword,
            String host,
            int port,
            String to,
            String from,
            String subject,
            String body,
            String fileDir, // ファイル名を含まないパス（読み出し用）
            ArrayList<String> flist) // ファイル名のみ（添付ファイル名となる）
            throws MessagingException, AddressException, IOException {
        JmSender s = new JmSender(smtpUser, smtpPassword);
        s.connect(host, port);
        s.sendWF(to, from, subject, body, fileDir, flist);
        s.disconnect();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////　下請けメソッド　　　/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 複数添付ファイルの処理
     *
     * @param body
     * @param path
     * @param flist
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    protected MimeMultipart createAttachmentParts(String body, String path, ArrayList<String> flist) throws MessagingException, IOException {
        MimeMultipart mp = new MimeMultipart();

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(Win2Jis(body), "ISO-2022-JP");	// WindowsではCP932対応処理
        mp.addBodyPart(textPart);
        /*
         * ファイルパートの作成
         */
        for (int i = 0; i < flist.size(); i++) {
            /*
             * ファイルパスを調べて、ファイルが存在する時だけ
             * マルチパート処理する
             */
            String fileName = flist.get(i);
            String filePath = pathString(path) + flist.get(i);// 元の名前
            File fp = new File(filePath);

            if (fp.exists()) {
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.setDataHandler(new DataHandler(new FileDataSource(filePath)));
                setFileName(filePart, fileName, "ISO-2022-JP", "ja");
                mp.addBodyPart(filePart);
            }
        }
        return mp;
    }

    /**
     * 複数添付ファイルの処理 本文はHTML送信用
     *
     * @param body
     * @param path
     * @param flist
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    protected MimeMultipart createHtmlAndAttachmentParts(String body, String path, ArrayList<String> flist) throws MessagingException, IOException {
        MimeMultipart mp = new MimeMultipart();
        MimeBodyPart textPart = new MimeBodyPart();

        // html のための変更
        textPart.setContent(body, "text/html; charset=iso-2022-jp");
        mp.addBodyPart(textPart);
        /*
         * ファイルパートの作成
         */
        for (int i = 0; i < flist.size(); i++) {
            /*
             * ファイルパスを調べて、ファイルが存在する時だけ
             * マルチパート処理する
             */
            String fileName = flist.get(i);
            String filePath = pathString(path) + flist.get(i);// 元の名前
            File fp = new File(filePath);

            if (fp.exists()) {
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.setDataHandler(new DataHandler(new FileDataSource(filePath)));
                setFileName(filePart, fileName, "ISO-2022-JP", "ja");
                mp.addBodyPart(filePart);
            }
        }
        return mp;
    }

    /**
     * メッセージオブジェクトの作成
     *
     * @return
     */
    protected MimeMessage createMessage() {
        //logger.info("createMessage()");
        return new MimeMessage(session);
    }

    /**
     * ヘッダの付加　／送信者，受信者，メーラー （注意）to = "some1@foo.com,some2@foo.com,...."
     * のようにCSV形式で複数の送信先を設定可能
     *
     * @param msg
     * @param to
     * @param from
     * @throws MessagingException
     * @throws AddressException
     */
    protected void setHeaders(MimeMessage msg, String to, String from) throws MessagingException, AddressException {
        //msg.setFrom(null);
        msg.addFrom(InternetAddress.parse(from, true));
        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to, true));
        msg.setHeader("Content-Transfer-Encoding", "7bit"); // 2003.2.23 追加
        msg.setHeader("X-Mailer", "jmSender 1.1"); // 2003.2.23 up

    }

    /**
     * 送信の下請けメソッド
     *
     * @param msg
     * @throws MessagingException
     */
    protected void send(MimeMessage msg) throws MessagingException {
        send(msg, msg.getAllRecipients());
    }

    /**
     * 送信の下請けメソッド
     *
     * @param msg
     * @param envelopeTo
     * @throws MessagingException
     */
    public synchronized void send(MimeMessage msg, Address[] envelopeTo) throws MessagingException {
        msg.setSentDate(new Date());
        // メッセージIDをつける
        session.getProperties().put("mail.from", ((InternetAddress) msg.getFrom()[0]).getAddress());
        msg.saveChanges();
        //
        transport.sendMessage(msg, envelopeTo);
    }

    /**
     * 送信の下請けメソッド／切断
     */
    protected void finalize() throws Throwable {
        disconnect();
    }

    /**
     * 終端にFile.separatorをもつパス文字列を返す
     *
     * @param path
     * @return
     */
    String pathString(String path) {
        int n = path.length();
        String e = path.substring(n - 1);
        if (e.equals(File.separator)) {
            return path;
        }

        return path + File.separator;
    }

    /**
     * ファイル名の拡張子を返す
     *
     * @param fname	ファイル名文字列
     * @return	拡張子、無い時は ""
     */
    protected String getExt(String fname) {
        int pos = fname.lastIndexOf('.');
        if (pos < 0) {
            return "";
        }

        StringBuffer bf = new StringBuffer();
        int ln = fname.length();
        if (pos < ln - 1) {
            pos++;
        } else {
            return "";
        }
        for (int i = pos; i < ln; i++) {
            char c = fname.charAt(i);
            bf.append(c);
        }
        if (bf.length() == 0) {
            return "";
        }
        return "." + bf.toString();
    }

    protected String Win2Jis(String s) {
        return Cp932.isWindows() ? Cp932.forJisMail(s) : s;
    }

    //////////////////////////////////////////////////////////////////////
    ////////////////////   内部クラス　　//////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Authenticatorクラス 任意のユーザー名／パスワードの獲得方法を定義できる session の生成にこのクラスオブジェクトを使う
     */
    class AuthenticatorUtil extends javax.mail.Authenticator {

        private String strUser;
        private String strPwd;

        /**
         * コンストラクタでユーザー名／パスワードを獲得する仕様とした
         *
         * @param user
         * @param password
         */
        public AuthenticatorUtil(String user, String password) {
            this.strUser = user;
            this.strPwd = password;
        }

        /**
         * PasswordAuthenticationを返す オーバーライド必須
         */
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(strUser, strPwd);
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    ////  日本語添付ファイル名への対応
    ///   ozacc-mail library 
    //////////////////////////////////////////////////////////////////////////////
    /**
     *
     * 出典 ozacc-mail library http://sourceforge.jp/projects/spring-ext/ 2012.5
     *
     */
    /**
     * This method set Content-Disposition: with RFC2231 encoding. It is
     * required JavaMail1.2.
     */
    /**
     * Part#setFileName()のマルチバイト対応版です。 JavaMail1.2でなければコンパイルできません
     */
    public void setFileName(Part part, String filename, String charset, String lang)
            throws MessagingException {
        // Set the Content-Disposition "filename" parameter
        ContentDisposition disposition;
        String[] strings = part.getHeader("Content-Disposition");
        if (strings == null || strings.length < 1) {
            disposition = new ContentDisposition(Part.ATTACHMENT);
        } else {
            disposition = new ContentDisposition(strings[0]);
            disposition.getParameterList().remove("filename");
        }
        part.setHeader("Content-Disposition", disposition.toString()
                + encodeParameter("filename", filename, charset, lang));
        ContentType cType;
        strings = part.getHeader("Content-Type");
        if (strings == null || strings.length < 1) {
            cType = new ContentType(part.getDataHandler().getContentType());
        } else {
            cType = new ContentType(strings[0]);
        }
        try {
            // I want to public the MimeUtility#doEncode()!!!
            String mimeString = MimeUtility.encodeWord(filename, charset, "B");
            // cut <CRLF>...
            StringBuffer sb = new StringBuffer();
            int i;
            while ((i = mimeString.indexOf('\r')) != -1) {
                sb.append(mimeString.substring(0, i));
                mimeString = mimeString.substring(i + 2);
            }
            sb.append(mimeString);
            cType.setParameter("name", new String(sb));
        } catch (UnsupportedEncodingException e) {
            throw new MessagingException("Encoding error", e);
        }
        part.setHeader("Content-Type", cType.toString());
    }

    /**
     * This method encodes the parameter.
     * <P>
     * But most MUA cannot decode the encoded parameters by this method. <BR>
     * I recommend using the "Content-Type:"'s name parameter both.
     * </P>
     */
    /**
     * ヘッダのパラメタ部のエンコードを行います。
     * <P>
     * 現状は受信できないものが多いのでこのメソッドだけでは使えません。 <BR>
     * Content-Disposition:のfilenameのみに使用し、さらに Content-Type:のnameにMIME
     * encodingでの記述も行うのが妥当でしょう。 <BR>
     * パラメタは必ず行頭から始まるものとします。 (ヘッダの開始行から折り返された位置を開始位置とします)
     * </P>
     * <P>
     * foldingの方針はascii/non ascii境界のみをチェックします。 現状は連続するascii/non
     * asciiの長さのチェックは現状行っていません。 (エンコード後のバイト数でチェックしなければならないのでかなり面倒)
     * </P>
     *
     * @param name パラメタ名
     * @param value エンコード対象のパラメタ値
     * @param encoding 文字エンコーディング
     * @param lang 言語指定子
     * @return エンコード済み文字列 ";\r\n name*0*=ISO-8859-2''・・・;\r\n name*1*=・・"
     */
    // 1.全体をエンコードして長かったら半分に切ってエンコードを繰り返す
    public String encodeParameter(String name, String value, String encoding, String lang) {
        StringBuffer result = new StringBuffer();
        StringBuffer encodedPart = new StringBuffer();
        boolean needWriteCES = !isAllAscii(value);
        boolean CESWasWritten = false;
        boolean encoded;
        boolean needFolding = false;
        int sequenceNo = 0;
        int column;
        while (value.length() > 0) {
            // index of boundary of ascii/non ascii
            int lastIndex;
            boolean isAscii = value.charAt(0) < 0x80;
            for (lastIndex = 1; lastIndex < value.length(); lastIndex++) {
                if (value.charAt(lastIndex) < 0x80) {
                    if (!isAscii) {
                        break;
                    }
                } else {
                    if (isAscii) {
                        break;
                    }
                }
            }
            if (lastIndex != value.length()) {
                needFolding = true;
            }
            RETRY:
            while (true) {
                encodedPart.setLength(0);
                String target = value.substring(0, lastIndex);
                byte[] bytes;
                try {
                    if (isAscii) {
                        bytes = target.getBytes("us-ascii");
                    } else {
                        bytes = target.getBytes(encoding);
                    }
                } catch (UnsupportedEncodingException e) {
                    bytes = target.getBytes(); // use default encoding
                    encoding = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
                }
                encoded = false;
                // It is not strict.
                column = name.length() + 7; // size of " " and "*nn*=" and ";"
                for (int i = 0; i < bytes.length; i++) {
                    if ((bytes[i] >= '0' && bytes[i] <= '9')
                            || (bytes[i] >= 'A' && bytes[i] <= 'Z')
                            || (bytes[i] >= 'a' && bytes[i] <= 'z') || bytes[i] == '$'
                            || bytes[i] == '.' || bytes[i] == '!') {
                        encodedPart.append((char) bytes[i]);
                        column++;
                    } else {
                        encoded = true;
                        encodedPart.append('%');
                        String hex = Integer.toString(bytes[i] & 0xff, 16);
                        if (hex.length() == 1) {
                            encodedPart.append('0');
                        }
                        encodedPart.append(hex);
                        column += 3;
                    }
                    if (column > 76) {
                        needFolding = true;
                        lastIndex /= 2;
                        continue RETRY;
                    }
                }
                result.append(";\r\n ").append(name);
                if (needFolding) {
                    result.append('*').append(sequenceNo);
                    sequenceNo++;
                }
                if (!CESWasWritten && needWriteCES) {
                    result.append("*=");
                    CESWasWritten = true;
                    result.append(encoding).append('\'');
                    if (lang != null) {
                        result.append(lang);
                    }
                    result.append('\'');
                } else if (encoded) {
                    result.append("*=");
                    /*
                     * 本当にcharacter encodingは先頭パートに書かないとだめなのか? if (encoded) {
                     * result.append("*="); if (!CESWasWritten && needWriteCES) {
                     * CESWasWritten = true;
                     * result.append(encoding).append('\''); if (lang != null)
                     * result.append(lang); result.append('\''); }
                     */
                } else {
                    result.append('=');
                }
                result.append(new String(encodedPart));
                value = value.substring(lastIndex);
                break;
            }
        }
        return new String(result);
    }

    /**
     * check if contains only ascii characters in text.
     */
    public static boolean isAllAscii(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) > 0x7f) { // non-ascii
                return false;
            }
        }
        return true;
    }

}
