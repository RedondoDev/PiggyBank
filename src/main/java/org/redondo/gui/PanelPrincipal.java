package org.redondo.gui;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PanelPrincipal extends JFrame {

	private Point coordenadasPinchar;

	public PanelPrincipal() {

		ImageIcon icono = new ImageIcon("src/main/resources/Piggy.png");

		this.setUndecorated(true);
		this.setIconImage(icono.getImage());
		this.setTitle("Piggy Bank");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 550);
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

	static class MiFondo extends JPanel {

		private final Image imagenFondo = new ImageIcon("src/main/resources/gradient2.png").getImage();

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

	static class PanelNull extends JPanel {

		ImageIcon imagenCerdito = new ImageIcon("src/main/resources/pagarImage.png");
		JLabel cerditoPagar;

		public PanelNull() {
			this.setLayout(null);
			this.setBounds(0, 0, 300, 300);
			this.setOpaque(false);

			Image imagenOriginal = imagenCerdito.getImage();
			Image imagenEscalada = imagenOriginal.getScaledInstance(58, 58, Image.SCALE_SMOOTH);
			ImageIcon imagenEscaladaIcono1 = new ImageIcon(imagenEscalada);
			ImageIcon imagenEscaladaIcono2 = new ImageIcon(imagenOriginal.getScaledInstance(63, 63, Image.SCALE_SMOOTH));

			cerditoPagar = new JLabel(imagenEscaladaIcono1);
			cerditoPagar.setBackground(new Color(0xFDF6F2));
			cerditoPagar.setOpaque(true);
			cerditoPagar.setCursor(new Cursor(Cursor.HAND_CURSOR));
			cerditoPagar.setBounds(152, 118, 64, 64);
			cerditoPagar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Pagar");
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					cerditoPagar.setIcon(imagenEscaladaIcono2);
					cerditoPagar.repaint();
				}
				@Override
				public void mouseExited(MouseEvent e) {
					cerditoPagar.setIcon(imagenEscaladaIcono1);
					cerditoPagar.repaint();
				}
			});

			this.add(cerditoPagar);

			this.setVisible(true);

		}

	}

	static class PanelGrid extends JPanel {

		JPanel p1, p2;
		JButton anadirCat, borrarCat, salir;

		public PanelGrid() {
			this.setLayout(new GridLayout(2, 1, 10, 10));
			this.setOpaque(false);

			p1 = crearPanel1();
			this.add(p1);
			p2 = crearPanel2();
			this.add(p2);
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
			pie.getStyler().setPlotBackgroundColor(null);
			pie.getStyler().setPlotBorderVisible(false);
			pie.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
			pie.getStyler().setDonutThickness(0.6);
			pie.getStyler().setLabelsFont(new Font("Malgun Gothic", Font.PLAIN, 12));
			pie.getStyler().setLegendFont(new Font("Malgun Gothic", Font.PLAIN, 16));
			pie.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
			pie.getStyler().setLegendBackgroundColor(new Color(255, 255, 255, 200));
			pie.getStyler().setLegendBorderColor(null);

			XChartPanel<PieChart> chartPanel = new XChartPanel<>(pie);
			chartPanel.setBackground(new Color(82, 102, 218, 0));
			chartPanel.setSize(500, 300);
			chartPanel.setLayout(null);
			PanelNull panelImagen = new PanelNull();
			chartPanel.add(panelImagen);
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

			p.setOpaque(false);
			return p;
		}

		private JPanel crearPanel2() {
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setBounds(0,0,getWidth(),getHeight());
			JLabel textoIngresos = crearLabel("Ingresos: 1600€", 0, 23);	// Variables
			textoIngresos.setBounds(100,40,200,35);
			JLabel textoGastos = crearLabel("Gastos: 1000€", 0,23);		// Variables
			textoGastos.setBounds(100,90,200,35);
			JLabel textoMes = crearLabel("Septiembre", 1,28);		// Variables
			textoMes.setBounds(105,170,170,40);
			JLabel textoNombre = crearLabel("Javi", 0,18);		// Variables
			textoNombre.setBounds(165,210,170,40);
			textoNombre.setHorizontalTextPosition(SwingConstants.CENTER);
			p.add(textoIngresos);
			p.add(textoGastos);
			p.add(textoMes);
			p.add(textoNombre);
			p.setOpaque(false);
			return p;
		}

		private JLabel crearLabel(String palabras, int tipo, int tamano) {
			JLabel texto = new JLabel(palabras);
			texto.setBackground(null);
			texto.setFont(new Font("Malgun Gothic", tipo, tamano));
			texto.setOpaque(false);
			return texto;
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
