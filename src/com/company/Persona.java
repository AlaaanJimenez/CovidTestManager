package com.company;

public abstract class Persona {

    private int dni;
    private String nombre;
    private char sexo;

    public Persona(int dni, String nombre, char sexo) {
        setDni(dni);
        setNombre(nombre);
        // realizar excepcion si pone un char que no se M o F
        setSexo(sexo);
    }
    public Persona()
    {

    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", sexo=" + sexo +
                '}';
    }


}
