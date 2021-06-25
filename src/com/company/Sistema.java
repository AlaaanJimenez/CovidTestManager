package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.print.DocFlavor;
import javax.swing.table.TableRowSorter;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jdk.swing.interop.SwingInterOpUtils;


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
    private String AISLADOS_PATH;
    private String SANOS_PATH;


    //Constructor
    public Sistema() {
        listaPacientes=new ArrayList<>();
        listaPersonal=new ArrayList<>();
        setPaths();
        setListaPacientes();
        setMapaTurnos();


    }


    /*
        Setea los paths de las carpetas donde se guardara el archivo de pacientes
        @return void
     */
    private void setPaths()    {
        this.USER_PATH=System.getProperty("user.dir");
        this.TURNOS_PATH="" + USER_PATH + "\\turnosVacunacion";
        this.AISLADOS_PATH="" + USER_PATH + "\\aislados";
        this.USUARIOS_PATH=""+USER_PATH+"\\usuarios";
        this.SANOS_PATH="" + USER_PATH + "\\sanos";


    }
    private void setListaPacientes(){
        this.listaPacientes=cargarPacientesDeArchivo();
    }
    private void setMapaTurnos(){

        ArrayList<MapaEntry>map=GenerarMapaDeArchivo();
        HashMap<Paciente,LocalDate>nuevomap=new HashMap<>();
        for (MapaEntry e:map)
        {
            nuevomap.put(e.getPaciente(),e.getDate());
        }
        this.MapaTurnos=nuevomap;
    }



    ///Validaciones ingreso x Teclado

    /*
Ingresa paciente x teclado y Añade al Arraylist de Pacientes
@return void
 */
    public void ingresarPacienteXteclado() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String nombre;
        int documento;
        char sexo;
        Barrios barrio;
        Paciente nuevo = new Paciente();

        boolean comp=false;
        while (comp==false) {
            nombre = seleccionarNombre();
            documento = seleccionarDni();
            sexo = seleccionarSexo();
            barrio = seleccionarBarrio();
            System.out.println(barrio);


            nuevo = new Paciente(documento, nombre, sexo, barrio);
            listaPacientes.add(nuevo);
            comp = AgregarPacienteAarchivo(nuevo);
        }
        System.out.println("Se creo y agrego a la liesta el siguiente paciente \n " + nuevo.toString());

    }
    
    /*
    Retorna un barrio y es utilizada en la carga del usuario
    Realiza Validaciones
    @return Barrio

     */
    private Barrios seleccionarBarrio(){
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
                System.out.println("\nERROR------------> No conocemos ningun sexo que se referencie con la letra " + let + "\nPor favor seleccione F o M para continuar\n");
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
               System.out.println("ERROR------------> El nombre solo puede contener letras de la a-z /A-Z \n Vuelva a intentarlo\n ");
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


///----------------------------------------------------------/// METODOS ///---------------------------------------------------------------------///


    /*
    Genera turno solicitando la fecha por teclado
    Realiza verificaciones (no fecha anterior a la actual / no mas de 30 dias)
    @return void
    @implements JodaTime
     */
    public void GenerarTurnoVacunacion() throws IOException {

        Scanner scan=new Scanner(System.in);
        boolean comp=false;
        int dia;
        int mes;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        LocalDate fechaActual=LocalDate.now();
        LocalDate fechaTurno=LocalDate.now();
        LocalDate fechaLimiteReserva=LocalDate.now();
        fechaLimiteReserva=fechaActual.plusDays(30);
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
        setMapaTurnos();
        MapaEntry a=new MapaEntry(nuevo,fechaTurno);
        boolean comp2=AgregarTurnoaArchivo(a);
        if (comp2==true)
        {
            System.out.println("Se cargo turno en el archivo");
        }


    }

    ///Mostrar Listas/Mapas
    /*
    Muestra por pantalla usuario y numero de turno
*/
    public void MostrarMapaTurnos(){
        int i=0;

        for(Paciente a:MapaTurnos.keySet())
            {
                i++;
                System.out.println("Turno N°"+i+"\nProgramado para el dia  "+MapaTurnos.get(a) + "\nAsignado al usuario:   " + a +"\n");
        }

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
///----------------------------------------------------------/// Metodos de Testing ///----------------------------------------------------------///

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
        //MapaTurnos.put(c,date4);
        MapaTurnos.put(d,date1);
        //MapaTurnos.put(b,date3);
    }

///----------------------------------------------------------/// Funciones Personal ///----------------------------------------------------------///
    /*
  Solicita al usuario seleccionar un id por teclado y busca en la lista al paciente
  @return Paciente
   */
    public Paciente busquedaPacientexID(){
        boolean comp=false;
        String cas;
        Scanner scan=new Scanner(System.in);
        Paciente nuevo=new Paciente();
        int num;

        while (comp !=true) {
            System.out.println("Por favor ingrese su numero de ID \n");
            num = scan.nextInt();

            System.out.println("El numerode ID que ingreso ( " + num + " )\nCorresponde al usuario con los siguientes datos: \n");
            for(Paciente e:listaPacientes)
            {
                if(e.getId()==num)
                {
                    nuevo=e;
                    System.out.println(nuevo.toString());
                }
                }
            if(nuevo.getBarrio()==null)
            {
                System.out.println("el usuario con el id ingresado no existe\n");
            }



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
    Recorre el mapa buscando fechas identicas a las del dia de la fecha
    @return void
     */
    public void VerificarTurnosDiadeHoy(){
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

    /*
  Pide id de usuario por teclado y realiza testeo
  El testeo retorna un random entre 42 y 36 grados
  Si el testeo da positivo crea un archivo en el PATH aislados con  la temperatura y el documento del paciente
  Si el testeo da negativo crea un archivo en el PATH sanos con  la temperatura y el documento del paciente
  @return void
 */
    public void realizarTesteo() throws IOException{
        boolean asd = false;
        int n = (int) (Math.random() * (42 - 36)) + 36;
        Scanner scan=new Scanner(System.in);
        LocalDate fechaActual=LocalDate.now();
        int i=0;
        boolean comp=false;
        System.out.println("ingrese el id del paciente a realizar el testeo");
        int id=scan.nextInt();

        Paciente s=new Paciente();
        for(Paciente a:MapaTurnos.keySet())
        {
            i++;
            if(MapaTurnos.get(a).compareTo(fechaActual)==0 && a.getId()==id)
            {
                comp=true;
                System.out.println("El paciente seleccionado tiene una fecha de testeo programada para hoy... Procedemos a realizar el testeo \n");
                s=a;
            }
        }
        if(comp==false)
        {
            System.out.println("\n El paciente con el id seleccionado no tiene un turno programado para hoy \n");
        }else {

            if (n > 38) {
                System.out.println("RESULTADO TESTEO: POSITIVO PARA COVID19 ------> TEMPERATURA:" + n);
                RegistroTesteo nuevo = new RegistroTesteo(s.getDni(), n);
                AgregarAislado(nuevo,s);
                EliminarTurnoArchivo(s.getDni());
            } else {
                System.out.println("RESULTADO TESTEO: NEGATIVO :) ------>TEMPERATURA: " + n);
                RegistroTesteo nuevo = new RegistroTesteo(s.getDni(), n);
                AgregarSanos(nuevo,s);
                EliminarTurnoArchivo(s.getDni());
            }


        }
    }


///----------------------------------------------------------/// MANEJO ARCHIVO PACIENTES///---------------------------------------------------///

    ///ARCHIVO PACIENTES
    /*
    Elimina paciente de archivo
    @return void
     */
    private void EliminarPacienteArchivo(Paciente user){
        ObjectMapper mapper=new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        File file=new File(USUARIOS_PATH);
        Paciente s=new Paciente();
        if(file.isDirectory())
        {
            File files[]=file.listFiles();
            for(int i=0;i<files.length;i++)
            {
                try {
                    s=mapper.readValue(files[i],Paciente.class);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (s.getDni()==user.getDni())
                {
                    files[i].delete();
                }
            }
        }

    }

    /*
    Agrega paciente a archivo
    @return void
     */
    public boolean AgregarPacienteAarchivo(Paciente paciente) throws IOException {

        File newPaciente=new File(USUARIOS_PATH+"\\"+paciente.getDni()+".json");
        ObjectMapper mapper=new ObjectMapper();
        boolean comp=false;
        boolean comp2=false;

        setListaPacientes();

            for (Paciente e : listaPacientes) {
                if (e.getDni() == paciente.getDni()) {
                    comp = true;
                }
            }
            if (comp == false) {

                mapper.writeValue(newPaciente, paciente);
                setListaPacientes();
                comp2=true;
                return true;
            } else {
                System.out.println("\n ERROR------------> El documento seleccionado ya existe en el archivo de Pacientes \nIntentelo de nuevo \n ");
                return false;
            }



    }

    /*
    Carga array local desde archivo de pacientes
    @return Arraylist<Paciente>
     */
    public ArrayList<Paciente> cargarPacientesDeArchivo(){
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
                    p.setId();
                    lista.add(p);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return lista;
    }

    /*

     */
    public void MostrarArchivoPacientes()    {
        setListaPacientes();
        for (Paciente e:listaPacientes)
        {
            System.out.println(e.toString());
        }
    }


///----------------------------------------------------------///ARCHIVO AISLADOS Y SANOS///----------------------------------------------------///
    /*
    Se llama luego de realizar un testeo
    Agrega un registro con la temperatura y documento del usuario al archivo de Aislados
    Elimina al usuario del Archivo de pacientes activos
     */
    private void AgregarAislado(RegistroTesteo e,Paciente s) throws IOException {
        File newAislado=new File(AISLADOS_PATH+"\\"+e.getDni()+".json");
        ObjectMapper mapper=new ObjectMapper();

        mapper.writeValue(newAislado, e);
        EliminarPacienteArchivo(s);
        setListaPacientes();
    }

    /*
    Se llama luego de realizar un testeo
    Agrega un registro con la temperatura y documento del usuario al archivo de Sanos
    Elimina al usuario del Archivo de pacientes activos
    @return void
     */
    private void AgregarSanos(RegistroTesteo e,Paciente s) throws IOException {
        File newSanos=new File(SANOS_PATH+"\\"+e.getDni()+".json");
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(newSanos, e);
        EliminarPacienteArchivo(s);
        setListaPacientes();
    }

    /*
    Recorre Archivo de aislados y lo muestra por pantalla
    @return void
     */
    private void MostrarArchivoAislados() {
        RegistroTesteo reg = new RegistroTesteo();
        File newAislado = new File(AISLADOS_PATH);
        ObjectMapper mapper = new ObjectMapper();

        if (newAislado.isDirectory()) {
            File files[] = newAislado.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    reg = mapper.readValue(files[i], RegistroTesteo.class);
                    System.out.println("Registro Aislado N°" + i + reg.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    Recorre Archivo de Sanos y lo muestra por pantalla
    @return void
     */
    private void MostrarArchivoSanos() {
        RegistroTesteo reg = new RegistroTesteo();
        File newAislado = new File(SANOS_PATH);
        ObjectMapper mapper = new ObjectMapper();

        if (newAislado.isDirectory()) {
            File files[] = newAislado.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    reg = mapper.readValue(files[i], RegistroTesteo.class);
                    System.out.println("Registro Sanos N°" + i + reg.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



///----------------------------------------------------------/// ARCHIVO TURNOS ///------------------------------------------------------------///
    /*
    Guarda en Archivo un json de Mapa entry
    Utiliza LocalDate en archivo por lo que se creo un serializador y deserializador especial
    @return ArrayList<MapaEntry>
     */
    public ArrayList<MapaEntry> GenerarMapaDeArchivo(){
        MapaEntry nueva=new MapaEntry();

        ObjectMapper mapper=new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        ArrayList<MapaEntry>lista=new ArrayList<>();
        File file=new File(TURNOS_PATH);

        if(file.isDirectory())
        {
            File files[]=file.listFiles();
            for(int i=0;i<files.length;i++)
            {
                try {
                    String json;
                    json=mapper.readValue(files[i],String.class);
                    nueva=mapper.readValue(json,MapaEntry.class);
                    lista.add(nueva);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return lista;

    }

    /*
    Recorre el mapa turnos si encuentra un turno con la misma key no permite continuar
    Agrega Paciente y fecha al archivo de turnos
    @return boolean

     */
    private boolean AgregarTurnoaArchivo(MapaEntry p) throws IOException {

        boolean comp=false;
        boolean comp2=false;
        int i = MapaTurnos.size() + 1;

        File nuevoTurno = new File(TURNOS_PATH + "\\TurnoN°" + i + ".json");
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


        setMapaTurnos();

        for (Paciente e : MapaTurnos.keySet())
        {
            if (e.getDni() == p.getPaciente().getDni()) {
                comp = true;
            }
        }
        if (comp == false) {

            String json=mapper.writeValueAsString(p);
            mapper.writeValue(nuevoTurno,json);
            setMapaTurnos();
            comp2=true;
            return true;
        } else {
            System.out.println("\n ERROR------------> El documento seleccionado ya existe en el archivo de Turnos \nIntentelo de nuevo \n ");
            return false;
        }


    }

    /*
    Recibe un paciente, reacorre el archivo de turnos si encunetra un turno con el mismo id que el paciente que llega por parametro lo elimina
    @return void
     */
    private void EliminarTurnoArchivo(int documento){
        ObjectMapper mapper=new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        File file=new File(TURNOS_PATH);
        MapaEntry t=new MapaEntry();
        if(file.isDirectory())
        {
            File files[]=file.listFiles();
            for(int i=0;i<files.length;i++)
            {
                try {
                    t=mapper.readValue(files[i],MapaEntry.class);
                } catch (IOException p)
                {
                    p.printStackTrace();
                }
                if (documento==t.getPaciente().getDni())
                {
                    files[i].delete();
                }
            }
        }
    }

    public void MostrarArchivoTurnos(){
        int i=0;
        setListaPacientes();
        for (Paciente e:MapaTurnos.keySet())
        {
            System.out.println("Turno N°"+i+"\nPaciente: "+e.toString()+"\nFecha de turno"+MapaTurnos.get(e).toString());
        }
    }



}
