package org.redondo.gui;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import org.redondo.bd.BaseDatos;
import org.redondo.logica.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class PanelPrincipal extends JFrame {

	private Point coordenadasPinchar;

	public PanelPrincipal() {

		ImageIcon icono = new ImageIcon("src/main/resources/Piggy.png");

		this.setUndecorated(true);
		this.setIconImage(icono.getImage());
		this.setTitle("Piggy Bank");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		MiFondo fondo = new MiFondo();
		this.setContentPane(fondo);
		fondo.setLayout(new GridLayout(1, 1)); // Para que ocupe el total

		PanelGrid panelGrid = new PanelGrid();
		fondo.add(panelGrid);

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

		this.setVisible(true);
	}

	class MiFondo extends JPanel {

		private Image imagenFondo = new ImageIcon("src/main/resources/gradient2.png").getImage();

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

	class PanelGrid extends JPanel {

		JPanel p1, p2;
		JButton anadirCat, borrarCat, salir;

		public PanelGrid() {
			this.setLayout(new GridLayout(2, 1, 10, 10));
			this.setOpaque(false); // Transparente para que el fondo se vea

			p1 = crearPanel1();
			this.add(p1);
			p2 = crearPanel2();
			this.add(p2);
			//p3 = crearPanel3();
			//this.add(p3);
			//p4 = crearPanel4();
			//this.add(p4);

		}

		class EscuchaRaton extends MouseAdapter implements FocusListener, ActionListener {

			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == anadirCat) {
					anadirCat.setBounds(597, 117, 137, 37);
					anadirCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
					anadirCat.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
				} else if (e.getSource() == borrarCat) {
					borrarCat.setBounds(597, 167, 137, 37);
					borrarCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
					borrarCat.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource() == anadirCat) {
					anadirCat.setBounds(600, 120, 130, 30);
					anadirCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
					anadirCat.setFont(new Font("Malgun Gothic", Font.BOLD, 15));

				} else if (e.getSource() == borrarCat) {
					borrarCat.setBounds(600, 170, 130, 30);
					borrarCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
					borrarCat.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == anadirCat) {
					System.out.println("anadir");
				} else if (e.getSource() == borrarCat) {
					System.out.println("borrar");
				} else if (e.getSource() == salir) {
					System.exit(ABORT);
				}
			}



		}

		private JPanel crearPanel1() {
			JPanel p = new JPanel();
			p.setLayout(null);

			PieChart pie = new PieChart(getWidth(), getHeight(), Styler.ChartTheme.XChart);
			pie.getStyler().setChartBackgroundColor(new Color(255, 255, 255, 0));
			pie.addSeries("Sueldo", 10);
			pie.addSeries("Alquiler", 10);
			pie.addSeries("Comida", 10);
			pie.addSeries("Ocio", 10);
			pie.getStyler().setPlotContentSize(0.9);
			pie.getStyler().setChartPadding(20);
			pie.getStyler().setPlotBackgroundColor(new Color(195, 90, 90, 0)); // este poner transp
			pie.getStyler().setPlotBorderVisible(false);
			pie.getStyler().setLabelsFont(new Font("Malgun Gothic", Font.PLAIN, 12));
			pie.getStyler().setLegendFont(new Font("Malgun Gothic", Font.PLAIN, 16));
			pie.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
			pie.getStyler().setLegendBackgroundColor(new Color(255, 255, 255, 180));
			pie.getStyler().setLegendBorderColor(new Color(255, 255, 255, 0));

			XChartPanel<PieChart> chartPanel = new XChartPanel<>(pie);
			chartPanel.setBackground(new Color(82, 102, 218, 0));
			chartPanel.setSize(500, 300);
			p.add(chartPanel);

			anadirCat = crearBoton("Añadir categoría", new EscuchaRaton());
			anadirCat.setBounds(600, 120, 130, 30);
			p.add(anadirCat);

			borrarCat = crearBoton("Borrar categoría", new EscuchaRaton());
			borrarCat.setBounds(600, 170, 130, 30);
			p.add(borrarCat);

			salir = crearBotonSalir(new EscuchaRaton());
			salir.setBounds(767, 7, 24, 24);
			p.add(salir);

			p.setOpaque(false); // Quitar fondo luego poniéndolo false
			return p;
		}

		private JPanel crearPanel2() {
			JPanel p = new JPanel();
			p.setOpaque(true); // Quitar fondo luego poniéndolo false
			return p;
		}

		private JButton crearBoton(String texto, EscuchaRaton e) {
			JButton b = new JButton(texto);
			b.setFocusable(false);
			b.setForeground((new Color(Inicio.COLOR1)));
			b.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
			b.setBackground(new Color(Inicio.COLOR4));
			b.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
			b.setOpaque(true);
			b.addActionListener(e);
			b.addMouseListener(e);
			return b;
		}

		private JButton crearBotonSalir(ActionListener a) {
			JButton b = new JButton();
			Image icono = new ImageIcon("src/main/resources/close.png").getImage();
			ImageIcon exit = new ImageIcon(icono.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
			b.setBorderPainted(false);
			b.setContentAreaFilled(false);
			b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			b.setIcon(exit);
			b.addActionListener(a);
			return b;
		}

	}

	public static void main(String[] args) {    // BORRAR
		new PanelPrincipal();
	}

}
