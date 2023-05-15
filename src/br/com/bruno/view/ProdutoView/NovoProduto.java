package br.com.bruno.view.ProdutoView;

import br.com.bruno.factory.connection.ProdutoDao;
import br.com.bruno.model.Produto;

import javax.swing.*;
import java.util.Objects;

public class NovoProduto extends JFrame {
    private JPanel mainPainel;
    private JButton salvarButton;
    private JTextField txtNomeProduto;
    private JTextField txtPrecoProduto;
    private JButton cancelarButton;
    private JComboBox boxTipo;
    Produto produto = new Produto();
    ProdutoDao produtoDao = new ProdutoDao();

    public NovoProduto() {

        setContentPane(mainPainel);
        setTitle("Novo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        JComboBox<String> comboBox1 = new JComboBox<>();
        comboBox1.addItem("<<Selecione o Tipo>>");
        comboBox1.addItem("Tubes");
        comboBox1.addItem("Chicletes");
        comboBox1.addItem("Marshmallows");
        comboBox1.setSelectedIndex(0);
        boxTipo.setModel(comboBox1.getModel());

        cancelarButton.addActionListener(e -> dispose());
        salvarButton.addActionListener(e -> {
            produto.setNome(txtNomeProduto.getText());
            produto.setPreco(Double.parseDouble(txtPrecoProduto.getText()));
            produto.setTipo(Objects.requireNonNull(boxTipo.getSelectedItem()).toString()); //setar os itens da box pra pode salva
            produtoDao.save(produto);
            if (produto.getNome().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            }
        });
    }


    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    new NovoProduto();
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NovoProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);        }
    }
}
