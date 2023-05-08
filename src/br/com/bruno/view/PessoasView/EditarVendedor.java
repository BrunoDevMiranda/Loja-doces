package br.com.bruno.view.PessoasView;

import br.com.bruno.factory.connection.VendedorDao;
import br.com.bruno.model.Vendedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarVendedor extends JFrame{
    private JPanel mainPainel;
    private JButton salvarButton;
    private JTextField txtName;
    private JTextField txtCpf;
    private JButton cancelarButton;
    private JLabel lblId;
    private JTextField txtID;
    Vendedor vendedor = new Vendedor();
    VendedorDao vendedorDao = new VendedorDao();
    public EditarVendedor(int id, String nome, String cpf){
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
                vendedor.setId(Integer.parseInt(lblId.getText()));
                vendedor.setNome(txtName.getText());
                vendedor.setCpf(txtCpf.getText());

                vendedorDao.update(vendedor);
                if (vendedor.getCpf().isEmpty() || vendedor.getNome().isEmpty()) {
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
                        EditarVendedor editarvendedores = new EditarVendedor(1,"nome","cpf");
                        editarvendedores.setVisible(true);
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
