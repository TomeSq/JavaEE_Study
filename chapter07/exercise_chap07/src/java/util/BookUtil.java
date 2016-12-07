package util;

import beans.Book;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class BookUtil {

    /**
     * ファイルデータからBookオブジェクトのListを作成して返す
     *
     * @param fpath CSV形式の書籍データファイル（書名、作者、出版社、出版年月）のパス<br/>
     * アプリケーションルートから相対パスで指定する（/resources/date/book.csv）
     * @return	ls Bookオブジェクトを要素として持つList
     */

    public static List<Book> getList(String fpath) {
        Logger log = Logger.getLogger(BookUtil.class.getName());
        String path = getRealPath(fpath);		// 絶対パスに変換
        List<Book> ls = new ArrayList<>();
        String line;
        try (InputStream is = new FileInputStream(path);
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));) {

            while ((line = in.readLine()) != null) {
                String[] s = line.split(",");
                if (s.length != 4) {
                    continue;
                }
                Book book = new Book(s);
                ls.add(book);
            }
        } catch (IOException e) {
            log.severe("★ファイルが見つからない:" + fpath);
        }
        return ls;
    }

    // I/Oで使用するパスを求める

    public static String getRealPath(String path) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return ctx.getRealPath(path);
    }
}
