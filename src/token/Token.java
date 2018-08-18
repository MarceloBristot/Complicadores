package token;

public class Token {
    private int cod;
    private String word;

    public Token(){
        super();
    }

    public static void Compile(String code){
        //Variável para armazenar a palavra
        String word = "";

        //Variável auxiliar provisória para armazenar caracter anterior
        String c0 = "";

        //Analisar cada caracter do código
        for(Character c : code.toCharArray()){
            //if(c0 == "")

            //Caso seja letra
            if(c.isAlphabetic(c)){
                word += c.toString();
            }
            //Caso seja dígito
            else if(c.isDigit(c))
                word += c.toString();
            //TODO Tratar erro de "variável" que começa com número

            //Caso seja delimitador
            else if(IsDel(c)){
                System.out.println("Del");
                //Caso possa ser sinal composto
                if(c.toString().equals("<") && (c0.equals(">") || c0.equals("=")))
                    System.out.println("<>");
                else if(c.toString().equals(">") && c0.equals("="))
                    System.out.println(">=");
                else if(c.toString().equals(".") && c0.equals("."))
                    System.out.println("..");
                else if(c.toString().equals(":") && c0.equals("="))
                    System.out.println(":=");

                    word = c.toString();
            }

            c0 = c.toString();
        }

    }

    //Checa se caracter é delimitador
    private static boolean IsDel(Character c){
//        switch(c){
//            case '+': break;
//            default:JOptionPane.showMessageDialog(null,"Caracter não registrado!","Erro!",JOptionPane.ERROR_MESSAGE); return false;
//        }
        if(c.equals("+") || c.equals("-") || c.equals("*")|| c.equals("/")|| c.equals("[") || c.equals("]") || c.equals("(") || c.equals(")") || c.equals(":") || c.equals("=") || c.equals("<") || c.equals(">") || c.equals(",") || c.equals(":") || c.equals(".") || c.equals("$"))
            System.out.println("Olá");
        return true;
    }

    //Armazena Token em pilha
    private static void ArmazenaToken(){
        //TODO Criar função de armazenamento de Tokens
    }

}
