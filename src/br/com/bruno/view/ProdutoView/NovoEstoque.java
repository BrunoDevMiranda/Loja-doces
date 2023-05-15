package br.com.bruno.view.ProdutoView;

import br.com.bruno.factory.DbException;
import br.com.bruno.factory.connection.ProdutoDao;
import br.com.bruno.model.Produto;
import br.com.bruno.services.Fabricar;

import javax.swing.*;
import java.util.List;


public class NovoEstoque extends JFrame {
    private JPanel mainPainel;
    private JComboBox boxTipo;
    private JButton cancelarButton;
    private JButton salvarButton;
    private JSpinner spinner1;
    private int quantidadeSelecionada;
    ProdutoDao produtoDao = new ProdutoDao();
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
            comboBox1.addItem(p.getNome() + ", | " + p.getTipo() + ", | R$: " + p.getPreco());
        }
        comboBox1.setSelectedIndex(0);
        boxTipo.setModel(comboBox1.getModel());
        salvarButton.addActionListener(e -> {
            try {
                Fabricar fabricar = new Fabricar();
                int selectIndex1 = comboBox1.getSelectedIndex(); // Obter o índice selecionado atualizado

                if (selectIndex1 > 0) { // Verificar se um item válido foi selecionado
                    Produto produtoSelecionado = produto.get(selectIndex1 - 1);
                    quantidadeSelecionada = Integer.parseInt(spinner1.getValue().toString());

                    switch (produtoSelecionado.getId()) {
                        case 1 -> fabricar.azedinhoUva(produtoSelecionado, quantidadeSelecionada);
                        case 2 -> fabricar.azezinhoArcoIris(produtoSelecionado, quantidadeSelecionada);
                        case 3 -> fabricar.azezinhoMorango(produtoSelecionado, quantidadeSelecionada);
                        case 4 -> fabricar.alvoradaBanana(produtoSelecionado, quantidadeSelecionada);
                        case 5 -> fabricar.coracaoMorango(produtoSelecionado, quantidadeSelecionada);
                        case 6 -> fabricar.vulcaoRosa(produtoSelecionado, quantidadeSelecionada);
                        case 7 -> fabricar.melancia(produtoSelecionado, quantidadeSelecionada);
                        case 8 -> fabricar.ovosDino(produtoSelecionado, quantidadeSelecionada);
                        case 11 -> fabricar.azedinnn(produtoSelecionado, quantidadeSelecionada);
                        default ->
                                JOptionPane.showMessageDialog(null, "Método correspondente ao produto não encontrado.");
                    }

                    JOptionPane.showMessageDialog(null, "Produto fabricado com sucesso!");
                }
            } catch (RuntimeException ex) {
                System.out.println("algo deu errado");
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (DbException ex) {
                throw new RuntimeException(ex);
            }

        });
        cancelarButton.addActionListener(e -> dispose());
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

    }

}






