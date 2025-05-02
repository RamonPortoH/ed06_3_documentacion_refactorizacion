package org.ed06.model;

/**
 * Clase abstracta que representa una plantilla para aplicar diversos
 * tipos de descuentos
 */
public abstract class Descuento {
    /**
     * Metodo para aplicar el descuento
     * @param precio Precio final antes de aplicar descuentos
     * @return El precio una vez descontado
     */
    public static double aplicarDescuento(double precio){
        return precio;
    }
}
