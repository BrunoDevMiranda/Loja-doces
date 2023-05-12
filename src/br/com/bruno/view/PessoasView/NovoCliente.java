package br.com.bruno.view.PessoasView;

import br.com.bruno.factory.connection.ClienteDao;
import br.com.bruno.factory.connection.VendedorDao;
import br.com.bruno.model.Cliente;
import br.com.bruno.model.Vendedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovoCliente extends JFrame {
    private JPanel mainPainel;
    private JButton salvarButton;
    private JTextField txtNome;
    private JTextField txtCPF;
    private JButton cancelarButton;

    Cliente cliente = new Cliente();
    ClienteDao clienteDao = new ClienteDao();
    Vendedor vendedor = new Vendedor();
    VendedorDao vendedorDao = new VendedorDao();

    public NovoCliente() {

        setContentPane(mainPainel);
        setTitle("Novo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                cliente.setNome(txtNome.getText());
                cliente.setCpf(txtCPF.getText());
                clienteDao.save(cliente);
                if (!(!cliente.getNome().isEmpty() && !cliente.getCpf().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Clinte cadastrado com sucesso!");
                }

                txtCPF.setText("");
                txtNome.setText("");
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
                    {

                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        NovoCliente novoCliente = new NovoCliente();
    }

    public JTextField getTxtName() {
        return txtNome;
    }

    public void setTxtName(JTextField txtName) {
        this.txtNome = txtName;
    }

    public JTextField getTxtCpf() {
        return txtCPF;
    }

    public void setTxtCpf(JTextField txtCpf) {
        this.txtCPF = txtCpf;
    }


}


