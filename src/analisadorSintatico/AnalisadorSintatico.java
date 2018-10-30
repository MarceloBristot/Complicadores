package analisadorSintatico;

import compilador.Compilador;
import token.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AnalisadorSintatico {
    public static Stack<Map> analisador = new Stack<>();
    public static Stack<Map> stack = new Stack<>();
    public AnalisadorSintatico() {
        super();
    }

    public static Stack<Map> Debugar(boolean first){

        if(first){
            stack = Token.Compile(Compilador.GetCode());
            stack = Reverter(stack);

            analisador.clear();
            Map<Integer,String> programa = new HashMap<>();
            programa.put(52,"PROGRAMA");
            analisador.push(programa);
        }

        else {
            if (stack.isEmpty()) {
                if(!analisador.isEmpty())
                    Compilador.Erro("Código faltando!");
                else
                    Compilador.Valido("Fim");
                return null;
            }
            boolean compare = Compare(analisador.lastElement(), stack.lastElement());
            if (!(compare))
                return null;

            //TODO MELHORAR MÉTODO
            Stack<Map> aux = new Stack<>();
            for(Map<Integer,String> i : analisador) {
                aux.push(i);
            }
            Compilador.TextoSintatico(aux.toString());
        }
        return null;
    }

    private static Stack<Map> PilhaToken(String code){
        stack = Token.Compile(code);

        System.out.println("Realizando análise sintática...");
        //Inverte a pilha
        stack = Reverter(stack);

        //TODO MELHORAR MÉTODO DE ARMAZENAMENTO
        Map<Integer,String> programa = new HashMap<>();
        programa.put(52,"PROGRAMA");
        analisador.clear();
        analisador.push(programa);
        return stack;
    }

    public static Stack<Map> Analisar(String code) {
        //Realiza análise léxica e armazena em uma Stack
        //Stack<Map> stack = Token.Compile(code);
        stack = Token.Compile(code);

        System.out.println("Realizando análise sintática...");
        //Inverte a pilha
        stack = Reverter(stack);

        //TODO MELHORAR MÉTODO DE ARMAZENAMENTO
        Map<Integer,String> programa = new HashMap<>();
        programa.put(52,"PROGRAMA");
        analisador.push(programa);

        while(!(analisador.isEmpty())){
            if(stack.isEmpty()){
                Compilador.Erro("Código faltando!");
            return null;}
            boolean compare = Compare(analisador.lastElement(),stack.lastElement());
            if(!(compare))
                return null;
        }
        Compilador.Valido("Exemplo válido!");
        return stack;
    }

    public static Stack Reverter(Stack pilha) {
        Stack aux = new Stack();

        while (!(pilha.isEmpty()))
            aux.push(pilha.pop());

        return aux;
    }

    private static boolean IsTerminal(Map token) {
        int code = GetKey(token);
            if (code <= 51)
                return true;
            else
                return false;
    }

    private static boolean Compare(Map analise, Map programa) {
        System.out.println("Análise: " + analise + " Programa: " + programa);
        if (IsTerminal(analise)) {
            if (GetKey(analise) == GetKey(programa)) {
                //Tá certo
                analisador.pop();
                stack.pop();
            } else{
                Compilador.Erro("NÃO TÁ CERTO: " + programa);
                return false;
            }
        } else {
            AnalisaPilha(GetKey(analise),GetKey(programa));
        }
        return true;
    }

    private static int GetKey(Map token){
        for(Object code : token.keySet()) {
            return (Integer) code;
        }
        return 0;
    }

    private static void AnalisaPilha(int keyAn, int keyPr){
        int array[] = new int[]{};
        switch (keyAn){
            case 52:
                if(keyPr == 1) {
                    array = new int[]{49, 53, 47, 25, 1};
//                    analisador.push(EncontraMap(49));
//                    analisador.push(EncontraMap(53));
//                    analisador.push(EncontraMap(47));
//                    analisador.push(EncontraMap(25));
//                    analisador.push(EncontraMap(1));
                }break;
            case 53:
                if((keyPr == 2) || (keyPr == 3) || (keyPr == 4) || (keyPr == 5) || (keyPr == 6)){
                    array = new int[] {64,62,59,57,54};
                }break;
            case 54:
                if(keyPr == 2)
                    array = new int[] {47,55,2};
                else if((keyPr == 3) || (keyPr == 4) || (keyPr == 5) || (keyPr == 6))
                    array = new int[] {};
                break;
            case 55:
                if(keyPr == 25)
                    array = new int[]{56,25};
                break;
            case 56:
                if(keyPr == 39 || keyPr == 47)
                    array = new int[] {};
                else if(keyPr == 46)
                    array = new int[] {56,25,46};
                break;
            case 57:
                if(keyPr == 4 || keyPr == 5 || keyPr == 6)
                    array = new int[] {};
                else if(keyPr == 3)
                    array = new int[] {58,47,26,40,25,3};
                break;
            case 58:
                if(keyPr == 4 || keyPr == 5 || keyPr == 6)
                    array = new int[] {};
                else if(keyPr == 25)
                    array = new int[]{58,47,26,40,25};
                break;
            case 59:
                if(keyPr == 4)
                    array = new int[]{60,47,61,39,55,4};
                else if(keyPr == 5 || keyPr == 6)
                    array = new int[]{};
                break;
            case 60:
                if(keyPr == 5 || keyPr == 6)
                    array = new int[]{};
                else if(keyPr == 25)
                    array = new int[]{60,47,61,39,55};
                break;
            case 61:
                if(keyPr == 8)
                    array = new int[]{8};
                else if(keyPr == 9)
                    array = new int[]{8,10,35,26,50,26,34,9};
                break;
            case 62:
                if(keyPr == 5)
                    array = new int[]{62,47,53,47,63,25,5};
                else if(keyPr == 6)
                    array = new int[]{};
                break;
            case 63:
                if(keyPr == 36)
                    array = new int[]{37,8,39,55,36};
                else if(keyPr == 39)
                    array = new int[]{};
                break;
            case 64:
                if(keyPr == 6)
                    array = new int[]{7,65,66,6};
                break;
            case 65:
                if(keyPr == 7)
                    array = new int[]{};
                else if(keyPr == 47)
                    array = new int[]{65,66,47};
                break;
            case 66:
                if(keyPr == 6)
                    array = new int[]{64};
                else if(keyPr == 7 || keyPr == 15 || keyPr == 19 || keyPr == 47)
                    array = new int[]{};
                else if(keyPr == 11)
                    array = new int[]{69,25,11};
                else if(keyPr == 12)
                    array = new int[]{25,12};
                else if(keyPr == 13)
                    array = new int[]{71,66,14,77,13};
                else if(keyPr == 16)
                    array = new int[]{66,17,77,16};
                else if(keyPr == 18)
                    array = new int[]{77,19,66,18};
                else if(keyPr == 20)
                    array = new int[]{37,74,72,36,20};
                else if(keyPr == 21)
                    array = new int[]{37,76,75,36,21};
                else if(keyPr == 25)
                    array = new int[]{67,25};
                else if(keyPr == 27)
                    array = new int[]{66,17,77,28,77,38,25,27};
                else if (keyPr == 29)
                    array = new int[]{7,84,10,77,29};
                break;
            case 67:
                if(keyPr == 34 || keyPr == 38)
                    array = new int[]{77,38,68};
                else if(keyPr == 39)
                    array = new int[]{66,39};
                break;
            case 68:
                if(keyPr == 34)
                    array = new int[]{35,77,34};
                else if(keyPr == 38)
                    array = new int[]{};
                break;
            case 69:
                if(keyPr == 7 || keyPr == 15 || keyPr == 19 || keyPr == 47)
                    array = new int[]{};
                else if(keyPr == 36)
                    array = new int[]{37,70,77,36};
                break;
            case 70:
                if(keyPr == 37)
                    array = new int[]{};
                else if(keyPr == 46)
                    array = new int[]{70,77,46};
                break;
            case 71:
                if(keyPr == 7 || keyPr == 19 || keyPr == 47)
                    array = new int[]{};
                else if(keyPr == 15)
                    array = new int[]{66,15};
                break;
            case 72:
                if(keyPr == 25)
                    array = new int[]{73,25};
                break;
            case 73:
                if(keyPr == 7 || keyPr == 10 || keyPr == 14 || keyPr == 15 || keyPr == 17 || keyPr == 19 || keyPr == 22 || keyPr == 23 || keyPr == 28 || (keyPr > 29 && keyPr < 34) || keyPr == 35 || keyPr == 37 || (keyPr > 39 && keyPr < 48))
                    array = new int[]{};
                else if(keyPr == 34)
                    array = new int[]{35,77,34};
                break;
            case 74:
                if(keyPr == 37)
                    array = new int[]{};
                else if(keyPr == 46)
                    array = new int[]{74,72,46};
                break;
            case 75:
                if(keyPr == 24 || keyPr == 25 || keyPr == 26 || keyPr == 30 || keyPr == 31 || keyPr == 36)
                    array = new int[]{77};
                else if(keyPr == 48)
                    array = new int[]{48};
                break;
            case 76:
                if(keyPr == 37)
                    array = new int[]{};
                else if(keyPr == 46)
                    array = new int[]{76,75,46};
                break;
            case 77:
                if(keyPr == 24 || keyPr == 25 || keyPr == 26 || keyPr == 30 || keyPr == 31 || keyPr == 36)
                    array = new int[]{78,79};
                break;
            case 78:
                if(keyPr == 7 || keyPr == 10 || keyPr == 14 || keyPr == 15 || keyPr == 17 || keyPr == 19 || keyPr == 28 || keyPr == 35 || keyPr == 37 || keyPr == 46 || keyPr == 47)
                    array = new int[]{};
                else if(keyPr > 39 && keyPr < 46)
                    array = new int[]{79,keyPr};
                break;
            case 79:
                if((keyPr > 23 && keyPr < 27) || keyPr == 36)
                    array = new int[]{80,81};
                else if(keyPr == 30 || keyPr == 31)
                    array = new int[]{80,81,keyPr};
                break;
            case 80:
                if(keyPr == 7 || keyPr == 10 || keyPr == 14 || keyPr == 15 || keyPr == 17 || keyPr == 19 || keyPr == 28 || keyPr == 35 || keyPr == 37 || (keyPr>39 && keyPr<48))
                    array = new int[]{};
                else if(keyPr == 22 || keyPr == 30 || keyPr == 31)
                    array = new int[]{80,81,keyPr};
                break;
            case 81:
                if((keyPr > 23 && keyPr < 27) || keyPr == 36)
                    array = new int[]{82,83};
                break;
            case 82:
                if(keyPr == 7 || keyPr == 10 || keyPr == 14 || keyPr == 15 || keyPr == 17 || keyPr == 19 || keyPr == 22 || keyPr == 28 || keyPr == 30 || keyPr == 31 || keyPr == 35 || keyPr == 37 || (keyPr>39 && keyPr<48))
                    array = new int[]{};
                else if(keyPr == 23 || keyPr == 32 || keyPr == 33)
                    array = new int[]{82,83,keyPr};
                break;
            case 83:
                if(keyPr == 24)
                    array = new int[]{83,24};
                else if(keyPr == 25)
                    array = new int[]{72};
                else if (keyPr == 26)
                    array = new int[]{26};
                else if (keyPr == 36)
                    array = new int[]{37,77,36};
                break;
            case 84:
                if(keyPr == 26)
                    array = new int[]{85,66,39,86,26};
                break;
            case 85:
                if(keyPr == 7)
                    array = new int[]{};
                else if(keyPr == 47)
                    array = new int[]{84,47};
                break;
            case 86:
                if(keyPr == 39)
                    array = new int[]{};
                else if(keyPr == 46)
                    array = new int[]{86,26,46};
                break;
            default:break;
        }
        System.out.println();
        System.out.println(analisador);
        analisador.pop();
        if(array.length > 0) {
            for (int i : array)
                analisador.push(EncontraMap(i));
        }
    }

    //Encontra Map relacionado
    private static Map<Integer, String> EncontraMap(int code){
        Map<Integer,String> token = new HashMap<>();
            switch (code){
                case 1: token.put(1, "PROGRAM");break;
                case 2: token.put(2, "LABEL");break;
                case 3: token.put(3, "CONST");break;
                case 4: token.put(4, "VAR");break;
                case 5: token.put(5, "PROCEDURE");break;
                case 6: token.put(6, "BEGIN");break;
                case 7: token.put(7, "END");break;
                case 8: token.put(8, "INTEGER");break;
                case 9: token.put(9, "ARRAY");break;
                case 10: token.put(10, "OF");break;
                case 11: token.put(11, "CALL");break;
                case 12: token.put(12, "GOTO");break;
                case 13: token.put(13, "IF");break;
                case 14: token.put(14, "THEN");break;
                case 15: token.put(15, "ELSE");break;
                case 16: token.put(16,"WHILE");break;
                case 17: token.put(17,"DO");break;
                case 18: token.put(18,"REPEAT");break;
                case 19: token.put(19,"UNTIL");break;
                case 20: token.put(20,"READLN");break;
                case 21: token.put(21,"WRITELN");break;
                case 22: token.put(22,"OR");break;
                case 23: token.put(23,"AND");break;
                case 24: token.put(24,"NOT");break;
                case 25: token.put(25,"IDENTIFICADOR");break;
                case 26: token.put(26,"INTEIRO");break;
                case 27: token.put(27,"FOR");break;
                case 28: token.put(28,"TO");break;
                case 29: token.put(29,"CASE");break;
                case 30: token.put(30, "+");break;
                case 31: token.put(31, "-");break;
                case 32: token.put(32, "*");break;
                case 33: token.put(33, "/");break;
                case 34: token.put(34, "[");break;
                case 35: token.put(35, "]");break;
                case 36: token.put(36, "(");break;
                case 37: token.put(37, ")");break;
                case 38: token.put(38, ":=");break;
                case 39: token.put(39, ":");break;
                case 40: token.put(40, "=");break;
                case 41: token.put(41, ">");break;
                case 42: token.put(42, ">=");break;
                case 43: token.put(43, "<");break;
                case 44: token.put(44, "<=");break;
                case 45: token.put(45, "<>");break;
                case 46: token.put(46,",");break;
                case 47: token.put(47,";");break;
                case 48: token.put(48,"LITERAL");break;
                case 49: token.put(49,".");break;
                case 50: token.put(50, "..");break;
                case 51: token.put(51, "$");break;
                //Não terminais
                case 53: token.put(53,"BLOCO");break;
                case 54: token.put(54,"DCLROT");break;
                case 55: token.put(55,"LID");break;
                case 56: token.put(56,"REPIDENT");break;
                case 57: token.put(57,"DCLCONST");break;
                case 58: token.put(58,"LDCCONST");break;
                case 59: token.put(59,"DCLVAR");break;
                case 60: token.put(60,"LDVAR");break;
                case 61: token.put(61,"TIPO");break;
                case 62: token.put(62,"DCLPROC");break;
                case 63: token.put(63,"DEFPAR");break;
                case 64: token.put(64,"CORPO");break;
                case 65: token.put(65,"REPCOMANDO");break;
                case 66: token.put(66,"COMANDO");break;
                case 67: token.put(67,"RCOMID");break;
                case 68: token.put(68,"RVAR");break;
                case 69: token.put(69,"PARAMETROS");break;
                case 70: token.put(70,"REPPAR");break;
                case 71: token.put(71,"ELSEPARTE");break;
                case 72: token.put(72,"VARIAVEL");break;
                case 73: token.put(73,"VARIAVEL1");break;
                case 74: token.put(74,"REPVARIAVEL");break;
                case 75: token.put(75,"ITEMSAIDA");break;
                case 76: token.put(76,"REPITEM");break;
                case 77: token.put(77,"EXPRESSAO");break;
                case 78: token.put(78,"REPEXPSIMP");break;
                case 79: token.put(79,"EXPSIMP");break;
                case 80: token.put(80,"REPEXP");break;
                case 81: token.put(81,"TERMO");break;
                case 82: token.put(82,"REPTERMO");break;
                case 83: token.put(83,"FATOR");break;
                case 84: token.put(84,"CONDCASE");break;
                case 85: token.put(85,"CONTCASE");break;
                case 86: token.put(86,"RPINTEIRO");break;
                case 87: token.put(87,"SEMEFEITO");break;
                default: Compilador.Erro("Não tá certo!");break;
            }
            return token;
        }
}
