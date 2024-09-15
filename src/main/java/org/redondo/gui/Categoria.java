package org.redondo.gui;

import org.junit.jupiter.api.parallel.Resources;
import org.redondo.bd.BaseDatos;
import org.redondo.logica.Usuario;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Categoria extends JFrame {

    final static int COLOR0 = 0x111111;
    final static int COLOR1 = 0xFAFAFA;
    final static int COLOR2 = 0xBFD9D2;
    final static int COLOR3 = 0xFFDCD1;
    final static int COLOR4 = 0xF6AB8C;
    final static int COLOR5 = 0xEEB805;
    final static int ERROR = 0xFF8065;

    private Point coordenadasPinchar;
    boolean intento = false;

    public static ErrorV ventanaError;

    public Categoria(JPanel p, Usuario u) {

        ImageIcon icono = new ImageIcon("src/main/resources/Piggy.png");

        this.setUndecorated(true);
        this.setIconImage(icono.getImage());
        this.setTitle("Piggy Bank");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(295, 155);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getRootPane().setBorder(new LineBorder(Color.WHITE, 3));

        MiPanel panel = new MiPanel(p, u);
        this.add(panel);

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


    class MiPanel extends JPanel {

        JLabel labelGradiente, textoNom, textoError;
        JTextField nombre;
        JButton anadir, salir;
        boolean datosCorrectos;
        static ArrayList<String> errores = new ArrayList<String>();

        public MiPanel(JPanel p, Usuario u) {
            this.setLayout(null);

            textoNom = crearTexto("Nombre");
            textoNom.setBounds(37, 20, 225, 25);
            this.add(textoNom);

            nombre = crearCampoTexto(new escuchaRaton());
            nombre.setBounds(34, 47, 225, 35);
            nombre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == anadir) {
                        intento = true;
                        String nombreCat = nombre.getText();
                        // u = BaseDatos.verificarNombreCat(nombreCat); HACER QUERY METER CAT
                        datosCorrectos = (u != null);
                        salir.requestFocusInWindow();
                        if (datosCorrectos) {
                            dispose();
                            System.out.println("Categoría añadida con éxito");
                            PanelPrincipal panel = new PanelPrincipal(u); //  SIGUIENTE VENTANA
                        } else if (!datosCorrectos) {
                            ventanaError = new ErrorV(errores);
                            System.out.println("Nombre de categoría vacío o usado.");
                            nombre.setBackground(new Color(Inicio.ERROR));
                        }
                    } else {
                        System.exit(ABORT);
                    }
                }
            });
            this.add(nombre);

            textoError = new JLabel("Nombre de categoría utilizado");
            textoError.setForeground(new Color(Inicio.ERROR));
            textoError.setBounds(60,80,170,20);
            textoError.setVisible(false);
            this.add(textoError);

            anadir = crearBoton("Añadir", new escuchaRaton()); // ARREGLAR HOVER
            anadir.setBounds(90, 100, 100, 35);
            anadir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == anadir) {
                        intento = true;
                        boolean existe = false;
                            existe = BaseDatos.existeCategoria(nombre.getText());   // AÑADIR EN BD Y VER SI FUNCIONA
                        if (nombre.getText().isEmpty()) {
                            nombre.setBackground(new Color(Inicio.ERROR));
                            salir.requestFocusInWindow();
                        } else if (existe) {
                            System.out.println("Añadido");
                            textoError.setVisible(true);
                            salir.requestFocusInWindow();
                            repaint();
                        } else {

                        }
                    }
                }
            });
            this.add(anadir);

            salir = crearBotonSalir();
            salir.setBounds(262, 7, 24, 24);
            salir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == salir) {
                        dispose();
                        p.repaint();    // Se repinta el chart
                    }
                }
            });
            this.add(salir);

            labelGradiente = new JLabel();
            labelGradiente.setBounds(0, 0, 350, 400);
            labelGradiente.setBackground(new Color(0xFFE8E0));
            labelGradiente.setOpaque(true); // IMPORTANTE dejarlo transparente
            this.add(labelGradiente);

            this.setOpaque(false);

        }


        class escuchaRaton extends MouseAdapter implements FocusListener {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == anadir) {
                    anadir.setBounds(87, 97, 107, 42);
                    anadir.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
                    anadir.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == anadir) {
                    anadir.setBounds(90, 100, 100, 35);
                    anadir.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
                    anadir.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if ((e.getSource() == nombre)) {
                    nombre.setBackground(new Color(Inicio.COLOR1));
                    intento = false;
                    textoError.setVisible(false);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }

        }

    }

    private JLabel crearTexto(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
        l.setForeground(new Color(Inicio.COLOR5));
        return l;
    }

    private JTextField crearCampoTexto(FocusListener f) {
        JTextField t = new JTextField();
        t.setForeground(new Color(Inicio.COLOR0).brighter().brighter());
        t.setBackground(new Color(Inicio.COLOR1));
        t.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        Border outerBorder = BorderFactory.createLineBorder(new Color(Inicio.COLOR3), 1, true);
        Border innerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
        t.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        t.addFocusListener(f);
        return t;
    }

    private JButton crearBoton(String texto, MiPanel.escuchaRaton e) {
        JButton b = new JButton(texto);
        b.setFocusable(false);
        b.setForeground((new Color(Inicio.COLOR1)));
        b.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBackground(new Color(Inicio.COLOR4));
        b.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
        b.setOpaque(true);
        b.addMouseListener(e);
        return b;
    }

    private JButton crearBotonSalir() {
        JButton b = new JButton();
        Image icono = new ImageIcon("src/main/resources/close.png").getImage();
        ImageIcon exit = new ImageIcon(icono.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setIcon(exit);
        return b;
    }

}