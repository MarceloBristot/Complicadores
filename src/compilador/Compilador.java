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
    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane scrollPaneToken = new JScrollPane();
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
    private JMenuItem menuDebug =  new JMenuItem("Debugar");
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

        //Opção Debug
        menuDebug.addActionListener(e->{
            //Mensagem
            System.out.println("Debugando...");

            System.out.println(AnalisadorSintatico.Debugar(textArea.getText()));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(309, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 646, Short.MAX_VALUE)
                                        .addComponent(scrollPaneToken, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 183, Short.MAX_VALUE)
                                        .addComponent(scrollPaneToken, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );



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

    public static void MostraSintatico(String word){
        lineArea.setText(lineArea.getText() + word);
    }
}