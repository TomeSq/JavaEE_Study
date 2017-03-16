package beans;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;

@Stateless
public class WeatherNews {

    @EJB
    WeatherFeed feed;

    @EJB
    private Event<String> event;
    
    // イベントオブジェクトをインジェクトする
    @Schedule(hour = "*/6", persistent = false)
    public void send() {
        // イベントを投げる（発火する）処理を書く
        event.fire(feed.rss(13));//東京の天気
        System.out.println("★タイマー実行");
    }
}
