package com.company;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public class Paciente extends Persona implements Serializable
{
    private int id;
    private Barrios barrio;
    private String USER_PATH=System.getProperty("user.dir");
    private String USUARIOS_PATH=""+USER_PATH+"\\usuarios";;

    public Paciente(int dni, String nombre, char sexo, Barrios barrio) {
        super(dni, nombre, sexo);
        setBarrio(barrio);
        setId();

    }
    public Paciente ()
    {

    }


    public int getId() {
        return id;
    }

    public void setId() {
        File file = new File(USUARIOS_PATH);
        if(file.isDirectory())
        {
            File files[]=file.listFiles();
            id=files.length+1;
        }
        else{
            id=0;
        }
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
