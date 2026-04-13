package br.com.jose.cadcolswing.carro.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import br.com.jose.cadcolswing.domain.Carro;
import br.com.jose.cadcolswing.domain.Colecionador;
import br.com.jose.cadcolswing.persistence.CarroDAO;
import br.com.jose.cadcolswing.principal.ui.MenuPrincipal;

public class MenuCarro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaCarro;
	private JLabel lblPlaca;
	private JFormattedTextField txtPlaca;
	private JButton btnBuscar;
	private JButton btnAdicionar;
	private JButton btnVoltar;
	private DefaultTableModel model;
	private JTextField txtQuantidade;
	private JLabel lblTotalCarros;
	private JLabel lblInfo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuCarro frame = new MenuCarro();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuCarro() {

		CarroDAO carroDAO = new CarroDAO();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblInfo = new JLabel("");
		lblInfo.setBounds(25, 100, 176, 14);
		contentPane.add(lblInfo);

		JLabel lblListaCarros = new JLabel("Lista de Carros");
		lblListaCarros.setBounds(5, 5, 674, 31);
		lblListaCarros.setVerticalAlignment(SwingConstants.TOP);
		lblListaCarros.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaCarros.setFont(new Font("Tahoma", Font.BOLD, 25));
		contentPane.add(lblListaCarros);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 125, 664, 365);
		contentPane.add(scrollPane);

		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		model.addColumn("ID");
		model.addColumn("CPF");
		model.addColumn("Placa");
		model.addColumn("Ano de fabricação");
		model.addColumn("Apelido");

		tabelaCarro = new JTable(model);
		scrollPane.setViewportView(tabelaCarro);

		List<Carro> lista = carroDAO.listar();
		if (lista == null || lista.isEmpty()) {
			lblInfo.setText("Nenhum carro na lista!");
		} else {
			for (Carro carro : lista) {
				Colecionador colecionador = carro.getColecionador();
				String cpf = "";

				if (colecionador != null) {
					cpf = colecionador.getCpf();
				}

				model.addRow(
						new Object[] { carro.getId(), cpf, carro.getPlaca(), carro.getAnoFab(), carro.getApelido() });
			}
		}

		lblPlaca = new JLabel("Placa:");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPlaca.setBounds(15, 47, 42, 34);
		contentPane.add(lblPlaca);

		try {
			MaskFormatter mask = new MaskFormatter("UUU#U##");
			mask.setPlaceholderCharacter('_');

			txtPlaca = new JFormattedTextField(mask);
			txtPlaca.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPlaca.setBounds(71, 47, 130, 31);
			contentPane.add(txtPlaca);

		} catch (Exception e) {
			e.printStackTrace();
		}

		txtPlaca.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (c == ' ') {
					e.consume();
				}
			}
		});

		btnAdicionar = new JButton("+ Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AdicionarCarro adicionarCarro = new AdicionarCarro();

				dispose();

				adicionarCarro.setVisible(true);
				adicionarCarro.setLocationRelativeTo(null);
			}
		});
		btnAdicionar.setForeground(Color.BLACK);
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdicionar.setBackground(Color.WHITE);
		btnAdicionar.setBounds(405, 47, 158, 34);
		contentPane.add(btnAdicionar);

		btnVoltar = new JButton("<- Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MenuPrincipal telaPrincipal = new MenuPrincipal();

				telaPrincipal.setVisible(true);
				telaPrincipal.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnVoltar.setForeground(Color.BLACK);
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(573, 47, 106, 34);
		contentPane.add(btnVoltar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String placa = txtPlaca.getText().replace("_", "").trim();

				model.setRowCount(0);

				if (placa.isEmpty()) {

					List<Carro> lista = carroDAO.listar();

					if (lista == null || lista.isEmpty()) {
						lblInfo.setText("Nenhum carro na lista!");
					} else {
						lblInfo.setText("");

						for (Carro carro : lista) {
							Colecionador colecionador = carro.getColecionador();
							String cpf = "";

							if (colecionador != null) {
								cpf = colecionador.getCpf();
							}

							model.addRow(new Object[] { carro.getId(), cpf, carro.getPlaca(), carro.getAnoFab(),
									carro.getApelido() });
						}
					}

				} else {

					txtPlaca.setText("");

					Carro carro = carroDAO.buscarPelaPlaca(placa);

					if (carro != null) {
						Colecionador colecionador = carro.getColecionador();
						String cpf = "";

						if (colecionador != null) {
							cpf = colecionador.getCpf();
						}

						model.addRow(new Object[] { carro.getId(), cpf, carro.getPlaca(), carro.getAnoFab(),
								carro.getApelido() });

						lblInfo.setText("");
					} else {
						lblInfo.setText("Carro não encontrado!");
					}
				}

				txtQuantidade.setText(String.valueOf(tabelaCarro.getRowCount()));
			}
		});
		btnBuscar.setForeground(Color.BLACK);
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(241, 47, 158, 34);
		contentPane.add(btnBuscar);

		txtQuantidade = new JTextField();
		txtQuantidade.setEditable(false);
		txtQuantidade.setText("0");
		txtQuantidade.setColumns(10);
		txtQuantidade.setBounds(622, 92, 52, 20);
		contentPane.add(txtQuantidade);

		lblTotalCarros = new JLabel("Total de Carros");
		lblTotalCarros.setBounds(519, 92, 100, 20);
		contentPane.add(lblTotalCarros);

		tabelaCarro.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int linha = tabelaCarro.getSelectedRow();

					String idSelecionado = tabelaCarro.getValueAt(linha, 0).toString();
					String cpfSelecionado = tabelaCarro.getValueAt(linha, 1).toString();
					String placaSelecionada = tabelaCarro.getValueAt(linha, 2).toString();
					String anoFabSelecionado = tabelaCarro.getValueAt(linha, 3).toString();
					String apelidoSelecionado = tabelaCarro.getValueAt(linha, 4).toString();

					GerenciarCarro gerenciarCarro = new GerenciarCarro(idSelecionado, cpfSelecionado, placaSelecionada,
							anoFabSelecionado, apelidoSelecionado);

					gerenciarCarro.setVisible(true);
					gerenciarCarro.setLocationRelativeTo(null);
					dispose();
				}
			}
		});

		txtQuantidade.setText(String.valueOf(tabelaCarro.getRowCount()));
	}
}