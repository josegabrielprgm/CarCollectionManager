package br.com.jose.cadcolswing.principal.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.jose.cadcolswing.carro.ui.MenuCarro;
import br.com.jose.cadcolswing.colecionador.ui.MenuColecionador;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					window.setVisible(true);
					window.setTitle("Tela Principal");
					window.setLocationRelativeTo(null);
					window.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		initialize();
	}

	private void initialize() {
		getContentPane().setBackground(new Color(230, 230, 230));
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));

		int larguraFrame = 800;
		int alturaFrame = 600;

		int larguraPanel = 650;
		int alturaPanel = 450;

		int x = (larguraFrame - larguraPanel) / 2;
		int y = (alturaFrame - alturaPanel) / 2;

		panel.setBounds(x, y, larguraPanel, alturaPanel);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblMenuPrincipal = new JLabel("Menu Principal");
		lblMenuPrincipal.setBounds(236, 5, 177, 34);
		lblMenuPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 25));
		panel.add(lblMenuPrincipal);

		JButton btnMenuColecionador = new JButton("Menu Colecionador");
		btnMenuColecionador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuColecionador menuColecionador = new MenuColecionador();
				menuColecionador.setLocationRelativeTo(null);
				menuColecionador.setVisible(true);
				dispose();
			}
		});
		btnMenuColecionador.setForeground(new Color(0, 0, 0));
		btnMenuColecionador.setBackground(new Color(255, 255, 255));
		btnMenuColecionador.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnMenuColecionador.setBounds(236, 172, 177, 39);
		panel.add(btnMenuColecionador);

		JButton btnMenuCarro = new JButton("Menu Carro");
		btnMenuCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MenuCarro menuCarro = new MenuCarro();

				dispose();

				menuCarro.setVisible(true);

				menuCarro.setLocationRelativeTo(null);

			}
		});
		btnMenuCarro.setForeground(Color.BLACK);
		btnMenuCarro.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnMenuCarro.setBackground(new Color(255, 255, 255));
		btnMenuCarro.setBounds(236, 222, 177, 39);
		panel.add(btnMenuCarro);
	}
}