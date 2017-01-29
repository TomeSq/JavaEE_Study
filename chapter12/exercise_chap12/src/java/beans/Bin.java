package beans;

public class Bin {

    //toBinメソッドは引数の10進数aを２進数表記の文字列にして返す
    public String toBin(int a) {
        return fix(bin(a));
    }

    private String fix(String s) {
        String z = "00000000000000000000000000000000" + s;
        return z.substring(s.length());
    }

    private String bin(int a) {
        String s = Integer.toBinaryString(a);
        return s;
    }
}
