package beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import util.BookUtil;

@Named
@SessionScoped
public class Bb implements Serializable {

    private List<Book> books;			// MeiboオブジェクトのList

    {	// 初期化ブロック
        // ユーティリティを使って、本のリストを作成する
        // データはresourcesフォルダのdataにある
        books = BookUtil.getList("/resources/data/Book.csv");
    }

    public String next() {
        // 実際にはここでデータベースなどに保存する
        return null;
    }

    public String edit(Book book) {
        if(!book.isEditable()){
            book.setEditable(true);
        }
        return null;
    }

    // セッターとゲッター

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
