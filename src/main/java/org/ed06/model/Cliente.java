package org.ed06.model;

/**
 * Clase que representa al cliente de un hotel
 */
public class Cliente {
    public int id;
    public String nombre;
    public String dni;
    public String email;
    public boolean esVip;

    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        if(validarNombre(nombre)) {
            this.nombre = nombre;
        }
        if(validarDni(dni)) {
            this.dni = dni;
        }
        if(validarEmail(email)) {
            this.email = email;
        }
        this.esVip = esVip;
    }

    /**
     * Metodo para verificar el nombre del cliente
     * @param nombre Nombre introducido por el cliente
     * @return True si el nombre es válido
     * @throws IllegalArgumentException si el nombre es nulo o menor a 3 caracteres sin espacios
     */
    public static boolean validarNombre(String nombre) {
        // Se comprueba que el nombre es válido
        if (nombre == null || nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        return true;
    }

    /**
     * Metodo para verificar el email del cliente
     * @param email Email introducido por el cliente
     * @return True si el email es válido
     * @throws IllegalArgumentException si el email no cumple con las expresiones regulares
     */
    public static boolean validarEmail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("El email no es válido");
        }
        return true;
    }

    /**
     * Metodo para verificar el DNI del cliente
     * @param dni DNI introducido por el cliente
     * @return True si el DNI es válido
     * @throws IllegalArgumentException si el DNI no cumple con la expresión regular
     */
    public static boolean validarDni(String dni) {
        if (!dni.matches("[0-9]{8}[A-Z]")) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        return true;
    }

}