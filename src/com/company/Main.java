package com.company;
<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
import java.util.*;

public class Main {

<<<<<<< HEAD
    public static void main(String[] args) throws IOException {

        Sistema sistem=new Sistema();
        //sistem.ingresarPacienteXteclado();
       // sistem.ingresarPacienteXteclado();
        //sistem.MostrarArchivoPacientes();
        //sistem.cargarPacientes();
        //sistem.GenerarTurnoVacunacion();
        //sistem.GenerarTurnoVacunacion();
=======
    public static void main(String[] args) {

        Sistema sistem=new Sistema();
        sistem.ingresarPacienteXteclado();
        sistem.ingresarPacienteXteclado();
        sistem.MostrarArchivoPacientes();
        //sistem.cargarPacientes();
        //sistem.GenerarTurnoVacunacion();
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40

        //sistem.MostrarMapaTurnos();
        //sistem.VerificarTurnosDiadeHoy();
        //sistem.busquedaPacientexID(2);
<<<<<<< HEAD



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








=======
    }
>>>>>>> 0357fcf0ce648ce09f91edf45a7f0ea740320f40
}
