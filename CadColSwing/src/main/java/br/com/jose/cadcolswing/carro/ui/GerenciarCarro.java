package br.com.jose.cadcolswing.carro.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.jose.cadcolswing.domain.Carro;
import br.com.jose.cadcolswing.domain.Colecionador;
import br.com.jose.cadcolswing.persistence.CarroDAO;
import br.com.jose.cadcolswing.persistence.ColecionadorDAO;

public class GerenciarCarro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField txtCpf;
	private JTextField txtPlaca;
	private JTextField txtAnoFab;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GerenciarCarro frame = new GerenciarCarro("", "", "", "", "");
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					frame.setTitle("Adicionar Colecionador");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String idSelecionado;
	private String cpfSelecionado;
	private String placaSelecionada;
	private String anoFabSelecionado;
	private String apelidoSelecionado;
	private final JButton btnCancelar = new JButton("Cancelar");
	private JButton btnApagar;
	private JTextField txtApelido;

	public GerenciarCarro(String id, String cpf, String placa, String anoFab, String apelido) {
		this.idSelecionado = id;
		this.cpfSelecionado = cpf;
		this.placaSelecionada = placa;
		this.anoFabSelecionado = anoFab;
		this.apelidoSelecionado = apelido;

		ColecionadorDAO colecionadorDAO = new ColecionadorDAO();
		CarroDAO carroDAO = new CarroDAO();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 295, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCpf.setBounds(10, 11, 49, 34);
		contentPane.add(lblCpf);

		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPlaca.setBounds(10, 56, 87, 34);
		contentPane.add(lblPlaca);

		JLabel lblApelido = new JLabel("Apelido:");
		lblApelido.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblApelido.setBounds(10, 101, 87, 34);
		contentPane.add(lblApelido);

		JLabel lblIAnoFab = new JLabel("Ano de Fabricação");
		lblIAnoFab.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIAnoFab.setBounds(10, 146, 155, 34);
		contentPane.add(lblIAnoFab);

		try {
			MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
			maskCpf.setPlaceholderCharacter('_');

			txtCpf = new JFormattedTextField(maskCpf);
			txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtCpf.setBounds(89, 14, 163, 29);
			contentPane.add(txtCpf);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		txtCpf.setEditable(false);

		txtPlaca = new JTextField();
		txtPlaca.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPlaca.setBounds(89, 59, 163, 29);
		contentPane.add(txtPlaca);

		try {
			MaskFormatter maskTel = new MaskFormatter("(##) #####-####");
			maskTel.setPlaceholderCharacter('_');

		} catch (ParseException e) {
			e.printStackTrace();
		}

		txtAnoFab = new JTextField();
		txtAnoFab.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAnoFab.setBounds(159, 149, 93, 29);
		contentPane.add(txtAnoFab);

		txtCpf.setText(cpf);
		txtPlaca.setText(placa);
		txtAnoFab.setText(anoFab);

		txtApelido = new JTextField();
		txtApelido.setText(apelido);
		txtApelido.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtApelido.setBounds(89, 101, 163, 29);
		contentPane.add(txtApelido);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Colecionador dono = new Colecionador();

				dono = colecionadorDAO.buscar(cpf);

				String cpf = txtCpf.getText().trim();
				String placa = txtPlaca.getText().trim();
				String anoFabStr = txtAnoFab.getText().trim();
				String apelido = txtApelido.getText().trim();

				if (apelido.isEmpty() || placa.isEmpty() || anoFabStr.isEmpty()) {
					JOptionPane.showMessageDialog(btnAlterar, "Preencha todos os campos!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else {

					int anoFab = Integer.parseInt(anoFabStr);

					if (anoFab < 1900 || anoFab > 2028) {
						JOptionPane.showMessageDialog(btnAlterar,
								"Requisitos do ano de fabricação: informe um ano válido entre 1900 e 2027.",
								"Requisitos de ano de fabricação", JOptionPane.INFORMATION_MESSAGE);
					} else {

						cpf = cpf.replace(".", "").replace("-", "").trim();

						Carro carro = new Carro();
						carro.setColecionador(dono);
						carro.setId(Integer.parseInt(idSelecionado));
						carro.setPlaca(placa);
						carro.setAnoFab(anoFab);
						carro.setApelido(apelido);
						carroDAO.atualizar(carro);

						JOptionPane.showMessageDialog(btnAlterar, "Colecionador alterado com sucesso!");

						MenuCarro menuCarro = new MenuCarro();
						menuCarro.setLocationRelativeTo(null);
						menuCarro.setVisible(true);
						dispose();
					}
				}
			}
		});

		btnAlterar.setForeground(new Color(0, 0, 0));
		btnAlterar.setBackground(new Color(255, 255, 255));
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAlterar.setBounds(89, 191, 108, 34);
		contentPane.add(btnAlterar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MenuCarro menuCarro = new MenuCarro();
				menuCarro.setLocationRelativeTo(null);
				menuCarro.setVisible(true);
				dispose();

			}
		});
		btnCancelar.setBounds(89, 266, 108, 32);
		contentPane.add(btnCancelar);
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBackground(Color.WHITE);

		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carroDAO.apagar(id);

				MenuCarro menuCarro = new MenuCarro();
				menuCarro.setLocationRelativeTo(null);
				menuCarro.setVisible(true);
				dispose();
			}
		});
		btnApagar.setForeground(Color.BLACK);
		btnApagar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnApagar.setBackground(Color.WHITE);
		btnApagar.setBounds(89, 230, 108, 32);
		contentPane.add(btnApagar);

		txtCpf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == ' ') {
					e.consume();
				}
			}
		});

		txtAnoFab.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
			}
		});

		lblApelido.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == ' ') {
					e.consume();
				}
			}
		});
	}
}