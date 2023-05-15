package br.com.bruno.view.ProdutoView;

import br.com.bruno.factory.connection.IngredientesDao;


import javax.swing.*;

import java.util.Objects;

public class Ingredientes extends JFrame {
    private JPanel mainPainel;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JComboBox boxTipo;
    private JTextField txtPeso;


    public Ingredientes() {
        setContentPane(mainPainel);
        setTitle("Bem Vindo Amin");
        setSize(700, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        IngredientesDao ingredientesDao = new IngredientesDao();
        br.com.bruno.model.Ingredientes ingredientes = new br.com.bruno.model.Ingredientes();


        JComboBox<String> comboBox1 = new JComboBox<>();
        comboBox1.addItem("<<Selecione>>");
        for (br.com.bruno.model.Ingredientes i : ingredientesDao.findingredientes()) {
            comboBox1.addItem(" | " + i.getNome() + " | ");
        }
        comboBox1.setSelectedIndex(0);
        boxTipo.setModel(comboBox1.getModel());

        salvarButton.addActionListener(e -> {
            ingredientes.setNome(Objects.requireNonNull(comboBox1.getSelectedItem()).toString());
            ingredientes.setPeso_liquido(Float.parseFloat(txtPeso.getText()));
            ingredientesDao.save(ingredientes);

        });
        cancelarButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    new Ingredientes();
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ingredientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
