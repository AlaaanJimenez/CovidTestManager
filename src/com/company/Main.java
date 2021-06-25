package com.company;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Sistema sistem=new Sistema();
        //sistem.ingresarPacienteXteclado();
       // sistem.ingresarPacienteXteclado();
        //sistem.MostrarArchivoPacientes();
        //sistem.cargarPacientes();
        //sistem.GenerarTurnoVacunacion();
        //sistem.GenerarTurnoVacunacion();

        //sistem.MostrarMapaTurnos();
        //sistem.VerificarTurnosDiadeHoy();
        //sistem.busquedaPacientexID(2);



        int opcion=0;
        Scanner scan=new Scanner(System.in);

        while (opcion!=7) {
            System.out.println("\n \n1)Ingresar Paciente por teclado\n2) Mostrar Archivo Pacientes\n3)Generar turno de vacunacion\n4)Verificar turnos programados para hoy\n5)Mostrar Mapa Turnos Archivo\n6)Realizar Testeo\n7)Salir\n");
            opcion=scan.nextInt();
            switch (opcion) {
                case 1: sistem.ingresarPacienteXteclado();
                        break;

                case 2:sistem.MostrarArchivoPacientes();
                        break;

                case 3:sistem.GenerarTurnoVacunacion();
                        break;
                case 4:sistem.VerificarTurnosDiadeHoy();
                        break;
                case 5: sistem.MostrarMapaTurnos();
                        break;
                case 6:sistem.realizarTesteo();



            }
        }
    }








}
