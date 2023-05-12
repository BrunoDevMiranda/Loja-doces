package br.com.bruno.view.ProdutoView;

import br.com.bruno.factory.connection.ProdutoDao;
import br.com.bruno.model.Produto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarProduto extends JFrame {
    private JPanel mainPainel;
    private JButton salvarButton;
    private JTextField txtNameProd;
    private JTextField txtPreco;
    private JButton cancelarButton;
    private JLabel lblIdProd;
    private JComboBox boxTipo;


    Produto produto = new Produto();
    ProdutoDao produtoDao = new ProdutoDao();

    public EditarProduto(int id, String nome, String tipo ,double preco) {

        lblIdProd.setText(String.valueOf(id));
        txtNameProd.setText(nome);
        txtPreco.setText(String.valueOf(preco));

        setContentPane(mainPainel);
        setTitle("Novo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        JComboBox<String> comboBox1 = new JComboBox<>();
        comboBox1.addItem(tipo);
        comboBox1.addItem("Tubes");
        comboBox1.addItem("Chicletes");
        comboBox1.addItem("Marshmallows");
        comboBox1.setSelectedIndex(0);
        boxTipo.setModel(comboBox1.getModel());

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                produto.setNome(txtNameProd.getText());
                produto.setPreco(Double.parseDouble(txtPreco.getText()));
                produto.setTipo(boxTipo.getSelectedItem().toString()); //setar os itens da box pra pode salva
                produtoDao.update(produto);
                if (!(!produto.getNome().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                }
            }

        });
    }


    public JTextField getTxtNameProd() {
        return txtNameProd;
    }

    public void setTxtNameProd(JTextField txtNameProd) {
        this.txtNameProd = txtNameProd;
    }

    public JTextField getTxtPreco() {
        return txtPreco;
    }

    public void setTxtPreco(JTextField txtPreco) {
        this.txtPreco = txtPreco;
    }

    public JLabel getLblIdProd() {
        return lblIdProd;
    }

    public void setLblIdProd(JLabel lblIdProd) {
        this.lblIdProd = lblIdProd;
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    {
                        EditarProduto editarProduto = new EditarProduto(1,"","",10);
                        editarProduto.setVisible(true);
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



    }
}
