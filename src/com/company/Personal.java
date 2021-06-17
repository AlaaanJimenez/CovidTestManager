package com.company;

public class Personal extends Persona {

    private static int identificador;
    private int id;
    private char sexo;
    private int sueldo;



    public Personal(int dni, String nombre, char sexo, int id, Barrios barrio,int sueldo) {
        super(dni, nombre, sexo);
        setId(identificador);
        setIdentificador();
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

    public static int getIdentificador() {
        return identificador;
    }

    public static void setIdentificador() {
        Personal.identificador = identificador++;

    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
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
