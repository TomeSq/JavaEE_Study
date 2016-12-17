package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * テキストファイルの入力と出力を行うクラスメソッド<br/>
 *
 * @author t.kawaba@gmail.com
 */
public class FileUtil {

    static final Logger log = Logger.getLogger(FileUtil.class.getName());

    /**
     * テキストファイルの全内容を読みだして文字列として返す
     *
     * @param fpath テキストファイルのパス<br/>
     * resourcesからの相対パスで指定する（/resources/～）
     * @return	テキスト
     */
    public static String getText(String fpath) {
        String path = getRealPath(fpath);		// 絶対パスに変換
        StringBuilder text = new StringBuilder();
        try (InputStream is = new FileInputStream(path);
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "Shift_Jis"));) {

            String line;
            while ((line = in.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            log.severe("★ファイルが見つからない:" + fpath);
        }
        return text.toString();
    }

    /**
     * テキストをファイルに書き出す
     *
     * @param text 出力するテキスト
     * @param fpath テキストファイルのパス<br/>
     * resourcesからの相対パスで指定する（/resources/～）
     */
    public static void putText(String text, String fpath) {
        String path = getRealPath(fpath);		// 絶対パスに変換
        try (OutputStream os = new FileOutputStream(path);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "Shift_Jis"));) {
            out.write(text);
        } catch (IOException e) {
            log.severe("★ファイルを出力できない:" + fpath);
        }
    }

    /**
     * I/Oで使用する絶対パスを求める
     *
     * @param path	アプリケーションルートのリソースからの相対パス（/resources/～）
     * @return	絶対パス
     */
    public static String getRealPath(String path) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return ctx.getRealPath(path);
    }
}
