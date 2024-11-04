package edu.badpals.paises;

import java.net.Inet4Address;
import java.sql.*;
import java.util.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/paises";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static Connection connection = null;
    private static Statement statement = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salida = false;

        System.out.println("¡Bienvenido al registro de países!\n");
        mostrarMenu();

        while(!salida){
            System.out.println("\nSeleccione una opción (0 para menú): ");
            String respuesta = sc.nextLine().toLowerCase();

            switch (respuesta) {
                case "0":
                case "menu":
                case "menú":
                case "mostrar menu":
                case "mostrar menú":
                    mostrarMenu();
                    break;
                case "1":
                case "mostrar paises":
                case "mostrar países":
                    mostrarPaises(sc);
                    break;
                case "2":
                case "crear pais":
                case "crear país":
                    crearPais(sc);
                    break;
                case "3":
                case "actualizar pais":
                case "actualizar país":
                    actualizarPais(sc);
                    break;
                case "4":
                case "eliminar pais":
                case "eliminar país":
                    eliminarPais(sc);
                    break;
                case "5":
                case "salir":
                case "exit":
                    salida = true;
                    System.out.println("Saliendo del programa. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no encontrada. Introduzca una de las opciones disponibles: ");
                    mostrarMenu();
            }

        }

    }
    private static void mostrarMenu(){
        StringBuilder sb = new StringBuilder();
        sb.append("==== MENÚ DE OPCIONES ====\n")
                .append("0. Mostrar Menú\n")
                .append("1. Mostrar países\n")
                .append("2. Crear país\n")
                .append("3. Actualizar país\n")
                .append("4. Eliminar país\n")
                .append("5. Salir/Exit\n");

        System.out.println(sb);
    }

    private static void mostrarPaises(Scanner sc){
        menuMostrar();
        boolean salida = false;

        while(!salida){
            System.out.println("Seleccione una opción (0 para menú): ");
            String respuesta = sc.nextLine().toLowerCase();
            switch(respuesta){
                case "0":
                case "menu":
                    menuMostrar();
                    break;
                case "1":
                case "mostrar todos los paises":
                case "mostrar todos los países":
                    List<Pais> paises = getPaises();
                    paises.stream().sorted(Comparator.comparing(Pais::getNombre)).forEach(pais -> System.out.println(pais));
                    break;
                case "2":
                case "mostrar pais":
                case "mostrar un pais":
                case "mostrar un país":
                    String nombre = pedirCadena("Introduzca el nombre del país a buscar",sc);
                    Pais pais = getPais(nombre);
                    if(pais != null){
                        System.out.println(pais);
                    }else{
                        System.out.println("El país de nombre: " + nombre + " no existe.");
                    }
                    break;
                case "3":
                case "salir":
                case "exit":
                    salida = true;
                    System.out.println("Saliendo de la sección Mostrar países.");
                    break;
                default:
                    System.out.println("Introduzca una de las opciones disponibles (0-3)");
                    mostrarMenu();
                    break;
            }
        }


    }

    private static void menuMostrar(){
        StringBuilder sb = new StringBuilder();
        sb.append("==== MOSTRAR PAISES ====\n")
                .append("0. Menú\n")
                .append("1. Mostrar todos los países\n")
                .append("2. Mostrar un país\n")
                .append("3. Salir/Exit\n");

        System.out.println(sb);
    }

    private static void crearPais(Scanner sc){
        System.out.println("==== CREAR NUEVO PAÍS ====\n");

        String nombre = pedirCadena("Introduzca el nombre del país: ", sc);
        if(getPais(nombre) != null){
            System.out.println("El nombre del país ya existe, no es posible crear un nuevo país con el nombre: " + nombre);
            return;
        }else{
            Integer num_habitantes = pedirHabitantes(sc);
            if (num_habitantes == null) return;
            String capital = pedirCadena("Introduzca nombre de la capital: ",sc);
            String moneda = pedirCadena("Introduzca nombre de la moneda del país: ",sc);

            System.out.println("\nLos datos recogidos son los siguientes:");
            System.out.println("Nombre de país: " + nombre + ", \nnúmero de habitantes: " + num_habitantes + ", \ncapital: " +capital+ ", \nmoneda: " + moneda);
            System.out.println("\nSi es correcto y desea continuar pulse 's'. Si desea abortar creación pulse cualquier otra tecla.");
            String respuesta = sc.nextLine().toLowerCase();
            if(respuesta.equals("s")){
                Pais pais = new Pais(nombre,num_habitantes,capital,moneda);
                insertPais(pais);
            }else{
                System.out.println("Creación abortada.\n");
            }
        }
    }

    private static void actualizarPais(Scanner sc){
        System.out.println("==== ACTUALIZAR PAÍS ====\n");

        String nombre = pedirCadena("Introduzca el nombre del país a actualizar",sc);
        Pais pais = getPais(nombre);
        if(pais == null){
            System.out.println("El país de nombre: "+nombre+" no existe. No es posible actualizarlo");
        }else{
            Integer nuevo_num_habitantes = pedirHabitantes(sc);
            if(nuevo_num_habitantes == null) return;
            String nuevaCapital = pedirCadena("Introduzca el nombre de la capital: ",sc);
            String nuevaMoneda = pedirCadena("Introduzca el nombre de la moneda: ",sc);

            System.out.println("\nLos datos recogidos son los siguientes:");
            System.out.println("Nombre de país: " + nombre + ", \nnúmero de habitantes: " + nuevo_num_habitantes + ", \ncapital: " +nuevaCapital+ ", \nmoneda: " + nuevaMoneda);
            System.out.println("\nSi es correcto y desea continuar pulse 's'. Si desea abortar la actualización pulse cualquier otra tecla.");
            String respuesta = sc.nextLine().toLowerCase();
            if(respuesta.equals("s")){
                pais.setNum_habitantes(nuevo_num_habitantes);
                pais.setCapital(nuevaCapital);
                pais.setMoneda(nuevaMoneda);
                updatePais(pais);
            }else{
                System.out.println("Actualización abortada.\n");
            }
        }
    }

    private static void eliminarPais(Scanner sc){
        System.out.println("==== ELIMINAR PAÍS ====\n");
        String nombre = pedirCadena("Introduzca el nombre del país a eliminar",sc);
        Pais pais = getPais(nombre);
        if(pais == null){
            System.out.println("El país de nombre: "+nombre+" no existe. No es posible eliminarlo.");
        }else{
            System.out.println("El país de nombre: "+nombre+ " va a ser eliminado. Si desea continuar pulse 's', sino pulse cualquier otra tecla.");
            String respuesta = sc.nextLine().toLowerCase();
            if(respuesta.equals("s")){
                deletePais(pais);
            }else{
                System.out.println("Eliminación abortada.\n");
            }
        }
    }


    private static String pedirCadena(String mensaje, Scanner sc) {
        System.out.println(mensaje);
        return sc.nextLine();
    }

    private static Integer pedirHabitantes(Scanner sc){
        boolean valido = false;
        int intentos= 0;
        Integer num_habitantes = null;

        while(!valido){
            System.out.println("Introduzca el número de habitantes del país: ");
            try{
                num_habitantes = sc.nextInt();
                sc.nextLine();
                valido = true;
            }catch (InputMismatchException e){
                System.out.println("Número no válido. Recuerde introducir un número entero.");
                intentos++;
                sc.nextLine();
                if(intentos == 3){
                    System.out.println("Número máximo de intentos alcanzado. Proceso abortado.");
                    return null;
                }
            }
        }
        return num_habitantes;
    }

    private static Pais getPais(String nombre){
        conectarBD();
        try{
            String selectPais = "select * from paises where nombre = ?";
            PreparedStatement ps = connection.prepareStatement(selectPais);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String nombrePais = rs.getString("nombre");
                int num_habitantes = rs.getInt("num_habitantes");
                String capital = rs.getString("capital");
                String moneda = rs.getString("moneda");
                Pais pais = new Pais(nombrePais,num_habitantes,capital,moneda);
                return pais;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar datos de país.");
            e.printStackTrace();
        }finally {
            desconectarBD();
        }
        return null;
    }

    private static void deletePais(Pais pais){
        conectarBD();
        try{
            String delete = "delete from paises where nombre = ?";
            PreparedStatement ps = connection.prepareStatement(delete);
            ps.setString(1,pais.getNombre());

            int filasBorradas = ps.executeUpdate();
            if(filasBorradas == 1){
                System.out.println("El país ha sido eliminado.");
            } else {
                System.out.println("No se encontró el país con el nombre especificado.");
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar datos de país.");
            e.printStackTrace();
        }finally {
            desconectarBD();
        }
    }

    private static void updatePais(Pais pais){
        conectarBD();
        try{
            String update = "update paises set  NUM_HABITANTES = ?, CAPITAL = ?, moneda = ? where nombre = ?";
            PreparedStatement ps = connection.prepareStatement(update);
            ps.setInt(1,pais.getNum_habitantes());
            ps.setString(2,pais.getCapital());
            ps.setString(3,pais.getMoneda());
            ps.setString(4,pais.getNombre());

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas == 1) {
                System.out.println("El país ha sido actualizado.");
            } else {
                System.out.println("No se encontró el país con el nombre especificado.");
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar datos de país.");
            e.printStackTrace();
        }finally {
            desconectarBD();
        }
    }

    private static void insertPais(Pais pais){
        conectarBD();
        try{
            String insert = "insert into paises (nombre,num_habitantes,capital,moneda) values (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setString(1,pais.getNombre());
            ps.setInt(2,pais.getNum_habitantes());
            ps.setString(3,pais.getCapital());
            ps.setString(4,pais.getMoneda());

            ps.executeUpdate();
            System.out.println("Se ha guardado el nuevo país "+ pais.getNombre()+ " en la BD.");
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar nuevo país.");
            e.printStackTrace();
        }finally {
            desconectarBD();
        }
    }

    private static List<Pais> getPaises(){
        List<Pais> paises = new ArrayList<>();
        conectarBD();
        try(ResultSet rs = statement.executeQuery("select * from paises")){
            while(rs.next()){
                String nombre = rs.getString("nombre");
                int num_habitantes = rs.getInt("num_habitantes");
                String capital = rs.getString("capital");
                String moneda = rs.getString("moneda");
                Pais pais = new Pais(nombre,num_habitantes,capital,moneda);
                paises.add(pais);
            }
            return paises;
        } catch (SQLException e) {
            System.out.println("Error al leer los datos de países.");
            e.printStackTrace();
        }finally {
            desconectarBD();
        }
        return null;

    }

    private static void conectarBD(){
        try{
            if(connection == null){
                connection = DriverManager.getConnection(URL,USER,PASS);
            }
            if(statement == null){
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar la BD");
            e.printStackTrace();
        }
    }

    private static void desconectarBD(){
        if(statement != null){
            try{
                statement.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar statement");
                e.printStackTrace();
            }finally{
                statement = null;
            }
        }
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar connection");
                e.printStackTrace();
            }finally{
                connection = null;
            }
        }
    }
}