package br.com.jose.cadcolswing.carro.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import br.com.jose.cadcolswing.domain.Carro;
import br.com.jose.cadcolswing.domain.Colecionador;
import br.com.jose.cadcolswing.persistence.CarroDAO;
import br.com.jose.cadcolswing.persistence.ColecionadorDAO;

public class AdicionarCarro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField txtCpf;
	private JTextField txtPlaca;
	private JTextField txtApelidoCarro;
	private JTextField txtAnoFab;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdicionarCarro frame = new AdicionarCarro();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdicionarCarro() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 295, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCpf.setBounds(10, 11, 46, 32);
		contentPane.add(lblCpf);

		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPlaca.setBounds(10, 51, 46, 32);
		contentPane.add(lblPlaca);

		JLabel lblApelido = new JLabel("Apelido:");
		lblApelido.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblApelido.setBounds(10, 94, 75, 32);
		contentPane.add(lblApelido);

		JLabel lblAnoDeFabricao = new JLabel("Ano Fabricação:");
		lblAnoDeFabricao.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAnoDeFabricao.setBounds(10, 137, 140, 32);
		contentPane.add(lblAnoDeFabricao);

		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setPlaceholderCharacter('_');

			txtCpf = new JFormattedTextField(mask);
			txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtCpf.setBounds(89, 13, 163, 29);
			contentPane.add(txtCpf);

		} catch (Exception e) {
			e.printStackTrace();
		}

		txtCpf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == ' ') {
					e.consume();
				}
			}
		});
		try {
			MaskFormatter mask = new MaskFormatter("UUU#U##");
			mask.setPlaceholderCharacter('_');

			txtPlaca = new JFormattedTextField(mask);
			txtPlaca.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPlaca.setBounds(89, 53, 163, 29);
			contentPane.add(txtPlaca);

		} catch (Exception e) {
			e.printStackTrace();
		}

		txtApelidoCarro = new JTextField();
		txtApelidoCarro.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtApelidoCarro.setBounds(89, 99, 163, 29);
		contentPane.add(txtApelidoCarro);

		txtAnoFab = new JTextField();
		txtAnoFab.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAnoFab.setBounds(155, 139, 97, 29);
		contentPane.add(txtAnoFab);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpf = txtCpf.getText().trim();
				String placa = txtPlaca.getText().trim();
				String apelido = txtApelidoCarro.getText().trim();
				String anoFabricaoStr = txtAnoFab.getText().trim();

				if (cpf.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha o CPF!");
					txtCpf.requestFocus();
					return;
				}

				if (placa.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha a Placa!");
					txtPlaca.requestFocus();
					return;
				}

				if (apelido.isBlank()) {
					JOptionPane.showMessageDialog(btnAdicionar, "Preencha o Apelido!");
					txtApelidoCarro.requestFocus();
					return;
				}

				if (anoFabricaoStr.isBlank()) {
					JOptionPane. showMessageDialog(btnAdicionar, "Preencha o Ano de Fabricação!");
					txtAnoFab.requestFocus();
					return;
				}
				cpf = cpf.replace(".", "").replace("-", "").trim();
				int anoFabricao = Integer.parseInt(anoFabricaoStr);

				if (anoFabricao < 1910 || anoFabricao > 2028) {
					JOptionPane.showMessageDialog(btnAdicionar,
							"Requisitos do ano de fabricação: informe um ano válido entre 1900 e 2027.",
							"Requisitos de ano de fabricação", JOptionPane.INFORMATION_MESSAGE);
				} else {

					CarroDAO carroDao = new CarroDAO();
					ColecionadorDAO colecionadorDao = new ColecionadorDAO();

					Colecionador col = null;
					List<Colecionador> lista = colecionadorDao.listar();

					for (Colecionador colecionador : lista) {
						if (colecionador.getCpf().equals(cpf)) {
							col = colecionador;
							break;
						}
					}

					if (col == null) {
						JOptionPane.showMessageDialog(btnAdicionar, "CPF não encontrado!", "Erro",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					Carro carro = new Carro();

					carro.setColecionador(col);
					carro.setPlaca(placa);
					carro.setAnoFab(anoFabricao);
					carro.setApelido(apelido);
					carroDao.criar(carro);

					JOptionPane.showMessageDialog(btnAdicionar, "Carro adicionado com sucesso!");

					MenuCarro menuCarro = new MenuCarro();
					menuCarro.setLocationRelativeTo(null);
					menuCarro.setVisible(true);
					dispose();

				}
			}

		});

		btnAdicionar.setForeground(new Color(0, 0, 0));
		btnAdicionar.setBackground(new Color(255, 255, 255));
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdicionar.setBounds(89, 231, 108, 34);
		contentPane.add(btnAdicionar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MenuCarro menuCarro = new MenuCarro();
				menuCarro.setLocationRelativeTo(null);
				menuCarro.setVisible(true);
				dispose();

			}
		});
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBounds(89, 270, 108, 34);
		contentPane.add(btnCancelar);
	}
}