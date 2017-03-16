package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@SessionScoped
public class Meibo implements Serializable {

    @Inject
    private Event<Student> event;	// イベントオブジェクトを用意しておく
    List<Student> ls = new ArrayList<>();

    public void add(Integer number, String name) {
        Student st = new Student(number, name);
        ls.add(st);
        event.fire(st);	// 引数（Student型）をセットしてイベントを発火させる
    }
}
