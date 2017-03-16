package beans;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class WeatherNews {

    @EJB
    WeatherFeed feed;

    // イベントオブジェクトをインジェクトする
    @Schedule(hour = "*/6", persistent = false)
    public void send() {
        // イベントを投げる（発火する）処理を書く

        System.out.println("★タイマー実行");
    }
}
