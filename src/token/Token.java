package token;

import compilador.Compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Token {
    private int cod;
    private String word;
    private static Stack<Map> tokenStack = new Stack();

    //Booleano para saber se é comentário
    private static boolean isCom = false;


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

        //Mensagem
        System.out.println("Realizando análise léxica...");

        //Limpar tela de apresentação de tokens
        Compilador.MostraToken(null,null);

        //Começa como não comentado
        isCom = false;

        if(code.equals("")){
            Compilador.Erro("Código em branco!");
        }

        //Analisar cada caracter do código
        for(Character c : code.toCharArray()){
            //Novo ciclo de análise
            if(word == ""){
                type = encontraTipo(c);
            }
            if(word == "(*" || isCom){
                if(word.equals("*") && c.toString().equals(")")){
                    isCom = false;
                    ArmazenaToken("COMENTÁRIO","C");
                    word = "";
                    continue;
                }
                word = c.toString();
            }
            //Caso seja início/fim ou literal
            else if(c.toString().equals("'") || isLit){
                if(isLit)
                    word += c;
                //Caso seja início/fim
                if(c.toString().equals("'")){
                    //Caso seja fim
                    if(isLit){
                        ArmazenaToken(word,"L");
                        word = "";
                    }
                    //Caso seja início
                    else{
                        ArmazenaToken(word,type);
                        word = c.toString();
                    }
                    isLit = !(isLit);
                }
            }
            //Alfabético
            else if(type == "A"){
                if(c.isLetterOrDigit(c) || c.toString().equals("_"))
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
                    else if (!(c.toString().equals(" ")) && !(c.toString().equals("\n"))){
                        ArmazenaToken(c.toString(), "D");
                    }
                }
            }

            //Numérico
            else  if(type == "N"){
                if(c.isDigit(c))
                    word += c.toString();
                //Erro de iniciar variável com dígito/concatenar dígito-letra
                else if(c.isAlphabetic(c) || c.toString().equals("_")){
                    Compilador.Erro("Variável iniciando com dígito!");
                }
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
                    else if (!(c.toString().equals(" ")) && !(c.toString().equals("\n"))){
                        ArmazenaToken(c.toString(), "D");
                    }
                }
            }

            //Delimitador
            else if(type == "D"){
                //Booleano para evitar três símbolos
                boolean registrado = false;
                //Caso o item anterior seja delimitador possivelmente composto
                if(IsDelComp(word)){
                    //Caso o atual também seja, logo é um símbolo composto
                    if(SecComp(word,c.toString())){
                        word += c.toString();
                        registrado = true;
                    }
                    //Armazena variável anterior, não armazenada por não saber se seria composta ou não
                    //TODO Arrumar duplo composto
                    ArmazenaToken(word, "D");

                    //Se atual pode ser composto, não armazenar token, porém salvar caracter
                    //if(IsDelComp(c.toString())){
                        word = "";
                        type = encontraTipo(c);
                    //    }
                    //Caso contrário, armazenar
                    //else{
                    if((IsDelComp(c.toString())) && !(registrado)){
                        //ArmazenaToken(c.toString(), encontraTipo(c));
                        word = c.toString();
                    }
                }
                //Caso o caracter anterior não possa ser composto
                 else if(!(c.toString().equals(" ")) && !(c.toString().equals("\n"))){
                     if(IsDelComp(c.toString()))
                         word = c.toString();
                     else
                        ArmazenaToken(c.toString(),"D");
                    }
                    if(c.isAlphabetic(c) || c.toString().equals("_")){
                        word += c.toString();
                        type = "A";
                    }
                    else if(c.isDigit(c)){
                        word += c.toString();
                        type = "N";
                    }
                }
                //Nenhuma das anteriores
            else
                System.out.println("ERRO - SEM DESCRIÇÃO");
        }
        //Caso a última palavra não tenha sido armazenada
        if(word != "" && !(isCom))
            ArmazenaToken(word, type);
        //Caso o comentário não tenha sido finalizado
        if(isCom)
            Compilador.Erro("Comentário não finalizado!");
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
        if(c.equals("<") || c.equals(">") || c.equals(".") || c.equals(":") || c.equals("("))
            return true;
        else
            return false;
    }

    private static boolean SecComp(String c0, String c){
        if(c0.equals("<") && (c.equals(">") || c.equals("=")))
            return true;
        else if(c0.equals(">") && c.equals("="))
            return true;
        else if(c0.equals(".") && c.equals("."))
            return true;
        else if(c0.equals(":") && c.equals("="))
            return true;
        else if (c0.equals("(") && c.equals("*")){
            isCom = true;
            return true;
        }
        else
            return false;
    }

    //Armazena Token em pilha
    private static Map<Integer, String> ArmazenaToken(String word, String type){
        Map<Integer,String> token = new HashMap<>();
        if(word.equals(" ")||word.equals("")){return null;}
        //word = word.toUpperCase();
        if(type == "A"){
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
                case "WHILE": token.put(16,"WHILE");break;
                case "DO": token.put(17,"DO");break;
                case "REPEAT": token.put(18,"REPEAT");break;
                case "UNTIL": token.put(19,"UNTIL");break;
                case "READLN": token.put(20,"READLN");break;
                case "WRITELN": token.put(21,"WRITELN");break;
                case "OR": token.put(22,"OR");break;
                case "AND": token.put(23,"AND");break;
                case "NOT": token.put(24,"NOT");break;
                case "FOR": token.put(27,"FOR");break;
                case "TO": token.put(28,"TO");break;
                case "CASE": token.put(29,"CASE");break;
                default: token.put(25,"IDENTIFICADOR");break;
            }
        }
        else if(type == "N")
            token.put(26,"INTEIRO");
        else if(type == "L")
            token.put(48,"LITERAL");
        else if(type == "D"){
            switch (word){
                case "+": token.put(30, "+");break;
                case "-": token.put(31, "-");break;
                case "*": token.put(32, "*");break;
                case "/": token.put(33, "/");break;
                case "[": token.put(34, "[");break;
                case "]": token.put(35, "]");break;
                case "(": token.put(36, "(");break;
                case ")": token.put(37, ")");break;
                case ":=": token.put(38, ":=");break;
                case ":": token.put(39, ":");break;
                case "=": token.put(40, "=");break;
                case ">": token.put(41, ">");break;
                case ">=": token.put(42, ">=");break;
                case "<": token.put(43, "<");break;
                case "<=": token.put(44, "<=");break;
                case "<>": token.put(45, "<>");break;
                case ",": token.put(46, ",");break;
                case ";": token.put(47, ";");break;
                case ".": token.put(49, ".");break;
                case "..": token.put(50, "..");break;
                case "$": token.put(51, "$");break;
                case "(*": return null;
                default:
                    Compilador.Erro("Erro - Caracter "+ word +" invalido");break;
            }
        }
        else if(type == "C"){
            System.out.println(word);
            return null;
        }
        else {
            System.err.println("ERRO - SEM REGISTRO VÁLIDO");
            return null;
        }
        System.out.println(token + " - " + word);
        Compilador.MostraToken(token, word);
        tokenStack.add(token);
        return null;
    }

    private static String encontraTipo(Character c){
        if(c.isAlphabetic(c) || c.toString().equals("_"))
            return "A";
        else if(c.isDigit(c))
            return "N";
        else
            return "D";
    }

}
