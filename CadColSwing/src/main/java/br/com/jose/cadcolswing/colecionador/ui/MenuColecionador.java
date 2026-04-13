package br.com.jose.cadcolswing.colecionador.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.jose.cadcolswing.domain.Colecionador;
import br.com.jose.cadcolswing.persistence.ColecionadorDAO;
import br.com.jose.cadcolswing.principal.ui.MenuPrincipal;

public class MenuColecionador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabela;
	private JTextField txtCpf;
	private DefaultTableModel model;
	private JTextField txtQuantidade;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuColecionador frame = new MenuColecionador();
					frame.setLocationRelativeTo(null);
					frame.setTitle("CadCol - Menu Colecionador");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuColecionador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblListaColecionadores = new JLabel("Lista de Colecionadores");
		lblListaColecionadores.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblListaColecionadores.setBounds(5, 5, 674, 31);
		lblListaColecionadores.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblListaColecionadores);

		JLabel lblInfo = new JLabel("");
		lblInfo.setBounds(5, 104, 225, 14);
		contentPane.add(lblInfo);

		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(5, 129, 674, 366);
		contentPane.add(scroll);

		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		model.addColumn("CPF");
		model.addColumn("Nome");
		model.addColumn("Idade");
		model.addColumn("Telefone");

		tabela = new JTable(model);
		scroll.setViewportView(tabela);

		ColecionadorDAO dao = new ColecionadorDAO();
		List<Colecionador> lista = dao.listar();

		if (lista != null && !lista.isEmpty()) {
			for (Colecionador c : lista) {
				model.addRow(new Object[] { c.getCpf(), c.getNome(), c.getIdade(), c.getTelefone() });
			}
		} else {
			lblInfo.setText("Nenhum colecionador encontrado.");
		}

		JButton btnAdicionar = new JButton("+ Adicionar");
		btnAdicionar.setForeground(new Color(0, 0, 0));
		btnAdicionar.setBackground(new Color(255, 255, 255));
		btnAdicionar.addActionListener(e -> {
			AdicionarColecionador tela = new AdicionarColecionador();
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
			dispose();
		});

		btnAdicionar.setBounds(405, 47, 158, 34);
		contentPane.add(btnAdicionar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(255, 255, 255));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cpf = txtCpf.getText().trim();

				cpf = cpf.replace(".", "").replace("-", "").replace(" ", "").replace("_", "");

				if (!cpf.isEmpty()) {
					Colecionador colecionador = dao.buscar(cpf);

					if (colecionador != null) {

						model.setRowCount(0);

						model.addRow(new Object[] { colecionador.getCpf(), colecionador.getNome(),
								colecionador.getIdade(), colecionador.getTelefone() });

						lblInfo.setText("");

						txtCpf.setText("");

					} else {
						lblInfo.setText("Colecionador não encontrado");
						txtCpf.setText("");
					}

				} else {

					model.setRowCount(0);

					ColecionadorDAO dao = new ColecionadorDAO();
					List<Colecionador> lista = dao.listar();

					if (lista != null && !lista.isEmpty()) {
						for (Colecionador c : lista) {
							model.addRow(new Object[] { c.getCpf(), c.getNome(), c.getIdade(), c.getTelefone() });
						}
					} else {
						lblInfo.setText("Nenhum colecionador encontrado.");
					}
				}

			}
		});

		btnBuscar.setBounds(240, 47, 158, 34);
		contentPane.add(btnBuscar);

		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setPlaceholderCharacter('_');

			txtCpf = new JFormattedTextField(mask);
			txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtCpf.setBounds(67, 50, 163, 29);
			contentPane.add(txtCpf);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCpf.setBounds(15, 47, 42, 34);
		contentPane.add(lblCpf);

		JButton btnVoltar = new JButton("<- Voltar");
		btnVoltar.setForeground(new Color(0, 0, 0));
		btnVoltar.setBackground(new Color(255, 255, 255));
		btnVoltar.addActionListener(e -> {
			MenuPrincipal tela = new MenuPrincipal();
			tela.setLocationRelativeTo(null);
			tela.setVisible(true);
			dispose();
		});
		btnVoltar.setBounds(573, 47, 106, 34);
		contentPane.add(btnVoltar);

		txtQuantidade = new JTextField();
		txtQuantidade.setEditable(false);
		txtQuantidade.setBounds(602, 101, 52, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);

		JLabel lblNewLabel = new JLabel("Total de Colecionadores");
		lblNewLabel.setBounds(454, 104, 150, 14);
		contentPane.add(lblNewLabel);

		txtCpf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == ' ') {
					e.consume();
				}
			}
		});

		tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int linha = tabela.getSelectedRow();

					String cpfSelecionado = tabela.getValueAt(linha, 0).toString();
					String nomeSelecionado = tabela.getValueAt(linha, 1).toString();
					String idadeSelecionada = tabela.getValueAt(linha, 2).toString();
					String telefoneSelecionado = tabela.getValueAt(linha, 3).toString();

					GerenciarCarro alterar = new GerenciarCarro(cpfSelecionado, nomeSelecionado, idadeSelecionada,
							telefoneSelecionado);

					alterar.setVisible(true);

					alterar.setLocationRelativeTo(null);
					dispose();
				}

			}
		});

		int quantidadeTotal = tabela.getRowCount();

		txtQuantidade.setText(String.valueOf(quantidadeTotal));

	}
}