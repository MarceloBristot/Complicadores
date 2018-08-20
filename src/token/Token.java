package token;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Token {
    private int cod;
    private String word;
    private static Stack<Map> tokenStack = new Stack();
    private static Map<Integer,String> token = new HashMap<>();


    public Token(){
        super();
    }

    public static Stack<Map> Compile(String code){
        //Variável para armazenar a palavra
        String word = "";

        //Variável auxiliar para armazenar tipo do caracter
        String type = "";

        //Booleano para saber se é literal
        boolean isLit = false;

        //Analisar cada caracter do código
        for(Character c : code.toCharArray()){
            //Novo ciclo de análise
            if(word == ""){
                type = EncontraTipo(c);
            }
            //Caso seja início/fim de literal
            if(c.toString().equals("'") || isLit){
                word += c;
                if(c.toString().equals("'")){
                    if(isLit){
                        ArmazenaToken(word,"L");
                        word = "";
                    }
                    isLit = !(isLit);
                }
            }
            //Alfabético
            else if(type == "A"){
                if(c.isLetterOrDigit(c))
                    word += c;
                else{
                    //Checa se caracter é válido
                    IsDel(c.toString());
                    //Salva a palavra
                    ArmazenaToken(word, "A");
                    //Zera palavra para novo ciclo
                    word = "";
                    if(IsDelComp(c.toString())){
                        type = "D";
                        word = c.toString();
                        //Salvar a palavra e aguardar, pode ou não ser delimitador composto
                    }
                    else if (!(c.toString().equals(" ")) || !(c.toString().equals("\n"))){
                        ArmazenaToken(c.toString(), "D");
                    }
                }
            }

            //Numérico
            else  if(type == "N"){
                if(c.isDigit(c))
                    word += c.toString();
                //Erro de iniciar variável com dígito/concatenar dígito-letra
                else if(c.isAlphabetic(c))
                    System.out.println("ERRO");
                else{
                    //Checa se caracter é válido
                    IsDel(c.toString());
                    //Salva a palavra
                    ArmazenaToken(word, "N");
                    //Zera palavra para novo ciclo
                    word = "";
                    if(IsDelComp(c.toString())){
                        type = "D";
                        word = c.toString();
                        //Salvar a palavra e aguardar, pode ou não ser delimitador composto
                    }
                    else if (!(c.toString().equals(" ")) || !(c.toString().equals("\n"))){
                        ArmazenaToken(c.toString(), "D");
                    }
                }
            }

            //Delimitador
            else if(type == "D"){
                //Caso tanto o item anterior quanto o atual sejam delimitadores, então é um símbolo composto
                if(IsDelComp(word)){
                    if(IsDel(c.toString()))
                        word += c.toString();
                    ArmazenaToken(word, "D");
                    word = "";
                }
//                else{
                 else if(!(c.toString().equals(" ")) && !(c.toString().equals("\n"))){
                        ArmazenaToken(c.toString(),"D");
                    }
//                    else
//                        ArmazenaToken(word,"D");
                    if(c.isAlphabetic(c)){
                        word += c.toString();
                        type = "A";
                    }
                    else if(c.isDigit(c)){
                        word += c.toString();
                        type = "N";
                    }
                }
//            }
            else
                System.out.println("ERRO");
        }
        return tokenStack;
    }

    //Checa se caracter é delimitador
    private static boolean IsDel(String c){
//        switch(c){
//            case '+': break;
//            default:JOptionPane.showMessageDialog(null,"Caracter " + c + " não registrado!","Erro!",JOptionPane.ERROR_MESSAGE); return false;
//        }
        if(c.equals("+") || c.equals("-") || c.equals("*")|| c.equals("/")|| c.equals("[") || c.equals("]") || c.equals("(") || c.equals(")") || c.equals(":") || c.equals("=") || c.equals("<") || c.equals(">") || c.equals(",") || c.equals(".") || c.equals("$") || c.equals("\n"))
            return true;
        else
            return false;
    }

    //Checa se caracter é delimitador com possibilidade de ser composto
    private static boolean IsDelComp(String c){
        if(c.equals("<") || c.equals(">") || c.equals(".") || c.equals(":"))
            return true;
        else
            return false;
    }

    //Armazena Token em pilha
    private static Map<Integer, String> ArmazenaToken(String word, String type){
        if(word.equals(" ")){return null;}
        //word = word.toUpperCase();
        switch (word.toUpperCase()){
            case "PROGRAM": token.put(1, "PROGRAM");break;
            case "LABEL": token.put(2, "LABEL");break;
            case "CONST": token.put(3, "CONST");break;
            case "VAR": token.put(4, "VAR");break;
            case "PROCEDURE": token.put(5, "PROCEDURE");break;
            case "BEGIN": token.put(6, "BEGIN");break;
            case "END": token.put(7, "END");break;
            case "INTEGER": token.put(8, "INTEGER");break;
            case "ARRAY": token.put(9, "ARRAY");break;
            case "OF": token.put(10, "OF");break;
            case "CALL": token.put(11, "CALL");break;
            case "GOTO": token.put(12, "GOTO");break;
            case "IF": token.put(13, "IF");break;
            case "THEN": token.put(14, "THEN");break;
            case "ELSE": token.put(15, "ELSE");break;
        }
        System.out.println(word);
        return null;
        //TODO Criar função de armazenamento de Tokens
    }

    private static String EncontraTipo(Character c){
        if(c.isAlphabetic(c))
            return "A";
        else if(c.isDigit(c))
            return "N";
        else
            return "D";
    }

}
