package analisadorSintatico;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import compilador.Compilador;
import token.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AnalisadorSintatico {
    public static Stack<Map> analisador = new Stack<>();
    public AnalisadorSintatico() {
        super();
    }

    public static Stack<Map> Analisar(String code) {
        //Realiza análise léxica e armazena em uma Stack
        Stack<Map> stack = Token.Compile(code);

//        System.out.println("Realizando análise sintática...");
//        //Inverte a pilha
//        stack = Reverter(stack);
//
//        //TODO MELHORAR MÉTODO DE ARMAZENAMENTO
//        Map<Integer,String> programa = new HashMap<>();
//        programa.put(52,"PROGRAMA");
//        analisador.push(programa);
//        //
//        while(!(analisador.isEmpty())){
//            Compare(analisador.lastElement(),stack.lastElement());
//        }
        return stack;
    }

    private static Stack Reverter(Stack pilha) {
        Stack aux = new Stack();

        while (!(pilha.isEmpty()))
            aux.push(pilha.pop());

        return aux;
    }

    private static boolean IsTerminal(int code) {
        if (code <= 51)
            return true;
        else
            return false;
    }

    private static boolean Compare(Map analise, Map code) {
        if (IsTerminal(analise.hashCode())) {
            if (analise == code) {
                //Tá certo
            } else
                Compilador.Erro("NÃO TÁ CERTO, CACETA!");
        } else {

        }
        return true;
    }

}
