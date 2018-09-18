package compilador;

import analisadorSintatico.AnalisadorSintatico;
import arquivo.Arquivo;
import token.Token;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class Compilador extends JFrame {

    private String[] colunas = {"Código, Símbolo"};
    private JTable table = new JTable(null,colunas);

    private JTextArea textArea = new JTextArea("");
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private static JTextArea tokensArea = new JTextArea("");

    private static JTextArea lineArea = new JTextArea("");

    private JTextArea sintArea = new JTextArea("");



    private JMenuBar menuBar = new JMenuBar();
    //Arquivo
    private JMenu menuFile = new JMenu("Arquivo");
    private JMenuItem menuNew = new JMenuItem("Novo");
    private JMenuItem menuOpen = new JMenuItem("Abrir");
    private JMenuItem menuSave = new JMenuItem("Salvar");
    //Build
    private JMenu menuAction = new JMenu("Ações");
    private JMenuItem menuCompile = new JMenuItem("Compilar");
    //Sobre
    private JMenu menuHelp = new JMenu("Ajuda");
    private JMenuItem menuAbout = new JMenuItem("Sobre");

    private String path = "";
    private JFileChooser fileChooser = new JFileChooser();

    public Compilador() {

        //Configurações da tela
        super("Compilador");
        setResizable(false);
        setBounds(150, 50, 1100, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Container para incluir recursos
        Container c = this.getContentPane();
        c.setLayout(null);
        setContentPane(c);


        table.setBounds(100,500,100,100);

        //Ações dos botões da barra de menu
        //Opção "Novo"
        menuNew.addActionListener(e->{
            //TODO Opção de Salvar
            textArea.setText("");
        });

        //Opção "Abrir"
        menuOpen.addActionListener(e->{
            //TODO Opção de Abrir
            fileChooser.showOpenDialog(this);
            path = fileChooser.getSelectedFile().getPath();
            try {
                textArea.setText(Arquivo.lerArquivo(path).toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //Opção "Salvar"
        menuSave.addActionListener(e->{
            fileChooser.showSaveDialog(this);
            try {
                Arquivo.gravarArquivo(fileChooser.getSelectedFile().getPath(),textArea.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //Opção Compilar
        menuCompile.addActionListener(e->{
            //Mensagem
            System.out.println("Compilando...");

//            System.out.println(Token.Compile(textArea.getText()));
            System.out.println(AnalisadorSintatico.Analisar(textArea.getText()));
        });

        //Opção "Sobre"
        menuAbout.addActionListener(e->{
            JOptionPane.showMessageDialog(null, "Trabalho acadêmico para a aula de Compiladores \n Desenvolvido por Luiz Gustavo e Marcelo Filho","Sobre", JOptionPane.INFORMATION_MESSAGE);
        });

        //Itens para a barra de opções
        menuFile.add(menuNew);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuAction.add(menuCompile);
        menuHelp.add(menuAbout);

        //Barra de opções
        menuBar.add(menuFile);
        menuBar.add(menuAction);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

        //Caixa para visualização de análise sintática
        sintArea.setEditable(false);
        sintArea.setBounds(700,0,500,20);
        c.add(sintArea);

        //Caixa para visualização dos tokens - SOLUÇÃO ALTERNATIVA PROVISÓRIA
        tokensArea.setEditable(false);
        tokensArea.setRows(10);
        tokensArea.setBounds(700,70,500,530);
        JScrollPane scroll = new JScrollPane(tokensArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        c.add(scroll);
        c.add(tokensArea);


        //Caixa de texto para edição
        textArea.setBounds(50,0,600,600);
        c.add(textArea);
        c.add(scrollPane);
        //TODO Barra de rolagem na área de texto

        //Caixa de contagem de linha
        lineArea.setBounds(0,0,25,600);
        lineArea.setEditable(false);
        lineArea.setText("1");
        c.add(lineArea);
    }

    public static void MostraToken(Map token, String word){
        if(token == null && word == null)
            tokensArea.setText("Compilando arquivo... \n");
        else
            tokensArea.setText(tokensArea.getText() + token + "'" + word + "' \n");
    }

//    public static void AtualizaContagem(int n){
//        lineArea.setText(lineArea.getText() + "\n" + n);
//    }

    public static void Erro(String msg){
        JOptionPane.showMessageDialog(null,msg,"Erro",JOptionPane.ERROR_MESSAGE);
    }
}