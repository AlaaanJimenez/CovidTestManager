package com.company;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Sistema sistem=new Sistema();
       // sistem.ingresarPacienteXteclado();
        //sistem.ingresarPacienteXteclado();
        //sistem.MostrarArchivoPacientes();
        //sistem.cargarPacientes();
        sistem.GenerarTurnoVacunacion();
        sistem.GenerarTurnoVacunacion();

        sistem.MostrarMapaTurnos();
        //sistem.VerificarTurnosDiadeHoy();
        //sistem.busquedaPacientexID(2);
    }
}
