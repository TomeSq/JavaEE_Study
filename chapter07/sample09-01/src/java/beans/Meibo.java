package beans;

public class Meibo {

    private Integer number;	// 番号
    private String name;	// 名前
    private String mail;	// メール
    // コンストラクタ

    public Meibo() {
    }

    public Meibo(Integer number, String name, String mail) {
        this.number = number;
        this.name = name;
        this.mail = mail;
    }

    // セッターとゲッター

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
