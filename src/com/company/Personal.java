package com.company;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
public class Personal extends Persona {


    private int id;
    private char sexo;
    private int sueldo;



    public Personal(int dni, String nombre, char sexo, int id, Barrios barrio,int sueldo) {
        super(dni, nombre, sexo);
        setId(super.getId());

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


    public int getId() {
        return id;
    }


    public void setId(int id) {
        id=id+1;
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
