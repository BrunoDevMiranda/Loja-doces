package br.com.bruno.view.PessoasView;

import br.com.bruno.factory.connection.ClienteDao;
import br.com.bruno.model.Cliente;
import br.com.bruno.view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarClientes extends JFrame {
    private JPanel mainPainel;
    private JTextField txtName;
    private JTextField txtCpf;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JLabel lblId;
    private JTextField txtID;
    private JLabel jLabel;
    private JLabel jLabel2;
    private AdminView adminView;
    Cliente cliente = new Cliente();
    ClienteDao clienteDao = new ClienteDao();


    public EditarClientes(int id, String nome, String cpf){
        lblId.setText(String.valueOf(id));
        txtName.setText(nome);
        txtCpf.setText(cpf);

        setContentPane(mainPainel);
        setTitle("Edição");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.setId(Integer.parseInt(lblId.getText()));
                cliente.setNome(txtName.getText());
                cliente.setCpf(txtCpf.getText());

                clienteDao.update(cliente);
                if (cliente.getCpf().isEmpty() || cliente.getNome().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Porfavor Preencher todos os campos", "Tente de Novo", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Clinte atualizado com sucesso!");
                }
                dispose();
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
                         EditarClientes editarClientes = new EditarClientes(1,"nome","cpf");
                         editarClientes.setVisible(true);
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JTextField getTxtCpf() {
        return txtCpf;
    }

    public void setTxtCpf(JTextField txtCpf) {
        this.txtCpf = txtCpf;
    }

    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }
}

//}
