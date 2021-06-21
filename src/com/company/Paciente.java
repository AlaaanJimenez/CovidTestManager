package com.company;

import java.io.Serializable;
import java.util.Objects;

public class Paciente extends Persona implements Serializable
{
    private static int identificador;
    private int id;
    private Barrios barrio;

    public Paciente(int dni, String nombre, char sexo, Barrios barrio) {
        super(dni, nombre, sexo);
        setBarrio(barrio);
        setId(this.identificador);
        setIdentificador();
    }
    public Paciente ()
    {

    }

    public static int getIdentificador() {
        return identificador;
    }

    public static void setIdentificador() {
    identificador++;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Barrios getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrios barrio) {
        this.barrio = barrio;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente paciente = (Paciente) o;
        return id == paciente.id &&
                barrio == paciente.barrio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, barrio);
    }

    @Override
    public String toString() {
        return super.toString()+"Paciente{" +
                "id=" + id +
                ", barrio=" + barrio +
                '}';
    }
}
