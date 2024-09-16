package org.redondo.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.redondo.bd.BaseDatos;
import org.redondo.logica.Usuario;

public class Inicio extends JFrame {

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

    public Inicio() {

        ImageIcon icono = new ImageIcon("src/main/resources/Piggy.png");

        this.setUndecorated(true);
        this.setIconImage(icono.getImage());
        this.setTitle("Piggy Bank");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(295, 360);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        MiPanel panel = new MiPanel();
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

        JLabel labelGradiente, labelImagen, textoUsuario, textoContrasena;
        JTextField nombre;
        JPasswordField contrasena;
        JButton logIn, signIn, salir;
        boolean datosCorrectos;
        static ArrayList<String> errores = new ArrayList<String>();

        public MiPanel() {
            this.setLayout(null);

            labelImagen = new JLabel();
            labelImagen.setBounds(79, 18, 140, 155);
            Image img = new ImageIcon("src/main/resources/Piggy.png").getImage();
            ImageIcon img2 = new ImageIcon(img.getScaledInstance(135, 145, Image.SCALE_SMOOTH));
            labelImagen.setIcon(img2);
            this.add(labelImagen);

            textoUsuario = crearTexto("Usuario");
            textoUsuario.setBounds(37, 170, 225, 25);
            this.add(textoUsuario);
            textoUsuario = crearTexto("Usuario");
            textoUsuario.setBounds(37, 170, 225, 25);
            this.add(textoUsuario);

            textoContrasena = crearTexto("Contraseña");
            textoContrasena.setBounds(37, 227, 225, 25);
            this.add(textoContrasena);
            textoContrasena = crearTexto("Contraseña");
            textoContrasena.setBounds(37, 227, 225, 25);
            this.add(textoContrasena);

            nombre = crearCampoTexto(new escuchaRaton());
            nombre.setBounds(34, 193, 225, 32);
            this.add(nombre);

            contrasena = crearCampoContrasena(new escuchaRaton());
            contrasena.setBounds(34, 250, 225, 32);
            this.add(contrasena);

            logIn = crearBoton("Log in", new escuchaRaton());
            logIn.setBounds(35, 297, 80, 28);
            this.add(logIn);

            signIn = crearBoton("Sign in", new escuchaRaton());
            signIn.setBounds(178, 297, 80, 28);
            this.add(signIn);

            textoUsuario = crearTexto("Versión 1.0");
            textoUsuario.setFont(new Font("Malgun Gothic", Font.PLAIN, 9));
            textoUsuario.setForeground(new Color(Inicio.COLOR0).brighter().brighter());
            textoUsuario.setBounds(240, 335, 100, 25);
            this.add(textoUsuario);

            salir = crearBotonSalir(new escuchaRaton());
            salir.setBounds(262, 7, 24, 24);
            this.add(salir);

            labelGradiente = new JLabel();
            labelGradiente.setBounds(0, 0, 350, 400);
            Image gradiente = new ImageIcon("src/main/resources/gradient.png").getImage();
            labelGradiente.setIcon(new ImageIcon((gradiente)));
            labelGradiente.setOpaque(false); // IMPORTANTE dejarlo transparente
            this.add(labelGradiente);

            this.setOpaque(false);

        }

        class escuchaRaton extends MouseAdapter implements FocusListener, ActionListener {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == logIn) {
                    logIn.setBounds(32, 294, 87, 35);
                    logIn.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
                    logIn.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
                } else if (e.getSource() == signIn) {
                    signIn.setBounds(175, 294, 87, 35);
                    signIn.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR1), 1));
                    signIn.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == logIn) {
                    logIn.setBounds(35, 297, 80, 28);
                    logIn.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
                    logIn.setFont(new Font("Malgun Gothic", Font.BOLD, 15));

                } else if (e.getSource() == signIn) {
                    signIn.setBounds(178, 297, 80, 28);
                    signIn.setBorder(BorderFactory.createLineBorder(new Color(Inicio.COLOR4), 2));
                    signIn.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if ((e.getSource() == nombre || e.getSource() == contrasena) && !datosCorrectos && intento) {
                    nombre.setBackground(new Color(Inicio.COLOR1));
                    contrasena.setBackground(new Color(Inicio.COLOR1));
                }
                intento = false;
            }

            @Override
            public void focusLost(FocusEvent e) {
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == logIn) {
                    intento = true;
                    String nombreUsuario = nombre.getText();
                    String contrasenaUsuario = String.valueOf(contrasena.getPassword());
                    Usuario u = BaseDatos.logIn(nombreUsuario, contrasenaUsuario);
                    datosCorrectos = (u != null);
                    salir.requestFocusInWindow();
                    if (u != null) {
                        dispose();
                        System.out.println("Entraste");
                        PanelPrincipal panel = new PanelPrincipal(u); //  SIGUIENTE VENTANA
                    } else {
                        System.out.println("No existe");
                        nombre.setBackground(new Color(Inicio.ERROR));
                        contrasena.setBackground(new Color(Inicio.ERROR));
                    }
                } else if (e.getSource() == signIn) {
                    intento = true;
                    String nombreUsuario = nombre.getText();
                    String contrasenaUsuario = String.valueOf(contrasena.getPassword());
                    errores = new ArrayList<String>();
                    errores = BaseDatos.signIn(errores, nombreUsuario, contrasenaUsuario);
                    if (!nombreUsuario.isEmpty() && !contrasenaUsuario.isEmpty() && errores.isEmpty()) {
                        salir.requestFocusInWindow();
                        dispose();
                        Inicio i = new Inicio();
                        System.out.println("Usuario creado");
                        errores.add("Usuario creado con éxito");
                        ventanaError = new ErrorV(errores);
                    } else {
                        ventanaError = new ErrorV(errores);
                        System.out.println("No creado");
                        nombre.setBackground(new Color(Inicio.ERROR));
                        contrasena.setBackground(new Color(Inicio.ERROR));
                    }
                } else {
                    System.exit(ABORT);
                }
            }

        }

        private JLabel crearTexto(String texto) {
            JLabel l = new JLabel(texto);
            l.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));
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

        private JPasswordField crearCampoContrasena(FocusListener f) {
            JPasswordField contrasena = new JPasswordField();
            contrasena.setForeground(new Color(Inicio.COLOR0).brighter().brighter());
            contrasena.setBackground(new Color(Inicio.COLOR1));
            contrasena.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
            Border outerBorder = BorderFactory.createLineBorder(new Color(Inicio.COLOR3), 1, true);
            Border innerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
            contrasena.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
            contrasena.addFocusListener(f);
            return contrasena;
        }

        private JButton crearBoton(String texto, escuchaRaton e) {
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

}