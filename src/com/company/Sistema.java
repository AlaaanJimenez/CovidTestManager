package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.table.TableRowSorter;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Sistema {

    private static int año=2021;

    private LocalDate fechaActual=LocalDate.now();
    private ArrayList<Personal> listaPersonal;
    private List<Paciente> listaPacientes;
    private HashMap<Paciente,LocalDate>MapaTurnos=new HashMap<>();

    ///MANEJO ARCHIVOS

    private String USER_PATH;
    private String TURNOS_PATH;
    private String USUARIOS_PATH;




    //Constructor
    public Sistema() {
        listaPacientes=new ArrayList<>();
        listaPersonal=new ArrayList<>();
        setPaths();
        setListaPacientes();


    }



    /*
Muestra Enum Barrios
@return void/String
 */
    public void mostrarBarrios() {
        Barrios[] Enums = Barrios.values();
        for (int i = 0; i < Barrios.values().length; i++) {
            System.out.println(i + ")" + Enums[i].toString() + "");
        }

    }

    ///Validaciones ingreso x Teclado

    /*
Ingresa paciente x teclado y Añade al Arraylist de Pacientes
@return void
 */
    public void ingresarPacienteXteclado() {
        Scanner scanner = new Scanner(System.in);
        String nombre;
        int documento;
        char sexo;
        Barrios barrio;

        nombre=seleccionarNombre();
        documento = seleccionarDni();
        sexo = seleccionarSexo();
        barrio = seleccionarBarrio();
        System.out.println(barrio);


        Paciente nuevo = new Paciente(documento, nombre, sexo, barrio);
        listaPacientes.add(nuevo);

        System.out.println("Se creo y agrego a la liesta el siguiente paciente \n " + nuevo.toString());
        AgregarPacienteAarchivo(nuevo);
    }
    
    /*
    Retorna un barrio y es utilizada en la carga del usuario
    Realiza Validaciones
    @return Barrio

     */
    private Barrios seleccionarBarrio()    {
        Barrios[] Enums = Barrios.values();
        Barrios s = null;
        Scanner scann = new Scanner(System.in);
        boolean valid=false;
        
        while (valid==false)
        {
        System.out.println("\nSeleccione el numero asignado al barrio del paciente");
        mostrarBarrios();
        int selec = scann.nextInt();
        
        if(selec<0 || selec>Barrios.values().length )
        {
            System.out.println("Por favor seleccione uno de los numeros Mostrados en pantalla\n");
        }
        else
        {
            s = Enums[selec];
            valid=true;
        }
        
        }
        
        
        return s;

    }

    /*
    Se utiliza en el ingreso de usuario x teclado.
    Realiza Validaciones (solo acepta letra F o M)
    @return char
     */
    private char seleccionarSexo() {
        char let = 's';
        Scanner scanner = new Scanner(System.in);
        String sexo;
        boolean confirm = false;
        while (confirm == false) {
            System.out.println("\n Ingrese Sexo  F / M \n");
            sexo = scanner.nextLine();
            sexo = sexo.toUpperCase();
            let = sexo.charAt(0);

            if (let == 'M' || let == 'F') {
                confirm = true;

            } else {
                System.out.println("\n No conocemos ningun sexo que se referencie con la letra " + let + "\n Por favor seleccione F o M para continuar\n");
            }
        }
        return let;
    }

    /*
        Se utiliza en el ingreso de usuario x teclado.
        Realiza Validaciones (no numeros)
        @return String
    */
    public String seleccionarNombre() {
        boolean comp = false;
        String nombre= "asd";
        Scanner scan = new Scanner(System.in);

        while (comp==false) {
            System.out.println("Ingrese nombre");
            nombre = scan.nextLine();

           if (nombre.matches("[a-zA-Z]*")) {
               System.out.println("Nombre valido \n");
               comp=true;
           } else {
               System.out.println("El nombre solo puede contener letras de la a-z /A-Z \n Vuelva a intentarlo\n ");
           }
       }
       return nombre;

    }
    
    /*
            Se utiliza en el ingreso de usuario x teclado.
            Realiza Validaciones (no mayor ni menor a 8 numeros / no letras)
            @return String
        */
    public int seleccionarDni()
    {
        Scanner scan=new Scanner(System.in);
        String documento = null;
        boolean verif=false;

        while (verif==false)
        {
            System.out.println("Ingrese documento\n");
            documento=scan.nextLine();

            if(documento.matches("[0-9]*"))
            {
                if(documento.length()<8 || documento.length()>8)
                {
                    System.out.println("El numero de documento debe contener 8 digitos------> Intentelo de nuevo");
                }
                else
                {
                    System.out.println("Documento Valido");
                    verif=true;
                }
            }
            else
            {
                System.out.println("El documento solo puede poseer numeros---> Intentelo de nuevo \n");
            }


        }

        int doc=Integer.parseInt(documento);
        System.out.println(doc);
        return doc;
    }





    ///METODOS


    /*
  Solicita al usuario seleccionar un id por teclado y busca en la lista al paciente
  @return Paciente
   */
    public Paciente busquedaPacientexID()
    {
        boolean comp=false;
        String cas;
        Scanner scan=new Scanner(System.in);
        Paciente nuevo=new Paciente();
        int num;

        while (comp !=true) {
            System.out.println("Por favor ingrese su numero de ID \n");
            num = scan.nextInt();

            System.out.println("El numerode ID que ingreso ( " + num + " )\nCorresponde al usuario con los siguientes datos: \n");
            nuevo = listaPacientes.get(num);
            System.out.println(nuevo.toString());

            System.out.println("Desea realizar un turno para el usuario con los Datos mostrados en pantalla ?  S/N");
            cas=scan.next();

            if(cas.charAt(0) == 's'||cas.charAt(0) =='S' )
            {
                comp=true;
            }
            else
            {
                comp=false;
            }
        }

        return nuevo;
    }


    /*
    Genera turno solicitando la fecha por teclado
    Realiza verificaciones (no fecha anterior a la actual / no mas de 30 dias)
    @return void
    @implements JodaTime
     */
    public void GenerarTurnoVacunacion()
    {

        Scanner scan=new Scanner(System.in);
        boolean comp=false;
        int dia;
        int mes;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        LocalDate fechaActual=LocalDate.now();
        LocalDate fechaTurno=LocalDate.now();
        LocalDate fechaLimiteReserva=LocalDate.now();
        fechaLimiteReserva=fechaActual.plusDays(30);

        System.out.println(fechaActual);
        System.out.println(fechaLimiteReserva);



        Paciente nuevo=busquedaPacientexID();


        while (comp!=true) {
            System.out.println("\nRecuerde que la fecha debe ser dentro de los 30 dias a partir de hoy " +fechaActual + "\n Ingrese Dia :");
            dia = scan.nextInt();
            System.out.println("ingrese numero de Mes \n");
            mes = scan.nextInt();

            fechaTurno=LocalDate.of(año,mes,dia);
            System.out.println(fechaTurno);


            if (fechaTurno.compareTo(fechaLimiteReserva) > 0) {
                System.out.println("\nERROR-----> No esta permitido programar un turno para mas de 30 dias, por favor intentelo de nuevo \n");
            }
            else if(fechaTurno.compareTo(fechaActual)<0)
            {
                System.out.println("\nNo puede realizar una reserva para una fecha del pasado :( \n");
            }
            else {
                comp = true;
            }
        }

        System.out.println("Se programo su turno de vacunacion para el dia "+fechaTurno.getDayOfMonth()+"/"+fechaTurno.getMonth()+"/"+fechaTurno.getYear()+"\n");
        System.out.println("Fecha programada para el paciente: "+nuevo.toString());
        MapaTurnos.put(nuevo,fechaTurno);

    }

    ///Mostrar Listas/Mapas

    public void MostrarMapaTurnos()
    {
        int i=0;

        for(Paciente a:MapaTurnos.keySet())
            {
                i++;
                System.out.println("Turno N°"+i+"\nProgramado para el dia  "+MapaTurnos.get(a) + "\nAsignado al usuario:   " + a +"\n");
        }

    }




///Metodos manejo interno

    public void cargarPacientes() {
        Paciente a = new Paciente(41079103, "alan", 'm', Barrios.Constitucion);
        Paciente b = new Paciente(41546503, "Marta", 'F', Barrios.DonBosco);
        Paciente c = new Paciente(36342303, "Juan", 'M', Barrios.Luro);
        Paciente d = new Paciente(34531103, "Sofia", 'f', Barrios.Constitucion);
        LocalDate date1 = LocalDate.of(año, 6, 17);
        LocalDate date2 = LocalDate.of(año, 6, 19);
        LocalDate date3 = LocalDate.of(año, 6, 22);
        LocalDate date4 = LocalDate.of(año, 6, 24);
        LocalDate date5 = LocalDate.of(año, 6, 17);

        listaPacientes.add(a);
        listaPacientes.add(b);
        listaPacientes.add(c);
        listaPacientes.add(d);


        MapaTurnos.put(a,date1);
        MapaTurnos.put(b,date3);
        MapaTurnos.put(c,date4);
        MapaTurnos.put(d,date1);
        //MapaTurnos.put(b,date3);
    }

///Funciones Personal


    /*
    Recorre el mapa buscando fechas identicas a las del dia de la fecha
    @return void
     */
    public void VerificarTurnosDiadeHoy()
    {
        int d =0;
        int i=0;
        StringBuilder sb =new StringBuilder();
        LocalDate fechaActual=LocalDate.now();


        for(Paciente a:MapaTurnos.keySet())
        {
            i++;
            if(MapaTurnos.get(a).compareTo(fechaActual)==0) {
                d++;

                System.out.println("Turno N°" + i + "\nProgramado para el dia  " + MapaTurnos.get(a) + "\nAsignado al usuario:   " + a + "\n");
            }
        }
        if(d==0)
        {
            System.out.println("\n No hay turnos programados para hoy-----> DIA LIBRE \n");
        }
    }


///MANEJO ARCHIVOS

    private void setPaths()    {
        this.USER_PATH=System.getProperty("user.dir");
        this.TURNOS_PATH="" + USER_PATH + "\\mapaTurnosVacunacion";
        //this.AISLADOS_PATH="" + USER_PATH + "\\aislados";
        this.USUARIOS_PATH=""+USER_PATH+"\\usuarios";

    }
    private void setListaPacientes(){
        this.listaPacientes=cargarPacientesDeArchivo();
    }


    public void AgregarPacienteAarchivo(Paciente paciente)
    {

        File newPaciente=new File(USUARIOS_PATH+"\\"+paciente.getDni()+".json");
        ObjectMapper mapper=new ObjectMapper();
        boolean comp=false;
        setListaPacientes();

        for (Paciente e:listaPacientes)
        {
            if(e.getDni()==paciente.getDni())
            {
                comp=true;
            }
        }
        if (comp==false)
        {
            try {
                mapper.writeValue(newPaciente,paciente);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            setListaPacientes();
        }
        else {
            System.out.println("El documento seleccionado ya existe en el archivo de Pacientes");
        }



    }

    public ArrayList<Paciente> cargarPacientesDeArchivo()
    {
        ObjectMapper mapper=new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        ArrayList<Paciente>lista=new ArrayList<>();
        File file=new File(USUARIOS_PATH);

        if(file.isDirectory())
        {
            File files[]=file.listFiles();
            for(int i=0;i<files.length;i++)
            {
                try {
                    Paciente p=mapper.readValue(files[i],Paciente.class);
                    lista.add(p);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return lista;
    }

    public void AgregarMapaArchivo()
    {


    }
    public void MostrarArchivoPacientes()
    {
        setListaPacientes();
        for (Paciente e:listaPacientes)
        {
            System.out.println(e.toString());
        }
    }
}
