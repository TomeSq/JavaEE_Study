package beans;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private String name;			// 氏名
    private Integer year;			// 生年
    private Integer sex;			// 性別
    private String region;			// 地域
    private String[] interest;		// 興味のある分野
    private boolean magazine;		// メルマガを受け取るかどうか

    // 次の「初期化ブロック」に書いたコードはコンストラクタの前に実行される
    // フィールド変数に既定値をセットするのに使用している
    //（未入力でnullにならないようにする意味もある）
    // 初期化しておくと初期画面にこれらの値が反映されることにも注意する
    {
        region = "関東";
        magazine = false;
    }

    // 性別を文字列にして返す

    public String getStringSex() {
        if(1 == this.sex){
            return "男性";
        }
        return "女性";
    }

    // 関心のある分野を文字列にして返す

    public String getStringInterests() {
        StringBuilder buf = new StringBuilder();
        
        for(String inter : interest){
            if(buf.toString().length() <= 0){
                buf.append(inter);
            } else {
                buf.append("　").append(inter);
            }
        }
        
        return buf.toString();
    }

    // メルマガの要・不要を文字列にして返す

    public String getStringMagazine() {
        if(this.magazine){
            return "受け取る";
        }
        return "受け取らない";
    }
    // 生年のMap
    private static Map<Integer, Integer> yearItems;

    static {
        yearItems = new LinkedHashMap<>();
        for (int i = 1940; i < 2000; i++) {
            yearItems.put(i, i);
        }
    }

    public Map<Integer, Integer> getYearItems() {
        return yearItems;
    }
    // 地域のMap
    private static Map<String, String> regionItems;

    static {
        regionItems = new LinkedHashMap<>();
        regionItems.put("北海道", "北海道");
        regionItems.put("東北", "東北");
        regionItems.put("関東", "関東");
        regionItems.put("中部", "中部");
        regionItems.put("関西", "関西");
        regionItems.put("中国", "中国");
        regionItems.put("四国", "四国");
        regionItems.put("九州・沖縄", "九州・沖縄");
    }

    public Map<String, String> getRegionItems() {
        return regionItems;
    }
    // 関心のある分野
    private static SelectItem[] interestItems = {
        new SelectItem("政治", "政治"),
        new SelectItem("社会", "社会"),
        new SelectItem("経済", "経済"),
        new SelectItem("歴史", "歴史"),
        new SelectItem("文化", "文化"),
        new SelectItem("芸能", "芸能"),
        new SelectItem("スポーツ", "スポーツ")
    };

    public SelectItem[] getInterestItems() {
        return interestItems;
    }

    public String next() {
        return "output.xhtml";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String[] getInterest() {
        return interest;
    }

    public void setInterest(String[] interest) {
        this.interest = interest;
    }

    public boolean isMagazine() {
        return magazine;
    }

    public void setMagazine(boolean magazine) {
        this.magazine = magazine;
    }

}
