package Projeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormModelos {
    private JPanel PanelModelos;
    private JTabbedPane tabbedPane1;
    private JTextField textFieldPreço;
    private JTextField textFieldQuantidade;
    private JTextField textFieldMarca;
    private JTextField textFieldModelo;
    private JButton guardarModeloButton;
    private JTextField textFieldAltPesquisa;
    private JButton altPesquisarButton;
    private JTextField textFieldAltModelo;
    private JButton altAlterarButton;
    private JTextField textFieldAltPreço;
    private JTextField textFieldAltQuantidade;
    private JTextField textFieldAltMarca;
    private JButton consultarModeloButton;
    private JTextField textFieldConsultarVeiculo;
    private JButton consultarTodosOsModelosButton;
    private JTextArea textAreaConsultarMarca;
    private JTextArea textAreaConsultarTudo;
    private JButton encontrarModeloButton;
    private JButton apagarModeloButton;
    private JTextField textFieldNomeModeloAApagar;
    private JTextField textFieldEliModelo;
    private JTextField textFieldEliPreço;
    private JTextField textFieldEliQuantidade;
    private JTextField textFieldEliMarca;

    Connection con;
    PreparedStatement pst;

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolha");
        frame.setContentPane(new FormModelos().PanelModelos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void Connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bdlojagomes", "root","1234");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public FormModelos() {
        Connect();

        guardarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo, preco, quantidade, marca;
                modelo = textFieldModelo.getText();
                preco = textFieldPreço.getText();
                quantidade = textFieldQuantidade.getText();
                marca = textFieldMarca.getText();
                try {
                    pst = con.prepareStatement("insert into produtos(NomeProdutos,Preço,Quantidade,NomeCategorias)values(?,?,?,?)");
                    pst.setString(1, modelo);
                    pst.setString(2, preco);
                    pst.setString(3, quantidade);
                    pst.setString(4, marca);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modelo adicionado!");
                    textFieldModelo.setText("");
                    textFieldPreço.setText("");
                    textFieldQuantidade.setText("");
                    textFieldMarca.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Esta marca não existe!");
                }
            }
        });

        altPesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String id = textFieldAltPesquisa.getText();
                    pst = con.prepareStatement("select NomeProdutos,Preço,Quantidade,NomeCategorias from produtos where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String modelo = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String marca = rs.getString(4);


                        textFieldAltModelo.setText(modelo);
                        textFieldAltPreço.setText(preço);
                        textFieldAltQuantidade.setText(quantidade);
                        textFieldAltMarca.setText(marca);
                    } else {
                        textFieldAltModelo.setText("");
                        textFieldAltPreço.setText("");
                        textFieldAltQuantidade.setText("");
                        textFieldAltMarca.setText("");
                        JOptionPane.showMessageDialog(null, "ID inválido");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        altAlterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo,preco,quantidade, marca, id;

                modelo = textFieldAltModelo.getText();
                preco = textFieldAltPreço.getText();
                quantidade = textFieldAltQuantidade.getText();
                marca = textFieldAltMarca.getText();
                id = textFieldAltPesquisa.getText();

                try {

                    pst = con.prepareStatement("update produtos set NomeProdutos = ?,Preço = ?,Quantidade = ?,NomeCategorias = ? where id = ?");
                    pst.setString(1, modelo);
                    pst.setString(2, preco);
                    pst.setString(3, quantidade);
                    pst.setString(4, marca);
                    pst.setString(5,id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modelo atualizado!");

                    textFieldAltModelo.setText("");
                    textFieldAltPreço.setText("");
                    textFieldAltQuantidade.setText("");
                    textFieldAltPesquisa.requestFocus();
                    textFieldAltMarca.setText("");
                }

                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Erro! Insira uma marca existente/Insira um número");
                    e1.printStackTrace();
                }
            }
        });

        consultarTodosOsModelosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("select id, NomeProdutos, Preço, Quantidade,NomeCategorias from produtos");
                    ResultSet rs = pst.executeQuery();
                    textAreaConsultarTudo.setText("");
                    while(rs.next()==true)
                    {
                        String modelo = rs.getString(1) + ", " + rs.getString(2)  + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5);
                        textAreaConsultarTudo.append(modelo + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        consultarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id;
                    id = textFieldConsultarVeiculo.getText();

                    pst = con.prepareStatement("select id, NomeProdutos, Preço, Quantidade,NomeCategorias from produtos WHERE NomeCategorias = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();
                    textAreaConsultarMarca.setText("");

                    while(rs.next()==true)
                    {
                        String modelo = rs.getString(1) + ", " + rs.getString(2)  + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5);
                        textAreaConsultarMarca.append(modelo + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Erro! Insira uma marca existente");
                    e1.printStackTrace();
                }
            }
        });

        encontrarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String id = textFieldNomeModeloAApagar.getText();
                    pst = con.prepareStatement("select NomeProdutos,Preço,Quantidade,NomeCategorias from produtos where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String modelo = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String marca = rs.getString(4);


                        textFieldEliModelo.setText(modelo);
                        textFieldEliPreço.setText(preço);
                        textFieldEliQuantidade.setText(quantidade);
                        textFieldEliMarca.setText(marca);
                    }
                    else
                    {
                        textFieldEliModelo.setText("");
                        textFieldEliPreço.setText("");
                        textFieldEliQuantidade.setText("");
                        textFieldEliMarca.setText("");
                        JOptionPane.showMessageDialog(null,"ID inválido");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        apagarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo, preco, quantidade, marca;
                modelo = textFieldEliModelo.getText();
                preco = textFieldEliPreço.getText();
                quantidade = textFieldEliQuantidade.getText();
                marca = textFieldEliMarca.getText();

                try {
                    pst = con.prepareStatement("delete from produtos where NomeProdutos = ? and Preço = ? and Quantidade = ? and NomeCategorias = ?");
                    pst.setString(1, modelo);
                    pst.setString(2, preco);
                    pst.setString(3, quantidade);
                    pst.setString(4, marca);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modelo Apagado!");

                    textFieldEliModelo.setText("");
                    textFieldEliPreço.setText("");
                    textFieldEliQuantidade.setText("");
                    textFieldEliMarca.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
