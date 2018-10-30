package compilador;

import analisadorSintatico.AnalisadorSintatico;
import arquivo.Arquivo;
import token.Token;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class Compilador extends JFrame {

    //public static Stack<Map> analisador = new Stack<>();
    public static Stack<Map> tokens = new Stack<>();

    private String[] colunas = {"Código, Símbolo"};
    private JTable table = new JTable(null,colunas);

    private static JTextArea textArea = new JTextArea("");
    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane scrollPaneToken = new JScrollPane();
    private JScrollPane scrollPaneSint = new JScrollPane();
    private static JTextArea tokensArea = new JTextArea("");

    private static JTextArea lineArea = new JTextArea("");

    private static JTextArea sintArea = new JTextArea("");



    private JMenuBar menuBar = new JMenuBar();
    //Arquivo
    private JMenu menuFile = new JMenu("Arquivo");
    private JMenuItem menuNew = new JMenuItem("Novo");
    private JMenuItem menuOpen = new JMenuItem("Abrir");
    private JMenuItem menuSave = new JMenuItem("Salvar");
    //Build
    private JMenu menuAction = new JMenu("Ações");
    private JMenuItem menuCompile = new JMenuItem("Compilar");
    private JMenuItem menuDebug =  new JMenuItem("Debugar");
    //Sobre
    private JMenu menuHelp = new JMenu("Ajuda");
    private JMenuItem menuAbout = new JMenuItem("Sobre");

    private String path = "";
    private JFileChooser fileChooser = new JFileChooser();

    private JButton buttonDebug = new JButton("Debugar");

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
            //tokens = AnalisadorSintatico.Analisar(textArea.getText());
        });

        //Opção Debug
        menuDebug.addActionListener(e->{
            AnalisadorSintatico.Debugar(true);
        });

        //Opção "Sobre"
        menuAbout.addActionListener(e->{
            JOptionPane.showMessageDialog(null, "Trabalho acadêmico para a aula de Compiladores \n Desenvolvido por Luiz Gustavo e Marcelo Filho","Sobre", JOptionPane.INFORMATION_MESSAGE);
        });


            //JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
            //JTextArea jTextArea1 = new javax.swing.JTextArea();


        textArea.setColumns(20);
        textArea.setRows(5);
        scrollPane.setViewportView(textArea);

        tokensArea.setColumns(20);
        tokensArea.setRows(5);
        scrollPaneToken.setViewportView(tokensArea);

        sintArea.setColumns(20);
        sintArea.setRows(5);
        scrollPaneSint.setViewportView(sintArea);

        buttonDebug.setText("Debug");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(scrollPaneSint, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonDebug)
                                                .addContainerGap())))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 646, Short.MAX_VALUE)
                                        .addComponent(scrollPaneToken, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPaneSint, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonDebug)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 219, Short.MAX_VALUE)
                                        .addComponent(scrollPaneToken, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );




        //Itens para a barra de opções
        menuFile.add(menuNew);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuAction.add(menuCompile);
        menuAction.add(menuDebug);
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
//        tokensArea.setRows(10);
//        tokensArea.setBounds(700,70,500,530);
//        JScrollPane scroll = new JScrollPane(tokensArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        c.add(scroll);
//        c.add(tokensArea);


        //Caixa de texto para edição
        //textArea.setBounds(50,0,600,600);
        //textArea.setColumns(20);
        //textArea.setRows(5);
        //scrollPane.setViewportView(textArea);
        //c.add(textArea);
        //c.add(scrollPane);
        //TODO Barra de rolagem na área de texto

        //Botão de debugar
        buttonDebug.setBounds(600,100,100,100);
        buttonDebug.addActionListener(e -> {
            tokens = AnalisadorSintatico.Debugar(false);
            //System.out.println(AnalisadorSintatico.Debugar(,false));
        });
        c.add(buttonDebug);

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

    public static void TextoSintatico(String token){
        sintArea.setText(token);
    }

//    public static void AtualizaContagem(int n){
//        lineArea.setText(lineArea.getText() + "\n" + n);
//    }

    public static void Erro(String msg){
        JOptionPane.showMessageDialog(null,msg,"Erro",JOptionPane.ERROR_MESSAGE);
    }

    public static void Valido(String msg){
        JOptionPane.showMessageDialog(null,msg,"Válido!",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void MostraSintatico(String word){
        lineArea.setText(lineArea.getText() + word);
    }

    public static String GetCode(){
        return textArea.getText();
    }
}