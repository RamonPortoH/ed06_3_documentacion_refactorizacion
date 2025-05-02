package org.ed06.model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Clase correspondiente a la reserva de una habitación
 */
public class Reserva {
    private int id;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;

    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    public int getId() {
        return id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // Calcula el precio total de la reserva.

    /**
     * Metodo que calcula el precio Final de la reserva
     * @return El precio final hechos los descuentos correspondientes
     */
    public double calcularPrecioFinal() {
        int nDias = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();
        double precioFinal = habitacion.getPrecioBase() * nDias;

        // Si el cliente es VIP, aplicamos un descuento del 10%
        if (cliente.esVip) {
            precioFinal = DescuentoVIP.aplicarDescuento(precioFinal);
        }

        // Si el intervalo de fechas es mayor a 7 días, aplicamos un descuento adicional del 5%
        if (nDias > 7) {
            precioFinal = DescuentoMas7Dias.aplicarDescuento(precioFinal);
        }

        return precioFinal;
    }

    /**
     * Clase para realizar un descuento especial a Clientes VIPs
     */
    public static class DescuentoVIP extends Descuento{
        /**
         * Implementación del metodo que hace el descuento
         * @param precio precio final antes de aplicar el descuento
         * @return precio aplicado el descuento a VIPs
         */
        public static double aplicarDescuento(double precio){
            double descuentoVIPS = 0.9;
            return precio * descuentoVIPS;
        }
    }

    /**
     * Clase para realizar un descuento especial si la estancia supera los 7 días
     */
    public static class DescuentoMas7Dias extends Descuento{
        /**
         * Implementación del metodo que hace el descuento
         * @param precio precio final antes de aplicar el descuento
         * @return precio aplicado el descuento por estancia mayor a 7 días
         */
        public static double aplicarDescuento(double precio){
            double descuentoMas7Dias = 0.95;
            return precio * descuentoMas7Dias;
        }
    }

}
