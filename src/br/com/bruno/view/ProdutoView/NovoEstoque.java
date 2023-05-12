package br.com.bruno.view.ProdutoView;

import br.com.bruno.factory.connection.EstoqueDao;
import br.com.bruno.factory.connection.ProdutoDao;
import br.com.bruno.model.Estoque;
import br.com.bruno.model.Produto;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class NovoEstoque extends JFrame {
    private JPanel mainPainel;
    private JComboBox boxTipo;
    private JButton cancelarButton;
    private JButton salvarButton;
    private JSpinner spinner1;
    Produto produto = new Produto();
    ProdutoDao produtoDao = new ProdutoDao();
    Estoque estoque = new Estoque();
    EstoqueDao estoqueDao = new EstoqueDao();

    public NovoEstoque() {
        setContentPane(mainPainel);
        setTitle("Novo");
        setSize(700, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        List<Produto> produto = produtoDao.findByProduto();
        JComboBox<String> comboBox1 = new JComboBox<>();
        comboBox1.addItem("<<Selecione>>");
        for (Produto p : produtoDao.findByProduto()) {
            comboBox1.addItem("" + p.getNome() + ", | " + p.getTipo() + ", | R$: " + p.getPreco());
        }
        comboBox1.setSelectedIndex(0);
        boxTipo.setModel(comboBox1.getModel());
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                estoque.setProduto(produto.get(boxTipo.getSelectedIndex()));
                estoque.setQuantidade(Integer.parseInt(spinner1.getValue().toString()));
                estoqueDao.save(estoque);

            }

        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    new NovoEstoque();
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NovoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Cria a instância da classe AdminView após definir o Look and Feel

    }


}
