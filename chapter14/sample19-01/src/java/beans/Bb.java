package beans;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {

    private Integer number;
    private String name;

    {
        System.out.println("★初期化ブロック");
    }  // 初期化ブロック
    @Inject
    Logger log;

    public Bb() {
        System.out.println("★コンストラクタ/");
    }

    @PostConstruct
    public void start() {
        log.info("★オブジェクト構築完了後の処理");
    }

    @PreDestroy
    public void end() {
        log.info("★オブジェクト廃棄直前の処理");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
