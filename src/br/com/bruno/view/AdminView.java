package br.com.bruno.view;

import br.com.bruno.factory.connection.*;
import br.com.bruno.model.*;
import br.com.bruno.view.PessoasView.EditarClientes;
import br.com.bruno.view.PessoasView.EditarVendedor;
import br.com.bruno.view.PessoasView.NovoPessoas;
import br.com.bruno.view.ProdutoView.EditarProduto;
import br.com.bruno.view.ProdutoView.NovoEstoque;
import br.com.bruno.view.ProdutoView.NovoProduto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AdminView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel mainPainel;
    private JTextField txtID;
    private JButton buscarButton;
    private JButton cadastrarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton cancelarButton;
    private JRadioButton clienteRadioButton;
    private JRadioButton vendedorRadioButton;
    private JTextField txtNome;
    private JTextField txtCPF;
    private JTable table1;
    private JTabbedPane tabbedPane2;
    private JLabel labelNome;
    private JLabel labelCPF;
    private JButton btnBuscarProduto;
    private JTextField txtIdProd;
    private JTable table2;
    private JButton btnNovoPRod;
    private JButton btnEditarProd;
    private JButton btnExcluirProd;
    private JButton btnCancelarProd;
    private JLabel lblNameProd;
    private JLabel lblTipoProd;
    private JLabel lblPrecoProd;
    private JTextField txtIdEstoque;
    private JButton btnBuscarEstoque;
    private JTable table3;
    private JButton btnNovoEstoque;
    private JButton btnCancelarEstoque;
    private JTable table4;
    private JButton buscarIngredientes;
    private ButtonGroup buttonGroup1;


    Cliente cliente = new Cliente();
    ClienteDao clienteDao = new ClienteDao();
    Vendedor vendedor = new Vendedor();
    VendedorDao vendedorDao = new VendedorDao();
    Produto produto = new Produto();
    ProdutoDao produtoDao = new ProdutoDao();
    private IngredientesDao ingredientesDao = new IngredientesDao();
    static EstoqueDao estoqueDao = new EstoqueDao();

    public AdminView() {
        setContentPane(mainPainel);
        setTitle("Bem Vindo Amin");
        setSize(700, 510);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        buscarButton.addActionListener(e -> findPessoas());
        cadastrarButton.addActionListener(e -> {
            NovoPessoas novoPessoas = new NovoPessoas();
        });
        editarButton.addActionListener(e -> {
            int id = Integer.parseInt(txtID.getText());
            String nome = labelNome.getText();
            String cpf = labelCPF.getText();
            if (clienteRadioButton.isSelected()) {
                EditarClientes editarClientes = new EditarClientes(id, nome, cpf);
            } else if (vendedorRadioButton.isSelected()) {
                EditarVendedor editarVendedor = new EditarVendedor(id, nome, cpf);
            }
        });
        excluirButton.addActionListener(e -> deletePessoas());
        cancelarButton.addActionListener(e -> {
            buttonGroup1.clearSelection();
            txtID.setText("");

        });
        btnNovoPRod.addActionListener(e -> {
            NovoProduto novoProduto = new NovoProduto();
        });
        btnBuscarProduto.addActionListener(e -> buscaProduto());
        btnEditarProd.addActionListener(e -> {
            int id = Integer.parseInt(txtIdProd.getText());
            String nome = lblNameProd.getText();
            String tipo = lblTipoProd.getText();
            double preco = Double.parseDouble(lblPrecoProd.getText());

            EditarProduto prod = new EditarProduto(id, nome, tipo, preco);


        });
        btnCancelarProd.addActionListener(e -> txtIdProd.setText(""));
        btnExcluirProd.addActionListener(e -> excluirProdutos());
        btnBuscarEstoque.addActionListener(e -> buscarEstoque());
        btnNovoEstoque.addActionListener(e -> {
            NovoEstoque novoEstoque = new NovoEstoque();
        });
        buscarIngredientes.addActionListener(e -> buscarIngredientes());
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    new AdminView();
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

    public static class clienteTableModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Id", "Nome", "Cpf"};
        private final List<Cliente> clientes = new ArrayList<>();

        @Override
        public int getRowCount() {
            return clientes.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        public Object getValueAt(int linha, int coluna) {
            if (linha >= 0 && linha < clientes.size()) {
                Cliente cliente = clientes.get(linha);
                switch (coluna) {
                    case 0 -> {
                        return cliente.getId();
                    }
                    case 1 -> {
                        return cliente.getNome();
                    }
                    case 2 -> {
                        return cliente.getCpf();
                    }
                }
            }
            return null;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }

    public static class vendedorTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Id", "Nome", "CPF"};
        private final List<Vendedor> vendedores = new ArrayList<>();

        @Override
        public int getRowCount() {
            return vendedores.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int linha, int coluna) {
            return switch (coluna) {
                case 0 -> vendedores.get(linha).getId();
                case 1 -> vendedores.get(linha).getNome();
                case 2 -> vendedores.get(linha).getCpf();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();

            } else {
                return Object.class;
            }
        }
    }

    public static class produtoTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Id", "Nome", "Tipo", "Preço"};
        private final List<Produto> produtos = new ArrayList<>();

        @Override
        public int getRowCount() {
            return produtos.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int linha, int coluna) {
            return switch (coluna) {
                case 0 -> produtos.get(linha).getId();
                case 1 -> produtos.get(linha).getNome();
                case 2 -> produtos.get(linha).getTipo();
                case 3 -> produtos.get(linha).getPreco();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();

            } else {
                return Object.class;
            }
        }
    }

    public static class estoqueTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Código", "Nome", "Tipo", "Preço", "Quantidade"};
        final List<Estoque> estoques = estoqueDao.innerJoin();

        @Override
        public int getRowCount() {
            return estoques.size() - 1;
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int linha, int coluna) {
            return switch (coluna) {
                case 0 -> estoques.get(linha).getProduto().getId();
                case 1 -> estoques.get(linha).getProduto().getNome();
                case 2 -> estoques.get(linha).getProduto().getTipo();
                case 3 -> estoques.get(linha).getProduto().getPreco();
                case 4 -> estoques.get(linha).getQuantidade();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();

            } else {
                return Object.class;
            }
        }
    }

    public static class ingredienteTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Código", "Nome", "Peso líquido"};
        IngredientesDao ingredientesDao = new IngredientesDao();
        final List<Ingredientes> ingredientes = ingredientesDao.findingredientes();

        public int getRowCount() {
            return ingredientes.size() - 1;
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int linha, int coluna) {
            return switch (coluna) {
                case 0 -> ingredientes.get(linha).getID();
                case 1 -> ingredientes.get(linha).getNome();
                case 2 -> ingredientes.get(linha).getPeso_liquido();

                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();

            } else {
                return Object.class;
            }
        }
    }

    private void findPessoas() {
        clienteTableModel modelC = new clienteTableModel();
        vendedorTableModel modelV = new vendedorTableModel();
        table1.setModel(modelC);
        table1.setModel(modelV);

        if (!txtID.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtID.getText());

                if (clienteRadioButton.isSelected()) {
                    cliente = clienteDao.findById(id);
                    if (cliente != null) {
                        labelNome.setText(cliente.getNome());
                        labelCPF.setText(cliente.getCpf());

                    }
                } else if (vendedorRadioButton.isSelected()) {
                    vendedor = vendedorDao.findByCodigo(id);
                    if (vendedor != null) {
                        labelNome.setText(vendedor.getNome());
                        labelCPF.setText(vendedor.getCpf());
                    }
                }
            } catch (NumberFormatException e) {
                // Lidar com o erro caso o valor inserido não seja um número válido
                JOptionPane.showMessageDialog(this, "O valor inserido não é um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (clienteRadioButton.isSelected()) {

                table1.setModel(modelC);
                modelC.clientes.addAll(clienteDao.findCliente());

            } else if (vendedorRadioButton.isSelected()) {

                table1.setModel(modelV);
                for (Vendedor v : vendedorDao.findvendedor()) {
                    modelV.vendedores.add(v);
                    System.out.println(v);
                }
            }

        }

    }

    private void deletePessoas() {
        if (clienteRadioButton.isSelected()) {
            cliente.setId(Integer.parseInt(txtID.getText()));

            if (cliente != null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    clienteDao.delete(cliente.getId());
                    JOptionPane.showMessageDialog(null, "Clinte Deletado com sucesso!");
                } else {
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cliente Não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
            }
            txtIdProd.setText("");

        } else if (vendedorRadioButton.isSelected()) {
            vendedor.setId(Integer.parseInt(txtID.getText()));

            if (vendedor != null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    vendedorDao.delete(vendedor.getId());
                    JOptionPane.showMessageDialog(null, "Vendedor Deletado com sucesso!");
                } else {
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vendedor Não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
            }
            txtIdProd.setText("");

        }
    }

    private void buscaProduto() {

        if (!txtIdProd.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtIdProd.getText());
                produto = ProdutoDao.findByID(id);
                lblNameProd.setText(produto.getNome());
                lblTipoProd.setText(produto.getTipo());
                lblPrecoProd.setText(String.valueOf(produto.getPreco()));
            } catch (NumberFormatException e) {
                // Lidar com o erro caso o valor inserido não seja um número válido
                JOptionPane.showMessageDialog(this, "O valor inserido não é um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            produtoTableModel model = new produtoTableModel();
            table2.setModel(model);
            model.produtos.addAll(produtoDao.findByProduto());
        }
    }

    //
    private void excluirProdutos() {
        produto.setId(Integer.parseInt(txtIdProd.getText()));
        if (produto != null) {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                produtoDao.delete(produto.getId());
                JOptionPane.showMessageDialog(null, "Produto Deletado com sucesso!");
            } else {
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Produto Não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
        }
        txtIdProd.setText("");
        lblPrecoProd.setText("**");
        lblNameProd.setText("**");
        lblTipoProd.setText("**");
    }

    private void buscarEstoque() {

        estoqueTableModel model = new estoqueTableModel();
        table3.setModel(model);
        for (Estoque e : estoqueDao.innerJoin()) {
            model.estoques.add(e);

            System.out.println(e);
            break;
        }
    }

    private void buscarIngredientes() {
        ingredienteTableModel model = new ingredienteTableModel();
        table4.setModel(model);
        for (Ingredientes i: ingredientesDao.findingredientes()){
            model.ingredientes.add(i);
            break;
        }

    }


}

