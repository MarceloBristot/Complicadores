package analisadorLexico;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AnalisadorLexico extends JFrame {

    private JTextArea text = new JTextArea("Teste...");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Arquivo");

    public AnalisadorLexico() {

        //Configurações da tela
        super("Compilador");
        setResizable(false);
        setBounds(150, 50, 900, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Container para incluir recursos
        Container c = this.getContentPane();
        c.setLayout(null);
        setContentPane(c);

        //Barra de opções
        menuBar.add(menu);
        setJMenuBar(menuBar);



        //Caixa de texto para edição
        text.setBounds(0,0,900,600);
        c.add(text);



    }
}