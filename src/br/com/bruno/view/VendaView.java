package br.com.bruno.view;

import br.com.bruno.factory.DbException;
import br.com.bruno.factory.connection.*;
import br.com.bruno.model.*;
import br.com.bruno.pdf.PdfText;
import br.com.bruno.view.PessoasView.NovoCliente;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String arquivoPDF = "document/Relatorio.pdf";
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


    Produto produto1 = new Produto();
    ProdutoDao produtoDao = new ProdutoDao();
    Estoque estoque = new Estoque();
    static EstoqueDao estoqueDao = new EstoqueDao();
    Cliente cliente = new Cliente();
    Vendedor vendedor = new Vendedor();
    VendedorDao vendedorDao = new VendedorDao();
    ClienteDao clienteDao = new ClienteDao();
    List<Produto> produto = produtoDao.findByProduto();
    JComboBox<String> comboBox1 = new JComboBox<>();
    Venda venda = new Venda();
    VendaDao vendaDao = new VendaDao();
    ItemVenda itemVenda = new ItemVenda();
    private Cliente clienteSelecionado;

    private Font fonteTitulo;
    private Font fonteTituloEmpresa;
    private Font fonteCabecalho;
    private Font fonteCorpo;
    private Font fonteRodape;


    public VendaView() {

        setContentPane(mainPainel);
        setTitle("Bem Vindo");
        setSize(700, 510);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        this.documentoPDF = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(this.documentoPDF, new FileOutputStream(arquivoPDF));
            this.documentoPDF.open();
            fonteTitulo = new Font(Font.HELVETICA, 28, Font.BOLD);
            fonteTituloEmpresa = new Font(Font.HELVETICA, 18, Font.NORMAL);
            fonteCabecalho = new Font(Font.HELVETICA, 14, Font.NORMAL);
            fonteCorpo = new Font(Font.HELVETICA, 12, Font.NORMAL);
            fonteRodape = new Font(Font.HELVETICA, 10, Font.NORMAL);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        Vendedor vendedor = vendedorDao.findByCodigo(1);
        this.vendedorSelecionado = vendedor;
        labelVendedor.setText(vendedorSelecionado.getNome());

        comboBox1.addItem("<<Selecione>>");
        for (Produto p : produtoDao.findByProduto()) {
            comboBox1.addItem("" + p.getNome() + ", | " + p.getTipo() + ", | R$: " + p.getPreco());
        }
        comboBox1.setSelectedIndex(0);
        comboBoxProdutos.setModel(comboBox1.getModel());
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarByCpf();
            }
        });
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarCarrinho();
                } catch (DbException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    efetuarVenda();
                } catch (DbException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBuscarEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEstoque();
            }
        });
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

            Produto produtoSelecionado = produto.get(selectedIndex);
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
                String textoCarrinho = "Cliente: " + clienteSelecionado.getNome() + ", CPF: " + clienteSelecionado.getCpf() +
                        "\nVendedor: " + vendedorSelecionado.getNome() + "\n" +
                        "\nCarrinho:\n";

                ItemVenda item = null;
                for (Produto p : produtoDao.findByProduto()) {
                    item = new ItemVenda(produtoSelecionado, quantidade);
                }
                itens.add(item);

                for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
                    Produto p = entry.getKey();
                    int qtd = entry.getValue();

                    BigDecimal subtotal = BigDecimal.valueOf(p.getPreco() * qtd).setScale(2, RoundingMode.HALF_UP);
                    total = total.add(subtotal);
                    textoCarrinho += p.getNome() + " | " + p.getTipo() + " | Quantidade: " + qtd + " | R$: " + p.getPreco() + "\n";
                }
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

        // Adiciona o cabeçalho com informações da empresa
        Paragraph tituloEmpresa = new Paragraph("LOJA FINISSIMO", fonteTituloEmpresa);
        tituloEmpresa.setAlignment(Element.ALIGN_CENTER);
        documentoPDF.add(tituloEmpresa);

        Paragraph cnpj = new Paragraph("CNPJ: 12.345.678/0001-00", fonteCabecalho);
        cnpj.setAlignment(Element.ALIGN_CENTER);
        documentoPDF.add(cnpj);

        // Adiciona o cabeçalho com informações da venda
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

        // Adiciona o corpo da nota fiscal com os itens da venda
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

// Adiciona o rodapé com o total da venda
        Paragraph rodape = new Paragraph();
        rodape.setAlignment(Element.ALIGN_RIGHT);
        rodape.add(new Chunk("Total da venda: ", fonteRodape));
        rodape.add(new Chunk(String.format("R$ %.2f", total), fonteRodape));
        documentoPDF.add(rodape);

// Fecha o documento
        documentoPDF.close();


//        Paragraph paragraphTitle = new Paragraph();
//        Paragraph paragraphTitle2 = new Paragraph();
//        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
//        paragraphTitle2.setAlignment(Element.ALIGN_CENTER);
//        paragraphTitle.add(new Chunk("LOJA FINISSIMO", new Font(Font.HELVETICA, 32)));
//        paragraphTitle2.add(new Chunk("CNPJ : 12.345.678/0001-00", new Font(Font.HELVETICA, 18)));
//
//        this.documentoPDF.add(paragraphTitle);
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(paragraphTitle2);
//        this.documentoPDF.add(new Paragraph());
//
//        Paragraph paragraphData = new Paragraph();
//        Paragraph paragraphNumeroVenda = new Paragraph();
//        paragraphData.setAlignment((Element.ALIGN_CENTER));
//        paragraphNumeroVenda.setAlignment((Element.ALIGN_CENTER));
//        paragraphData.add(new Chunk("Data da Venda: " + venda.getData(), new Font(Font.HELVETICA, 14)));
//        paragraphNumeroVenda.add(new Chunk("Numero Nota Fiscal: " + venda.getID(), new Font(Font.HELVETICA, 14)));
//        this.documentoPDF.add(paragraphData);
//        this.documentoPDF.add(paragraphNumeroVenda);
//
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//
//        Paragraph paragraphClient1 = new Paragraph();
//        Paragraph paragraphClient2 = new Paragraph();
//        paragraphClient1.setAlignment((Element.ALIGN_CENTER));
//        paragraphClient2.setAlignment((Element.ALIGN_CENTER));
//        paragraphClient1.add(new Chunk("Cliente: " + venda.getCliente().getNome(), new Font(Font.BOLD, 16)));
//        paragraphClient2.add(new Chunk("CPF " + venda.getCliente().getCpf()));
//        this.documentoPDF.add(paragraphClient1);
//        this.documentoPDF.add(paragraphClient2);
//
//        Paragraph paragraphVendedor1 = new Paragraph();
//        Paragraph paragraphVendedor2 = new Paragraph();
//        paragraphVendedor1.setAlignment(Element.ALIGN_CENTER);
//        paragraphVendedor2.setAlignment(Element.ALIGN_CENTER);
//        paragraphVendedor1.add(new Chunk("Vendedor: " + venda.getVendedor().getNome(), new Font(Font.BOLD, 16)));
//
//        Paragraph paragraphSessao = new Paragraph("________________________________________________________________________");
//        paragraphSessao.setAlignment((Element.ALIGN_CENTER));
//        this.documentoPDF.add(paragraphSessao);
//        this.documentoPDF.add(new Paragraph(" "));
//
//        this.documentoPDF.add(paragraphVendedor1);
//        this.documentoPDF.add(paragraphVendedor2);
//        this.documentoPDF.add(paragraphSessao);
//        this.documentoPDF.add(new Paragraph(" "));
//
//        Paragraph paragraphCorpo = new Paragraph();
//        Paragraph paragraphCorpoitens1 = new Paragraph();
//        Paragraph paragraphCorpoitens11 = new Paragraph();
//        Paragraph paragraphCorpoitens2 = new Paragraph();
//        Paragraph paragraphCorpoitens22 = new Paragraph();
//        Paragraph paragraphCorpoitensTotal = new Paragraph();
//        paragraphCorpo.setAlignment(Element.ALIGN_CENTER);
//        paragraphCorpo.add("Produtos");
//
//        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
//
//        String texto = "";
//        for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
//            Produto p = entry.getKey();
//            int qtd = entry.getValue();
//
//            BigDecimal subtotal = BigDecimal.valueOf(p.getPreco() * qtd).setScale(2, RoundingMode.HALF_UP);
//            total = total.add(subtotal);
//            texto += p.getNome() + " \n " +
//                    p.getTipo() + "\nQuantidade: " +
//                    qtd + "\nR$: " + p.getPreco() + "Total: ............................................... "+BigDecimal.valueOf(p.getPreco()*qtd).setScale(2,RoundingMode.HALF_UP)+"\n\n";
//        }
//        paragraphCorpoitens1.add(texto);
//
//        this.documentoPDF.add(paragraphCorpo);
//        this.documentoPDF.add(paragraphCorpoitens1);
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(paragraphCorpoitens11);
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(paragraphCorpoitens2);
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(paragraphCorpoitens22);
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//
//
//        paragraphCorpoitensTotal.add("Sub total:  ..................................................................................: R$ " + total);
//        this.documentoPDF.add(paragraphCorpoitensTotal);
//        Paragraph paragraphSessao4 = new Paragraph("________________________________________________________________________");
//
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//        this.documentoPDF.add(new Paragraph(" "));
//
//
//        Paragraph paragraphSessao5 = new Paragraph("________________________________");
//        paragraphSessao5.setAlignment((Element.ALIGN_CENTER));
//        this.documentoPDF.add(paragraphSessao4);
//
//        Paragraph paragraphSign = new Paragraph();
//        paragraphSign.setAlignment((Element.ALIGN_CENTER));
//        paragraphSign.add(new Chunk("Assinatura"));
//        this.documentoPDF.add(paragraphSign);
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

            System.out.println(e);
            break;
        }

    }

    public static class estoqueTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Código", "Nome", "Tipo", "Preço", "Quantidade"};
        private final  List<Estoque> estoques = estoqueDao.innerJoin();
        @Override
        public int getRowCount() {
            return estoques.size() -1;
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
}


