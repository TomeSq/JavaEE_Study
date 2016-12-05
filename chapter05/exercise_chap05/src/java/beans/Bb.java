package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Bb {
    //フィールド変数を作成する
    private String name;
    private Double weight;
    private Double height;
    private String msg;

    public String next() {
        return "output.xhtml";
    }

    public double getBmi() {
        return weight / (height * height);
    }

    public String getBmiMessage() {
        double bmi = getBmi();
        
        String bmiMessage;
        
        if(bmi < 20){
            bmiMessage = "やせ気味";
        } else if(20 <= bmi && bmi < 24){
            bmiMessage = "普通";
        } else if(24 <= bmi && bmi < 26.5){
            bmiMessage = "太り気味";
        } else {
            bmiMessage = "太り過ぎ";
        }
        return bmiMessage;
    }
	// ゲッターとセッターを作成する

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
