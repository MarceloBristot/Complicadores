package analisadorSemantico;

import analisadorSintatico.AnalisadorSintatico;
import compilador.Compilador;
import token.Token;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalisadorSemantico {
    private static int nivel = 0;
    private static int nivelMax = 0;
    public static Map<Integer,ArrayList<Identificador>> listaIdentificador = new HashMap<>();
    public static List<Identificador> ids= new ArrayList<Identificador>();
    public static int numId = 0;
    public static int count = 0;

    public static int getCodigo() {
        return codigo;
    }

    public static void setCodigo(int codigo) {
        AnalisadorSemantico.codigo = codigo;
    }

    private static int codigo = 0;
    private static int tipoAnalise = 0;

    //public void setMaxNivel(int i){
    //    nivelMax = i;
    //}

    public static void limparAnalise(){
        AnalisadorSemantico.zeraNumId();
        AnalisadorSemantico.ids.clear();
        AnalisadorSemantico.listaIdentificador.clear();
        nivel = 0;
        codigo = 0;
        tipoAnalise = 0;
    }

    public static void addNivel(){
        nivel ++;
        deleteIds();
    }

    public static void setAnalise(int analise){
        switch (analise){
            case 0: tipoAnalise = 0; break;//Declaração
            case 1: tipoAnalise = 1; break;//Atribuição (Próximas devem ser comparação)
            case 2: tipoAnalise = 2; break;//Comparação
            default: tipoAnalise = analise; break;//Comparação
        }
    }

    public static void zeraNumId(){
        numId = 0;
    }

    public static void removeNivel(){
        if(nivel > 0) nivel--;
    }

    public static boolean buscaId(Identificador id){
        for (int i = id.nivel; i > -1; i--){
            List<Identificador> idList = listaIdentificador.get(i);
            if(idList == null) {
                listaIdentificador.put(nivel,new ArrayList<>());
                break;
            }
            else{
                for (Identificador item : idList) {
                    if ((id.nome.equals(item.nome)) && id.nivel == item.nivel){
                        id.categoria = item.categoria;
                        id.tipo = item.tipo;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean buscaIdExiste(Identificador id){
        for (int i = nivelMax; i > -1; i--){
            List<Identificador> idList = listaIdentificador.get(i);
            if(idList == null) {
                listaIdentificador.put(nivel,new ArrayList<>());
                break;
            }
            else{
                for (Identificador item : idList) {
                    if ((id.nome.equals(item.nome))){
                        id.categoria = item.categoria;
                        id.tipo = item.tipo;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void listaIds(){
        for (Identificador id:ids) {
            System.out.println(id.nome);
        }
    }

    public static void checaId(Identificador id){
        //Identificador id = new Identificador();
        //id.setNome(name);
        nivel = ids.get(numId).nivel;
        if(nivel > nivelMax)
            nivelMax = nivel;
        id.nivel = nivel;
        id.nome = ids.get(numId).nome;
        id.tipo = ids.get(numId).tipo;
        id.linha = ids.get(numId).linha;
        id.categoria = ids.get(numId).categoria;
        if(tipoAnalise < 2)
            tipoAnalise = 1;
        if(ids.get(numId).categoria > 0)
            tipoAnalise = 0;
        numId++;
        switch (tipoAnalise){
            case 0: addId(id); break;
            case 1: existeId(id); setAnalise(2); break;
            case 2: compararId(id); break;
            case 11:
                existeId(id);
                if(id.categoria != 5)
                    Compilador.Erro("Variável " + id.nome + " não é uma Procedure");
                else
                    setAnalise(69);
                break;
            case 69:
                existeId(id);
                if(id.tipo != 8)
                    Compilador.Erro("Variável " + id.nome + " não é INTEGER (Linha "+ id.linha + ")" );
                else
                    setAnalise(69);
            case 72:
                existeId(id);
                if(id.categoria == 3 && id.declarado == 0){
                    //Para CONSTS
                }
                if(id.tipo != 8)
                    Compilador.Erro("Variável " + id.nome + " não é INTEGER (Linha "+ id.linha + ")" );
                count++;
                //else
                 //   setAnalise(69);
            //case 4: addId(id); break;
            //case 5: addId(id); addNivel(); break;
            default: break;
        }
    }

    public static void compararId(Identificador id){
        existeId(id);
        //if(id.tipo)
    }

    public static boolean existeId(Identificador id){
        if(buscaIdExiste(id))
            return true;
        else {
            Compilador.Erro("Variável " + id.nome + " não declarada");
            return false;
        }

    }

    public static void addId(Identificador id){

        //id.nome = ids.get(numId).nome;
        //numId++;
        //id.categoria = codigo;
        if(buscaId(id))
            Compilador.Erro("Identificador " + id.nome + " já declarado!");
        else{
            listaIdentificador.get(nivel).add(id);
            if(codigo == 5){//Se for declaração de procedure
                addNivel();
                AnalisadorSemantico.setCodigo(69);
            }
        }

    }

    public static void deleteIds(){
        listaIdentificador.remove(nivel);
    }

    public static void insertIdTable(String name){
        Identificador id = new Identificador();
        id.setNome(name);
        id.setNivel(nivel);
        id.setCategoria(codigo);
        id.setLinha(Token.linhas);

        switch (codigo){
            case 2: id.setTipo(48);break;//Caso label
            case 3: id.setTipo(8);break;//Caso const
            case 4: id.setTipo(8);break;//Caso var
            //case 5: addNivel();setCodigo(69);break;//Caso procedure
            default:break;
        }

        ids.add(id);
        //System.out.println("Nome: " + id.nome);


        if(codigo == 5){
            addNivel();
            AnalisadorSemantico.setCodigo(69);
        }
        //checaId(name);
    }

    public static String getNomeToken(int numId){
        switch (numId){
            case 1:return "PROGRAM";
            case 2:return "LABEL";
            case 3:return "CONST";
            case 4:return "VAR";
            case 5:return "PROCEDURE";
            case 8:return "INTEGER";
        }
        return "";
    }

}
