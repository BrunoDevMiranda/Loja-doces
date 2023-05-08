package br.com.bruno.view.PessoasView;

import br.com.bruno.factory.connection.ClienteDao;
import br.com.bruno.factory.connection.VendedorDao;
import br.com.bruno.model.Cliente;
import br.com.bruno.model.Vendedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovoPessoas extends JFrame {
    private JPanel mainPainel;
    private JButton salvarButton;
    private JTextField txtNome;
    private JTextField txtCPF;
    private JButton cancelarButton;
    private JRadioButton clienteRadioButton;
    private JRadioButton vendedorRadioButton;
    Cliente cliente = new Cliente();
    ClienteDao clienteDao = new ClienteDao();
    Vendedor vendedor = new Vendedor();
    VendedorDao vendedorDao = new VendedorDao();

    public NovoPessoas(){

        setContentPane(mainPainel);
        setTitle("Novo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (clienteRadioButton.isSelected()) {

                    cliente.setNome(txtNome.getText());
                    cliente.setCpf(txtCPF.getText());
                    clienteDao.save(cliente);
                    if (!(!cliente.getNome().isEmpty() && !cliente.getCpf().isEmpty())) {
                        JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Clinte cadastrado com sucesso!");

                    }
                } else if (vendedorRadioButton.isSelected()) {
                    vendedor.setNome(txtNome.getText());
                    vendedor.setCpf(txtCPF.getText());
                    vendedorDao.save(vendedor);

                    if (vendedor.getCpf().isEmpty() || vendedor.getNome().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vendedor cadastrado com sucesso!");
                    }
                    txtCPF.setText("");
                    txtNome.setText("");
                }

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
        NovoPessoas novoPessoas = new NovoPessoas();

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


