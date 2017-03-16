package beans;

import java.io.Serializable;

public class Student implements Serializable {

    private Integer number;
    private String name;

    public Student(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return number + ":" + name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
