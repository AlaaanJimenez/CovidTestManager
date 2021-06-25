package com.company;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public abstract class Persona {

    private static int id;
    private int identificador;
=======

public abstract class Persona {

>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    private int dni;
    private String nombre;
    private char sexo;

    public Persona(int dni, String nombre, char sexo) {
        setDni(dni);
        setNombre(nombre);
<<<<<<< HEAD
        setId();
        setidentificador();
        // realizar excepcion si pone un char que no se M o F
        setSexo(sexo);
    }

    public static int getIdentificador() {
        return id;
    }

    private void setId() {
        this.identificador = id;
        id = id + 1;
    }
public int getId()
{
    return this.id;
}
=======
        // realizar excepcion si pone un char que no se M o F
        setSexo(sexo);
    }
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    public Persona()
    {

    }
<<<<<<< HEAD
    private void setidentificador()
    {
        this.identificador=identificador++;
    }
=======

>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
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
