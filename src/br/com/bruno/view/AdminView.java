package br.com.bruno.view;

import br.com.bruno.factory.connection.ClienteDao;
import br.com.bruno.factory.connection.VendedorDao;
import br.com.bruno.model.Cliente;
import br.com.bruno.model.Produto;
import br.com.bruno.model.Vendedor;
import br.com.bruno.view.PessoasView.EditarClientes;
import br.com.bruno.view.PessoasView.EditarVendedor;
import br.com.bruno.view.PessoasView.NovoPessoas;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    Cliente cliente = new Cliente();
    ClienteDao clienteDao = new ClienteDao();
    Vendedor vendedor = new Vendedor();
    VendedorDao vendedorDao = new VendedorDao();




    public AdminView() {
        setContentPane(mainPainel);
        setTitle("Bem Vindo");
        setSize(700, 510);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

//        JComboBox<String> comboBox1 = new JComboBox<>();
//        JComboBox<String> comboBox2 = new JComboBox<>();
//        comboBox1.addItem("<<Selecione o Tipo>>");
//        comboBox1.addItem("Tubes");
//        comboBox1.addItem("Chicletes");
//        comboBox1.addItem("Marshmallows");
//        comboBox1.setSelectedIndex(0);
//        boxTipo.setModel(comboBox1.getModel());
//
//        comboBox2.addItem("1");
//        comboBox2.addItem("2");
//        comboBox2.addItem("3");
//        comboBox2.setSelectedIndex(0);
//        boxEstoque.setModel(comboBox2.getModel());

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPessoas();
            }
        });
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              NovoPessoas novoPessoas = new NovoPessoas();
            }

        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtID.getText());
                String nome = labelNome.getText();
                String cpf = labelCPF.getText();
                if(clienteRadioButton.isSelected()){
                    EditarClientes editarClientes  = new EditarClientes(id,nome,cpf);
                }else if (vendedorRadioButton.isSelected()){
                    EditarVendedor editarVendedor = new EditarVendedor(id,nome,cpf);
                }
            }
        });
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            deletePessoas();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             dispose();

            }
        });
//        btnCadastrarPro.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cadastrarProduto();
//            }
//        });
//        btnbuscarProdutos.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                buscarProdutoByID();
//            }
//        });
//
//        btnAltPro.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                alterarProdutos();
//            }
//
//
//        });
//        btnExcluirPro.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                excluirProdutos();
//            }
//        });
//        btnCancelarPro.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//            }
//        });
//        btnEstoqueCadastrar.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addProdutoEstoque();
//            }
//        });
//        btnListarEstoque.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                listarEstoque();
//            }
//        });
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

        // Cria a instância da classe AdminView após definir o Look and Feel

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
                    case 0:
                        return cliente.getId();
                    case 1:
                        return cliente.getNome();
                    case 2:
                        return cliente.getCpf();
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
            switch (coluna) {
                case 0:
                    return vendedores.get(linha).getId();
                case 1:
                    return vendedores.get(linha).getNome();
                case 2:
                    return vendedores.get(linha).getCpf();
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
            switch (coluna) {
                case 0:
                    return produtos.get(linha).getId();
                case 1:
                    return produtos.get(linha).getNome();
                case 2:
                    return produtos.get(linha).getTipo();
                case 3:
                    return produtos.get(linha).getPreco();
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

    private void findPessoas() {
        clienteTableModel modelC = new clienteTableModel();
        vendedorTableModel modelV = new vendedorTableModel();
        table1.setModel(modelC);
        table1.setModel(modelV);

        if (!txtID.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtID.getText());

                if (clienteRadioButton.isSelected()) {
                    cliente = clienteDao.findByCodigo(id);
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
                for (Cliente c : clienteDao.findCliente()) {
                    modelC.clientes.add(c);
                }

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
                int resposta =  JOptionPane.showConfirmDialog(null,"Deseja realmente excluir?","Confirmação",JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION){
                    clienteDao.delete(cliente.getId());
                    JOptionPane.showMessageDialog(null, "Clinte Deletado com sucesso!");
                }else {
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cliente Não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
            }
            txtID.setText("");
        } else if (vendedorRadioButton.isSelected()) {
            vendedor.setId(Integer.parseInt(txtID.getText()));

            if (vendedor != null) {
                int resposta =  JOptionPane.showConfirmDialog(null,"Deseja realmente excluir?","Confirmação",JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION){
                    vendedorDao.delete(vendedor.getId());
                    JOptionPane.showMessageDialog(null, "Vendedor Deletado com sucesso!");
                }else {
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vendedor Não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
            }
            txtID.setText("");
        }
    }

//    private void cadastrarProduto() {
//        produto.setNome(txtNomeProduto.getText());
//        produto.setPreco(Double.parseDouble(txtPrecoProduto.getText()));
//        produto.setTipo(boxTipo.getSelectedItem().toString()); //setar os itens da box pra pode salva
//
//        produtoDao.save(produto);
//        AdminView.produtoTableModel model = new AdminView.produtoTableModel();
//        table2.setModel(model);
//        model.produtos.add(produto);
//        if (!(!produto.getNome().isEmpty())) {
//            JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
//        } else {
//            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
//        }
//    }
//
//    private void buscarProdutoByID() {
//        AdminView.produtoTableModel model = new AdminView.produtoTableModel();
//        table2.setModel(model);
//        try {
//            int id = Integer.parseInt(txtIdProduto.getText());
//            produto = ProdutoDao.findByCodigo(id);
//            if (produto != null) {
//                model.produtos.add(produto);
//            }
//        } catch (NumberFormatException e) {
//            // Lidar com o erro caso o valor inserido não seja um número válido
//            JOptionPane.showMessageDialog(this, "O valor inserido não é um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void buscarTodosProdutos() {
//        produtoTableModel model = new produtoTableModel();
//        table2.setModel(model);
//        for (Produto p : produtoDao.findproduto()) {
//            model.produtos.add(p);
//        }
//    }
//
//    private void alterarProdutos() {
//        produto.setId(Integer.parseInt(txtIdProduto.getText()));
//        produto.setNome(txtNomeProduto.getText());
//        produto.setTipo(boxTipo.getSelectedItem().toString());
//        produto.setPreco(Double.parseDouble(txtPrecoProduto.getText()));
//        produtoDao.update(produto);
//
//        txtIdProduto.setText("");
//        txtNomeProduto.setText("");
//        txtPrecoProduto.setText("");
//
//    }
//
//    private void excluirProdutos() {
//        produto.setId(Integer.parseInt(txtIdProduto.getText()));
//        produtoDao.delete(produto.getId());
//        if (produto != null) {
//            JOptionPane.showMessageDialog(null, "Produto Deletado com sucesso!");
//        } else {
//            JOptionPane.showMessageDialog(null, "Produto Não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
//        }
//        txtIdProduto.setText("");
//    }
//
//    private void addProdutoEstoque() {
//    }
//
//    private void listarEstoque() {
//    }


    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(JTextField txtNome) {
        this.txtNome = txtNome;
    }

    public JTextField getTxtCPF() {
        return txtCPF;
    }

    public void setTxtCPF(JTextField txtCPF) {
        this.txtCPF = txtCPF;
    }
}

