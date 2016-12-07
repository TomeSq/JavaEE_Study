package beans;

public class Book {

    private String title;
    private String author;
    private String publisher;
    private String date;
    private boolean editable;

    public Book() {
    }

    public Book(String[] s) {
        // nullでなければ引数の配列要素を代入し、nullなら空文字列を代入する
        this.title = s[0] != null ? s[0] : "";
        this.author = s[1] != null ? s[1] : "";
        this.publisher = s[2] != null ? s[2] : "";
        this.date = s[3] != null ? s[3] : "";
    }

    @Override
    public String toString() {
        return title + "/" + author + "/" + publisher + "/" + date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String Publisher) {
        this.publisher = Publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}
