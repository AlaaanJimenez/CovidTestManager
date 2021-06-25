package com.company;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
public class Personal extends Persona {


=======

public class Personal extends Persona {

    private static int identificador;
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    private int id;
    private char sexo;
    private int sueldo;



    public Personal(int dni, String nombre, char sexo, int id, Barrios barrio,int sueldo) {
        super(dni, nombre, sexo);
<<<<<<< HEAD
        setId(super.getId());

=======
        setId(identificador);
        setIdentificador();
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    }


    @Override
    public char getSexo() {
        return sexo;
    }


    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

<<<<<<< HEAD
=======
    public static int getIdentificador() {
        return identificador;
    }

    public static void setIdentificador() {
        Personal.identificador = identificador++;

    }
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40

    public int getId() {
        return id;
    }


    public void setId(int id) {
<<<<<<< HEAD
        id=id+1;
=======
        this.id = id;
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    }

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", sexo=" + sexo +
                ", sueldo=" + sueldo +
                '}';
    }
}
