package org.redondo.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.redondo.logica.Usuario;

public class BaseDatos {

    private static String url = "jdbc:sqlite:database.db";
    private static Connection c = conectar();

    private static Connection conectar() {
        try {
            c = DriverManager.getConnection(url);
            System.out.println("Conectado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Usuario logIn(String nombre, String contrasena) {
        String queryVerificacion = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ?";
        PreparedStatement s;
        try {
            s = c.prepareStatement(queryVerificacion);
            s.setString(1, nombre);
            s.setString(2, contrasena);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
                System.out.println(u.toString());
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> signIn(ArrayList<String> errores, String nombre, String contrasena) {
        errores = usuarioExistente(errores, nombre, contrasena);
        if (!errores.isEmpty()) {
            return errores;
        }
        errores = credencialesCorrectas(errores, nombre, contrasena);
        if (errores.isEmpty()) {
            String crearUsuario = "INSERT INTO usuario (nombre, contraseña) VALUES (?,?)";
            PreparedStatement s;
            try {
                s = c.prepareStatement(crearUsuario);
                s.setString(1, nombre);
                s.setString(2, contrasena);
                s.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return errores;
    }

    public static ArrayList<String> usuarioExistente(ArrayList<String> errores, String nombre, String contrasena) {
        String buscarCoincidencia = "SELECT nombre FROM usuario WHERE nombre = ?";
        PreparedStatement s;
        try {
            s = c.prepareStatement(buscarCoincidencia);
            s.setString(1, nombre);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                errores.add("Nombre de usuario existente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return errores;
    }

    public static ArrayList<String> credencialesCorrectas(ArrayList<String> errores, String nombre, String contrasena) {
        boolean mayMinCon;
        if (nombre.length() < 4 || nombre.length() > 12) {
            errores.add("El nombre debe tener entre 4 y 12 caracteres");
        }
        if (!nombre.matches("^[a-zA-Z0-9_]+$")) {
            errores.add("El nombre no puede contener caracteres extraños");
        }
        if (contrasena.length() < 4 || contrasena.length() > 12) {
            errores.add("La contraseña debe tener entre 4 y 12 caracteres");
        }
        mayMinCon = mayMinContrasena(contrasena);
        if (!mayMinCon) {
            errores.add("La contraseña debe contener mayúsculas y minúsculas");
        }
        return errores;
    }

    public static boolean mayMinContrasena(String contrasena) {
        int may = 0, min = 0;
        for (int i = 0; i < contrasena.length(); i++) {
            if (contrasena.charAt(i) >= 'A' && contrasena.charAt(i) <= 'Z') {
                may++;
            } else if (contrasena.charAt(i) >= 'a' && contrasena.charAt(i) <= 'z') {
                min++;
            }
        }
        return may != 0 && min != 0;
    }

    public static boolean existeCategoria(String nombreCat, Usuario u) {
        String buscarCoincidencia = "SELECT categoría FROM gastos WHERE LOWER(categoría) = LOWER(?) AND id_usuario = ?";
        PreparedStatement s;
        try {
            s = c.prepareStatement(buscarCoincidencia);
            s.setString(1, nombreCat.trim());
            s.setInt(2, u.getId());
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("No coincide");
        return false;
    }

    public static void anadirCategoria(String nombreCat, Usuario u) {
        String crearUsuario = "INSERT INTO gastos (categoría, id_usuario) VALUES (?,?)";
        PreparedStatement s;
        try {
            s = c.prepareStatement(crearUsuario);
            s.setString(1, nombreCat);
            s.setInt(2, u.getId());
            System.out.println(u.getId());
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> categoriasExistentes(Usuario u, ArrayList<String> categorias) {
        String buscarCoincidencia = "SELECT categoría FROM gastos WHERE id_usuario = ?";
        PreparedStatement s;
        try {
            s = c.prepareStatement(buscarCoincidencia);
            s.setInt(1, u.getId());
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                categorias.add(rs.getString("categoría"));
            }
            for(String c : categorias) {
            System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

}

