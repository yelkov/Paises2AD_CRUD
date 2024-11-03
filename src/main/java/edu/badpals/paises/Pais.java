package edu.badpals.paises;

import java.util.Objects;

public class Pais {
    private String nombre;
    private int num_habitantes;
    private String capital;
    private String moneda;

    public Pais() {
    }

    public Pais(String nombre, int num_habitantes, String capital, String moneda) {
        this.nombre = nombre;
        this.num_habitantes = num_habitantes;
        this.capital = capital;
        this.moneda = moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNum_habitantes() {
        return num_habitantes;
    }

    public void setNum_habitantes(int num_habitantes) {
        this.num_habitantes = num_habitantes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\tPaís: ");
        sb.append("\n\t\t Nombre: " + nombre);
        sb.append("\n\t\t Número de habitantes: " + num_habitantes);
        sb.append("\n\t\t Capital: " + capital);
        sb.append("\n\t\t Moneda: " + moneda);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pais pais)) return false;
        return Objects.equals(nombre, pais.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}
