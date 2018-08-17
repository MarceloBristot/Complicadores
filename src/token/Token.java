package token;

import java.util.regex.*;

public class Token {
    private int cod;
    private String word;

    public Token(){
        super();
    }

    public static void Compile(String code){
        String word = "";
        for(Character c : code.toCharArray()){
            if(!(isDel(c))){
                word += c.toString();
            }
            else{
                word = c.toString();
            }
        }

    }

    private static boolean isDel(Character c){
        if(c.equals("+") || c.equals("'"))
            System.out.println("Olá");
        return true;
    }

}
