package com.company;

<<<<<<< HEAD
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public class Paciente extends Persona implements Serializable
{
    private int id;
    private Barrios barrio;
    private String USER_PATH=System.getProperty("user.dir");
    private String USUARIOS_PATH=""+USER_PATH+"\\usuarios";;
=======
import java.io.Serializable;

public class Paciente extends Persona implements Serializable
{
    private static int identificador;
    private int id;
    private Barrios barrio;
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40

    public Paciente(int dni, String nombre, char sexo, Barrios barrio) {
        super(dni, nombre, sexo);
        setBarrio(barrio);
<<<<<<< HEAD
        setId();

=======
        setId(this.identificador);
        setIdentificador();
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    }
    public Paciente ()
    {

    }

<<<<<<< HEAD
=======
    public static int getIdentificador() {
        return identificador;
    }

    public static void setIdentificador() {
    identificador++;
        }
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40

    public int getId() {
        return id;
    }

<<<<<<< HEAD
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


=======
    public void setId(int id) {
        this.id = id;
    }



>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    public Barrios getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrios barrio) {
        this.barrio = barrio;
    }


    @Override
<<<<<<< HEAD
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
=======
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
    public String toString() {
        return super.toString()+"Paciente{" +
                "id=" + id +
                ", barrio=" + barrio +
                '}';
    }
}
