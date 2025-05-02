package org.ed06.model;

public class Habitacion {
    private int numero;
    private String tipo; // "SIMPLE", "DOBLE", "SUITE"
    private double precioBase;


    private boolean disponible;

    public Habitacion(int numero, String tipo, double precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Metodo para confirmar una reserva de la habitación y marcarla como no disponible
     */
    public void reservar() {
        if (disponible) {
            System.out.println("Habitación #" + numero + " ya reservada");
        }
        disponible = false;
    }
}
