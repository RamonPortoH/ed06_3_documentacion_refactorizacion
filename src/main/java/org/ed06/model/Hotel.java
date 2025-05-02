package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

/**
 * Clase que es una representación de un Hotel
 */
public class Hotel {
    private String nombre;
    private String direccion;
    private String telefono;

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    public Hotel(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Metodo para agregar una nueva habitación al hotel

    /**
     * Metodo para registrar una Habitación
     * @param tipo Tipo de la habitación
     * @param precioBase Precio de la habitación
     */
    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
    }

    /**
     * Metodo que lista habitaciones disponibles
     */
    public void listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isDisponible()) {
                System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
            }
        }
    }

    /**
     * Metodo para obtener una habitación según su número
     * @param numero Número de la Habitación
     * @return Habitación correspondiente al número
     */
    public Habitacion getHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    //Metodo que realiza una reserva.
    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Comprobamos si hay habitaciones en el hotel
        if (habitaciones.isEmpty()){
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }
        // Comprobamos si existe el cliente
        Cliente cliente = this.clientes.get(clienteId);

        if (cliente == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return -3;
        }

        if (!fechaCoherente(fechaEntrada, fechaSalida)){
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -2;
        }

        Habitacion habitacion = habitacionDisponibleSegunTipo(tipo); //Se encuentra una habitación disponible

        //Si es nula da mensaje de error
        if (habitacion == null){
            System.out.println("No hay habitaciones disponibles del tipo " + tipo);
            return -1;
        }

        nuevoClienteVip(clienteId);

        // Creamos la reserva
        Reserva reserva = new Reserva(reservasPorHabitacion.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
        reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);
        // Marcamos la habitación como no disponible
        habitacion.reservar();

        System.out.println("Reserva realizada con éxito");
        return habitacion.getNumero();

    }

    /**
     * Metodo que comprueba si las fechas son coherentes. La fecha de entrada no puede ser mayor a la de salida.
     * @param fechaEntrada Fecha de entrada del húesped
     * @param fechaSalida Fecha de salida del húesped
     * @return True o false si son coherentes.
     */
    //Metodo para comprobar si las fechas son correctas
    public boolean fechaCoherente(LocalDate fechaEntrada, LocalDate fechaSalida){
        if(fechaEntrada.isBefore(fechaSalida)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo que comprueba si hay habitaciones disponibles del tipo deseado por el cliente
     * @param tipo Tipo de habitación que desea el cliente
     * @return True o False si hay habitaciones disponibles de ese tipo.
     */
    //Metodo para comprobar si existen habitaciones del tipo deseado
    public Habitacion habitacionDisponibleSegunTipo(String tipo){
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getTipo().equals(tipo.toUpperCase()) && habitacion.isDisponible()){
                return habitacion;
            }
        }
        return null;
    }

    /**
     * Metodo que convierte un cliente normal a VIP en función del número de reservas.
     * Si alcanza las necesarias se convertirá en cliente VIP, en caso contrario no ocurre nada.
     * @param clienteId Id del cliente que realiza la reserva
     */
    //Metodo que convierte a un cliente en VIP
    public void nuevoClienteVip(int clienteId){
        int numReservas = 0;
        Cliente cliente = this.clientes.get(clienteId);
        for (List<Reserva> reservasHabitacion : reservasPorHabitacion.values()) {
            for(Reserva reservaCliente : reservasHabitacion) {
                if(reservaCliente.getCliente().equals(cliente)) {
                    if(reservaCliente.getFechaInicio().isAfter(LocalDate.now().minusYears(1))) {
                        numReservas++;
                    }
                }
            }
        }
        if(numReservas > 3 && !cliente.esVip) {
            cliente.esVip = true;
            System.out.println("El cliente " + cliente.nombre + " ha pasado a ser VIP");
        }
    }

    /**
     * Metodo para listar las reservas hechas
     */
    public void listarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(reserva -> System.out.println(
                "Reserva #" + reserva.getId() + " - Cliente: " + reserva.getCliente().nombre
                    + " - Fecha de entrada: " + reserva.getFechaInicio()
                    + " - Fecha de salida: " + reserva.getFechaFin()));
        });
    }

    /**
     * Metodo que lista los clientes registrados en el hotel
     */
    public void listarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.id + " - Nombre: " + cliente.nombre + " - DNI: " + cliente.dni + " - VIP: " + cliente.esVip);
        }
    }

    /**
     * Metodo para registrar a un nuevo cliente
     * @param nombre Nombre del cliente
     * @param email Email del cliente
     * @param dni DNI del cliente
     * @param esVip El cliente posee membresía VIP o no
     */
    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.id, cliente);
    }
}
