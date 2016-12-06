package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Bb implements Serializable {

    private List<Meibo> meibo;			// MeiboオブジェクトのList

    {	// 初期化リスト
        meibo = new ArrayList<>();	// Listを生成してデータをセット
        meibo.add(new Meibo(100, "田中宏", "tanaka@mail.jp"));
        meibo.add(new Meibo(200, "佐藤修", "satoh@mail.jp"));
        meibo.add(new Meibo(300, "鈴木太郎", "suzuki@mail.jp"));
        meibo.add(new Meibo(400, "山田弘子", "yamada@mail.jp"));
        meibo.add(new Meibo(500, "藤本花子", "fujimoto@mail.jp"));
    }

    // クリックした行のオブジェクトを得て編集する
    public String edit(Meibo obj) {
        String mail = obj.getMail();
        obj.setMail(mail + "★");
        return null;
    }

    public String next() {
        // 実際にはここでデータベースなどに保存する
        return null;
    }

    // セッターとゲッター
    public List<Meibo> getMeibo() {
        return meibo;
    }

    public void setMeibo(List<Meibo> meibo) {
        this.meibo = meibo;
    }
}
