package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Sistema sistem=new Sistema();
        //sistem.ingresarPacienteXteclado();
        //sistem.ingresarPacienteXteclado();

        sistem.cargarPacientes();
        //sistem.GenerarTurnoVacunacion();

        //sistem.MostrarMapaTurnos();
        sistem.VerificarTurnosDiadeHoy();
        //sistem.busquedaPacientexID(2);
    }
}
