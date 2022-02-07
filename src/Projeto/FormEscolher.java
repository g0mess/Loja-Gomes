package Projeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormEscolher {
    private JPanel panelEscolher;
    private JButton ModelosButton;
    private JButton MarcasButton;

    public FormEscolher() {
        ModelosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormModelos().setVisible(true);
            }
        });
        MarcasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormCategorias().setVisible(true);
            }
        });
    }

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolher");
        frame.setContentPane(new FormEscolher().panelEscolher);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
