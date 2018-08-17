package compilador;

import arquivo.Arquivo;
import token.Token;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Compilador extends JFrame {

    private JTextArea textArea = new JTextArea("");
    private JScrollPane scrollPane = new JScrollPane(textArea);

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
        setBounds(150, 50, 900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Container para incluir recursos
        Container c = this.getContentPane();
        c.setLayout(null);
        setContentPane(c);


        //Ações dos botões da barra de menu
        //Opção "Novo"
        menuNew.addActionListener(e->{
            //TODO Opção de Salvar
            textArea.setText("");
        });

        //Opção "Abrir"
        menuOpen.addActionListener(e->{
            //TODO Opção de Salvar
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
            Token.Compile(textArea.getText());
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

        //Caixa de texto para edição
        textArea.setBounds(0,0,800,600);
        c.add(textArea);
        c.add(scrollPane);
        //TODO Barra de rolagem na área de texto

    }
}