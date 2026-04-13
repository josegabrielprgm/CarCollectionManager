package br.com.jose.cadcolswing.colecionador.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.jose.cadcolswing.domain.Colecionador;
import br.com.jose.cadcolswing.persistence.ColecionadorDAO;

public class AdicionarColecionador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField txtCpf;
	private JFormattedTextField txtTelefone;
	private JTextField txtNome;
	private JTextField txtIdade;
	private JButton btnCancelar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdicionarColecionador frame = new AdicionarColecionador();
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

	public AdicionarColecionador() {

		ColecionadorDAO dao = new ColecionadorDAO();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 295, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCpf.setBounds(10, 11, 49, 34);
		contentPane.add(lblCpf);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNome.setBounds(10, 56, 87, 34);
		contentPane.add(lblNome);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTelefone.setBounds(10, 101, 87, 34);
		contentPane.add(lblTelefone);

		JLabel lblIdade = new JLabel("Idade:");
		lblIdade.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIdade.setBounds(10, 146, 87, 34);
		contentPane.add(lblIdade);

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

		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNome.setBounds(89, 59, 163, 29);
		contentPane.add(txtNome);

		try {
			MaskFormatter maskTel = new MaskFormatter("(##) #####-####");
			maskTel.setPlaceholderCharacter('_');

			txtTelefone = new JFormattedTextField(maskTel);
			txtTelefone.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtTelefone.setBounds(89, 100, 161, 30);
			contentPane.add(txtTelefone);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		txtIdade = new JTextField();
		txtIdade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtIdade.setBounds(89, 146, 163, 29);
		contentPane.add(txtIdade);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cpf = txtCpf.getText();
				String nome = txtNome.getText().trim();
				String idadeStr = txtIdade.getText().trim();
				String telefone = txtTelefone.getText();

				if (cpf.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha o CPF!");
					txtCpf.requestFocus();
					return;
				}

				if (nome.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha o nome!");
					txtNome.requestFocus();
					return;
				}

				if (idadeStr.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha a idade!");
					txtIdade.requestFocus();
					return;
				}

				if (telefone.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha o telefone!");
					txtTelefone.requestFocus();
					return;
				} else {

					int idade = Integer.parseInt(idadeStr);
					if (idade < 18 || idade > 120) {
						JOptionPane.showMessageDialog(btnAdicionar,
								"Requisitos de idade: é necessário ter entre 18 e 120 anos.", "Requisitos",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						cpf = cpf.replace(".", "").replace("-", "").trim();
						telefone = telefone.replace("(", "").replace(")", "").replace("-", "").replace(" ", "").trim();

						List<Colecionador> lista = dao.listar();

						boolean cpfExistente = false;

						for (Colecionador c : lista) {
							if (c.getCpf().equals(cpf)) {
								cpfExistente = true;
								break;
							}
						}

						if (!cpfExistente) {

							Colecionador colecionador = new Colecionador();

							colecionador.setCpf(cpf);
							colecionador.setNome(nome);
							colecionador.setTelefone(telefone);
							colecionador.setIdade(idade);

							dao.criar(colecionador);

							JOptionPane.showMessageDialog(btnAdicionar, "Colecionador adicionado com sucesso!");

							MenuColecionador menuColecionador = new MenuColecionador();
							menuColecionador.setLocationRelativeTo(null);
							menuColecionador.setVisible(true);
							dispose();

						} else {
							JOptionPane.showMessageDialog(btnAdicionar, "CPF já cadastrado!", "Erro",
									JOptionPane.ERROR_MESSAGE);

						}
					}
				}
			}
		});

		btnAdicionar.setForeground(new Color(0, 0, 0));
		btnAdicionar.setBackground(new Color(255, 255, 255));
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdicionar.setBounds(89, 231, 108, 34);
		contentPane.add(btnAdicionar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MenuColecionador menuColecionador = new MenuColecionador();
				menuColecionador.setLocationRelativeTo(null);
				menuColecionador.setVisible(true);
				dispose();

			}
		});
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBounds(89, 270, 108, 34);
		contentPane.add(btnCancelar);

		txtCpf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == ' ') {
					e.consume();
				}
			}
		});

		txtIdade.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
			}
		});
	}
}