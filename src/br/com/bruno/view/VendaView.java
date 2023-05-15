package br.com.bruno.view;

import br.com.bruno.factory.DbException;
import br.com.bruno.factory.connection.*;
import br.com.bruno.model.*;
import br.com.bruno.pdf.PdfText;

import br.com.bruno.view.ProdutoView.NovoEstoque;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendaView extends JFrame {
    private Document documentoPDF;
    private final Vendedor vendedorSelecionado;
    private JTabbedPane tabbedPane1;
    private JPanel mainPainel;
    private JTextField txtCpf;
    private JButton buscarButton;
    private JComboBox comboBoxProdutos;
    private JButton adicionarButton;
    private JSpinner spinnerQuantidade;
    private JTextPane textPane;
    private JButton sairButton;
    private JButton confirmarButton;
    private JLabel labelVendedor;
    private JButton limparButton;
    private JButton btnBuscarEstoque;
    private JTable table3;
    private JButton btnNovoEstoque;
    private JButton btnCancelarEstoque;



    ProdutoDao produtoDao = new ProdutoDao();
    static EstoqueDao estoqueDao = new EstoqueDao();
    VendedorDao vendedorDao = new VendedorDao();
    List<Produto> produto = produtoDao.findByProduto();
    JComboBox<String> comboBox1 = new JComboBox<>();
    Venda venda = new Venda();
    VendaDao vendaDao = new VendaDao();
    private Cliente clienteSelecionado;
    private Font fonteTitulo;
    private Font fonteTituloEmpresa;
    private Font fonteCabecalho;
    private Font fonteCorpo;
    private Font fonteRodape;


    public VendaView() {

        setContentPane(mainPainel);
        setTitle("Bem Vindo Vendedor");
        setSize(700, 510);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        this.documentoPDF = new Document(PageSize.A4);
        try {
            String arquivoPDF = "document/Relatorio.pdf";
            PdfWriter.getInstance(this.documentoPDF, new FileOutputStream(arquivoPDF));
            this.documentoPDF.open();
            fonteTitulo = new Font(Font.HELVETICA, 28, Font.BOLD);
            fonteTituloEmpresa = new Font(Font.HELVETICA, 18, Font.NORMAL);
            fonteCabecalho = new Font(Font.HELVETICA, 14, Font.NORMAL);
            fonteCorpo = new Font(Font.HELVETICA, 12, Font.NORMAL);
            fonteRodape = new Font(Font.HELVETICA, 10, Font.NORMAL);
        } catch (FileNotFoundException | DocumentException e) {
            throw new RuntimeException(e);
        }

        this.vendedorSelecionado = vendedorDao.findByCodigo(1);
        labelVendedor.setText(vendedorSelecionado.getNome());

        comboBox1.addItem("<<Selecione>>");
        for (Produto p : produtoDao.findByProduto()) {
            comboBox1.addItem("" + p.getNome() + ", | " + p.getTipo() + ", | R$: " + p.getPreco());
        }
        comboBox1.setSelectedIndex(0);
        comboBoxProdutos.setModel(comboBox1.getModel());
        buscarButton.addActionListener(e -> buscarByCpf());
        adicionarButton.addActionListener(e -> {
            try {
                adicionarCarrinho();
            } catch (DbException ex) {
                throw new RuntimeException(ex);
            }
        });
        confirmarButton.addActionListener(e -> {
            try {
                efetuarVenda();
            } catch (DbException ex) {
                throw new RuntimeException(ex);
            }
        });

        limparButton.addActionListener(e -> limparCampos());
        sairButton.addActionListener(e -> dispose());
        btnBuscarEstoque.addActionListener(e -> buscarEstoque());
        btnNovoEstoque.addActionListener(e -> new NovoEstoque());
        btnCancelarEstoque.addActionListener(e -> dispose());
    }


    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    new VendaView();
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private void buscarByCpf() {
        String cpf = txtCpf.getText();
        Cliente cliente = new ClienteDao().findByCpf(cpf);
        if (cliente != null) {
            txtCpf.setText(cliente.getNome());
            textPane.setText("Cliente: " + cliente.getNome() + ", CPF :" + cliente.getCpf());
            this.clienteSelecionado = cliente;
        }
    }

    private Map<Produto, Integer> carrinho = new HashMap<>();
    List<ItemVenda> itens = new ArrayList<>();

    private void adicionarCarrinho() throws DbException {

        int selectedIndex = comboBox1.getSelectedIndex(); // obtém o índice do item selecionado no JComboBox
        if (selectedIndex > 0) { // verifica se um produto foi selecionado

            Produto produtoSelecionado = produto.get(selectedIndex -1);
            int quantidade = (int) spinnerQuantidade.getValue(); // obtém o valor atual do JSpinner
            int quantidadeEmEstoque = estoqueDao.findByEstoqueProduto(produtoSelecionado);
            if (quantidadeEmEstoque > quantidade) {
                System.out.println("Quantidade em Estoque :" + quantidadeEmEstoque);
            } else {
                JOptionPane.showMessageDialog(null, "Não há estoque suficiente desse produto");
                JOptionPane.showMessageDialog(null, "Consulte o estoque");
            }


            if (quantidadeEmEstoque > quantidade) {

                if (carrinho.containsKey(produtoSelecionado)) { // verifica se o produto já está no carrinho
                    quantidade += carrinho.get(produtoSelecionado); // se sim, soma a quantidade atual com a quantidade já existente no carrinho
                }
                carrinho.put(produtoSelecionado, quantidade); // adiciona o produto e sua quantidade ao carrinho

                BigDecimal total = BigDecimal.ZERO;
                StringBuilder textoCarrinho = new StringBuilder("Cliente: " + clienteSelecionado.getNome() + ", CPF: " + clienteSelecionado.getCpf() +
                        "\nVendedor: " + vendedorSelecionado.getNome() + "\n\nCarrinho:\n");

                ItemVenda item = null;
                for (Produto p: produtoDao.findByProduto()) {
                    item = new ItemVenda(produtoSelecionado, quantidade);
                }
                for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
                    Produto p = entry.getKey();
                    int qtd = entry.getValue();

                    BigDecimal subtotal = BigDecimal.valueOf(p.getPreco() * qtd).setScale(2, RoundingMode.HALF_UP);
                    total = total.add(subtotal);
                    textoCarrinho.append(p.getNome()).append(" | ").append(p.getTipo()).append(" | Quantidade: ").append(qtd).append(" | R$: ").append(p.getPreco()).append("\n");
                }
                itens.add(item);
                textPane.setText(textoCarrinho + "\nSub Total : ......... R$ " + total);
                estoqueDao.atualizarEstoque(produtoSelecionado, quantidade);
            }


        }
    }

    private void efetuarVenda() throws DbException, DocumentException {

        if (venda != null) {
            venda.setData(LocalDate.now());
            venda.setCliente(clienteSelecionado);
            venda.setVendedor(vendedorSelecionado);
            venda.setItens(itens);
            vendaDao.save(venda);
            System.out.println("Venda Efetuada com sucesso!");
            int confimar = JOptionPane.showConfirmDialog(null, "Venda Efetuada com Sucesso deseja imprmier o Recibo?", "Confimar", JOptionPane.YES_NO_OPTION);
            if (confimar == JOptionPane.YES_OPTION) {
                System.out.println("Imprindo");
                new PdfText("Nota Fiscal");
                gerarPdf();
                imprimir();
            }
        } else {
            System.out.println("Nada aconteceu");
        }

    }

    private void limparCampos() {
        txtCpf.setText("");
        carrinho.clear();
        itens.clear();
        textPane.setText("");
    }


    public void gerarPdf() throws DocumentException {

        Paragraph titulo = new Paragraph("NOTA FISCAL", fonteTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documentoPDF.add(titulo);


        Paragraph tituloEmpresa = new Paragraph("LOJA FINISSIMO", fonteTituloEmpresa);
        tituloEmpresa.setAlignment(Element.ALIGN_CENTER);
        documentoPDF.add(tituloEmpresa);

        Paragraph cnpj = new Paragraph("CNPJ: 12.345.678/0001-00", fonteCabecalho);
        cnpj.setAlignment(Element.ALIGN_CENTER);
        documentoPDF.add(cnpj);


        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataVenda = data.format(formatter);

        Paragraph dadosVenda = new Paragraph();

        dadosVenda.setAlignment(Element.ALIGN_CENTER);
        dadosVenda.add(new Chunk("Data de Emissão", fonteCabecalho));
        dadosVenda.add(new Chunk(dataVenda, fonteCorpo));
        dadosVenda.add(new Chunk("\nNúmero da venda: ", fonteCabecalho));
        dadosVenda.add(new Chunk(String.valueOf(1000), fonteCorpo) + "\n");
        dadosVenda.add(new Chunk(""));
        dadosVenda.add(new Chunk("Cliente: " + venda.getCliente().getNome(), new Font(Font.BOLD, 16)));
        dadosVenda.add(new Chunk(", CPF: " + venda.getCliente().getCpf()));
        dadosVenda.add(new Chunk("\n Vendedor: " + vendedorSelecionado.getNome()));
        dadosVenda.add(new Chunk(""));
        documentoPDF.add(dadosVenda);


        PdfPTable tabelaItens = new PdfPTable(4);
        tabelaItens.setWidthPercentage(100);

        PdfPCell colunaProduto = new PdfPCell(new Phrase("Produto", fonteCabecalho));
        PdfPCell colunaTipo = new PdfPCell(new Phrase("Tipo", fonteCabecalho));
        PdfPCell colunaQuantidade = new PdfPCell(new Phrase("Quantidade", fonteCabecalho));
        PdfPCell colunaPreco = new PdfPCell(new Phrase("Preço", fonteCabecalho));

        tabelaItens.addCell(colunaProduto);
        tabelaItens.addCell(colunaTipo);
        tabelaItens.addCell(colunaQuantidade);
        tabelaItens.addCell(colunaPreco);

        double total = 0;
        for (Map.Entry<Produto, Integer> item : carrinho.entrySet()) {
            Produto produto = item.getKey();
            int quantidade = item.getValue();
            double preco = produto.getPreco() * quantidade;

            PdfPCell celulaProduto = new PdfPCell(new Phrase(produto.getNome(), fonteCorpo));
            PdfPCell celulaTipo = new PdfPCell(new Phrase(produto.getTipo(), fonteCorpo));
            PdfPCell celulaQuantidade = new PdfPCell(new Phrase(String.valueOf(quantidade), fonteCorpo));
            PdfPCell celulaPreco = new PdfPCell(new Phrase(String.format("R$ %.2f", preco), fonteCorpo));

            tabelaItens.addCell(celulaProduto);
            tabelaItens.addCell(celulaTipo);
            tabelaItens.addCell(celulaQuantidade);
            tabelaItens.addCell(celulaPreco);

            total += preco;
        }

        documentoPDF.add(new Paragraph("\nItens da venda:\n", fonteCabecalho));
        documentoPDF.add(tabelaItens);


        Paragraph rodape = new Paragraph();
        rodape.setAlignment(Element.ALIGN_RIGHT);
        rodape.add(new Chunk("Total da venda: ", fonteRodape));
        rodape.add(new Chunk(String.format("R$ %.2f", total), fonteRodape));
        documentoPDF.add(rodape);

        documentoPDF.close();


    }


    public void imprimir() {
        if (this.documentoPDF != null && this.documentoPDF.isOpen()) {
            this.documentoPDF.close();

        }
    }

    private void buscarEstoque() {

        AdminView.estoqueTableModel model = new AdminView.estoqueTableModel();
        table3.setModel(model);
        for (Estoque e : estoqueDao.innerJoin()) {
            model.estoques.add(e);
            break;

        }

    }

}


