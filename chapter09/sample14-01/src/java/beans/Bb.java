package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import util.FileUtil;

@Named
@RequestScoped
public class Bb {

    private String text;	// テキストを入れる文字列変数

    {
        text = FileUtil.getText("/resources/data/toshishun.txt"); // 読み込み
    }

    public String next() {
        save();				// 保存
        return null;		// index.xhtmlを再表示
    }

    public void save() {
        FileUtil.putText(text, "/resources/data/toshishun.txt"); // 書き出し
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
