package br.com.bruno.view;

import br.com.bruno.factory.connection.LoginDao;
import br.com.bruno.services.Login;

import javax.swing.*;



public class LoginView extends JFrame {
    private JPanel mainPainel;
    private JTextField txtUser;
    private JPasswordField txtSenha;
    private JButton entrarButton;

    LoginDao loginDao = new LoginDao();
    public LoginView() {
        setContentPane(mainPainel);
        setTitle("Bem Vindo");
        setSize(320, 210);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        entrarButton.addActionListener(e -> {

            for (Login l : loginDao.findlogin()) {
                if (l != null){
                    if (txtUser.getText().equals(l.getUsuario()) && txtSenha.getText().equals(l.getSenha()) && l.getTipoUsuario().equals("admin")){
                        new AdminView();
                    }else if (txtUser.getText().equals(l.getUsuario()) && txtSenha.getText().equals(l.getSenha()) && l.getTipoUsuario().equals("user")){
                        new VendaView();
                    }
                }else {
                    System.out.println("Usuario não encontrado");
                }
            }




        });


    }

}
