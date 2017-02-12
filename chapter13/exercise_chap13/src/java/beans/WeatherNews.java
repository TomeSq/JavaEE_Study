package beans;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class WeatherNews {
	// 必要なEJB（WeatherFeedとMailSender）をインジェクトする
    @EJB
    WeatherFeed feed;
    
    @EJB
    MailSender sernder;

	// タイマーサービスを使って指定時間にメールを出すための@Scheduleアノテーションを追加する
    @Schedule(hour="*/6")
    public void send() { // 自分あてに天気情報を送信する処理
        sernder.send("", "yahoo天気・災害・東京都の天気", feed.rss(13));
    }
}
