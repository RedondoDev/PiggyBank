package org.redondo.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ErrorV extends JFrame {

	private Point coordenadasPinchar;
	public static boolean abierta;

	public ErrorV(ArrayList<String> errores) {

		abierta = true;
		ImageIcon icono = new ImageIcon("src/main/resources/Piggy.png");

		this.setUndecorated(true);
		this.setIconImage(icono.getImage());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(430, 160);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		PanelError panelError = new PanelError(errores);
		this.add(panelError);

		this.setVisible(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				coordenadasPinchar = e.getPoint();
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen() - coordenadasPinchar.x;
				int y = e.getYOnScreen() - coordenadasPinchar.y;
				setLocation(x, y);
			}
		});

	}

	class PanelError extends JPanel {

		JButton salir;

		public PanelError(ArrayList<String> errores) {

			this.setLayout(null);
			this.setBackground(new Color(Inicio.COLOR4));
			this.setSize(430, 150);
			this.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 2));

			JPanel panelGrid = new JPanel();
			panelGrid.setBounds(50, 30, 330, 100);
			panelGrid.setLayout(new GridLayout(errores.size(), 1));
			panelGrid.setBackground(new Color(Inicio.COLOR4));
			for (String e : errores) {
				JLabel error = new JLabel(e);
				error.setHorizontalAlignment(SwingConstants.CENTER);
				panelGrid.add(error);
			}
			this.add(panelGrid);

			salir = crearBotonSalir(new escuchaRaton());
			salir.setBounds(399, 5, 25, 25);
			this.add(salir);

			errores.clear();

			this.setOpaque(true);
		}

		class escuchaRaton extends MouseAdapter implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == salir) {
					ErrorV.this.dispose();
				}
			}

		}

	}

	private JButton crearBotonSalir(ActionListener a) {
		JButton b = new JButton();
		Image icono = new ImageIcon("src/main/resources/close.png").getImage();
		ImageIcon exit = new ImageIcon(icono.getScaledInstance(25, 25, Image.SCALE_SMOOTH));

		b.setIcon(exit);
		b.setSize(30, 30);
		b.setBorder(BorderFactory.createEmptyBorder());
		b.setContentAreaFilled(false);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addActionListener(a);
		return b;
	}

}
