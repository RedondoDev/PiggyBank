package org.redondo.gui;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieSeries;
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

    public PanelPrincipal(Usuario u) {

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
        fondo.setLayout(new GridLayout(1, 1));

        PanelGrid panelGrid = new PanelGrid(u);
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
        ArrayList<String> categorias = new ArrayList<String>();
        JButton anadirCat, borrarCat, salir, botonIzq, botonDer;

        public PanelGrid(Usuario u) {
            this.setLayout(new GridLayout(2, 1, 10, 10));
            this.setOpaque(false);

            p1 = crearPanel1(u);
            this.add(p1);
            p2 = crearPanel2(u);
            this.add(p2);

            repaint();
        }

        class EscuchaRaton extends MouseAdapter implements FocusListener, ActionListener {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == anadirCat) {
                    anadirCat.setBounds(568, 97, 164, 47);
                    anadirCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 3));
                    anadirCat.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
                } else if (e.getSource() == borrarCat) {
                    borrarCat.setBounds(568, 157, 164, 47);
                    borrarCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 3));
                    borrarCat.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == anadirCat) {
                    anadirCat.setBounds(572, 100, 156, 40);
                    anadirCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
                    anadirCat.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
                } else if (e.getSource() == borrarCat) {
                    borrarCat.setBounds(572, 160, 156, 40);
                    borrarCat.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
                    borrarCat.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
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
                if (e.getSource() == salir) {
                    System.exit(ABORT);
                }
            }

        }

        private JPanel crearPanel1(Usuario u) {
            JPanel p = new JPanel();
            p.setLayout(null);

            PieChart pie = new PieChart(getWidth(), getHeight(), Styler.ChartTheme.XChart);
            pie.getStyler().setChartBackgroundColor(new Color(255, 255, 255, 0));
            BaseDatos.categoriasExistentes(u, categorias);
            for (int i = 0; i < categorias.size(); i++) {
                pie.removeSeries(categorias.get(i));
            }
            pie.addSeries("Ingresos", 10);
            for (int i = 0; i < categorias.size(); i++) {
                if (!pie.getSeriesMap().containsKey(categorias.get(i))) {
                    pie.addSeries(categorias.get(i), 10);
                }
            }
            pie.getStyler().setPlotContentSize(0.9);
            pie.getStyler().setChartPadding(20);
            pie.getStyler().setPlotBackgroundColor(null);
            pie.getStyler().setPlotBorderVisible(false);
            pie.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
            pie.getStyler().setDonutThickness(0.6);
            pie.getStyler().setLabelsFont(new Font("Malgun Gothic", Font.PLAIN, 12));
            pie.getStyler().setLegendFont(new Font("Malgun Gothic", Font.PLAIN, 16));
            pie.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
            pie.getStyler().setLegendBackgroundColor(new Color(255, 255, 255, 255));
            pie.getStyler().setLegendBorderColor(null);

            XChartPanel<PieChart> chartPanel = new XChartPanel<>(pie);
            chartPanel.setBackground(new Color(82, 102, 218, 0));
            chartPanel.setSize(500, 300);
            chartPanel.setLayout(null);
            PanelNull panelImagen = new PanelNull();
            chartPanel.add(panelImagen);
            p.add(chartPanel);

            anadirCat = crearBoton("Añadir categoría", new EscuchaRaton());
            anadirCat.setBounds(572, 100, 156, 40);
            anadirCat.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Categoria c = new Categoria(p, u);        // Nueva ventana
                }
            });
            p.add(anadirCat);

            borrarCat = crearBoton("Borrar categoría", new EscuchaRaton());
            borrarCat.setBounds(572, 160, 156, 40);
            borrarCat.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Borrada");        // Hacer panel para elegir cuál borrar
                }
            });
            p.add(borrarCat);

            salir = crearBotonImagen("src/main/resources/close.png", 25, 25, new EscuchaRaton());
            salir.setBounds(767, 7, 24, 24);
            p.add(salir);

            p.setOpaque(false);
            return p;
        }

        private JPanel crearPanel2(Usuario u) {
            JPanel p = new JPanel();
            p.setLayout(null);
            p.setBounds(0, 0, getWidth(), getHeight());
            JLabel textoIngresos = crearLabel("Ingresos: 1600€", 0, 23);    // Variables
            textoIngresos.setBounds(82, 40, 200, 35);
            JLabel textoGastos = crearLabel("Gastos: 1000€", 0, 23);        // Variables
            textoGastos.setBounds(82, 90, 200, 35);
            JLabel textoMes = crearLabel("Septiembre", 1, 28);        // Variables
            textoMes.setBounds(92, 170, 180, 40);
            JLabel textoNombre = crearLabel(u.getNombre(), 0, 18);
            textoNombre.setBounds(92, 210, 180, 40);
            botonIzq = crearBotonImagen("src/main/resources/leftArrow.png", 25, 20, new EscuchaRaton());
            botonIzq.setBounds(73, 181, 25, 25);
            botonIzq.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Izquierda");
                }
            });
            botonDer = crearBotonImagen("src/main/resources/rightArrow.png", 25, 20, new EscuchaRaton());
            botonDer.setBounds(266, 181, 25, 25);
            botonDer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Derecha");
                }
            });
            p.add(textoIngresos);
            p.add(textoGastos);
            p.add(textoMes);
            p.add(botonIzq);
            p.add(botonDer);
            p.add(textoNombre);
            p.setOpaque(false);
            return p;
        }

        private JLabel crearLabel(String palabras, int tipo, int tamano) {
            JLabel texto = new JLabel(palabras);
            texto.setBackground(null);
            texto.setHorizontalAlignment(SwingConstants.CENTER);
            texto.setFont(new Font("Malgun Gothic", tipo, tamano));
            texto.setOpaque(false);
            return texto;
        }

        private JButton crearBoton(String texto, EscuchaRaton e) {
            JButton b = new JButton(texto);
            b.setFocusable(false);
            b.setForeground((new Color(Inicio.COLOR1)));
            b.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setBackground(new Color(Inicio.COLOR4));
            b.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
            b.setOpaque(true);
            b.addActionListener(e);
            b.addMouseListener(e);
            return b;
        }

        private JButton crearBotonImagen(String rutaImagen, int width, int height, ActionListener a) {
            JButton b = new JButton();
            Image icono = new ImageIcon(rutaImagen).getImage();
            ImageIcon exit = new ImageIcon(icono.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            b.setBorderPainted(false);
            b.setFocusable(false);
            b.setContentAreaFilled(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setIcon(exit);
            b.addActionListener(a);
            return b;
        }

    }

}
