import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CanchaRepository mapCanchas = new CanchaRepository();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nBienvenido al crud sin ui.");
        while (true){
            System.out.println("""
                            1. Crear
                            2. Listar
                            3. Buscar por id
                            4. Actualizar
                            5. Eliminar
                            6. Salir
                            Seleccione una de las opciones dadas:""");
            int option = sc.nextInt();
            if (option < 1 || option > 6) {
                System.out.println("Escríba una opción válida\n \n");
                continue;
            }
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Escribe el nombre de la cancha a crear:");
                    String nombre = sc.nextLine();

                    TipoCancha tipo = null;
                    while (true) {
                        System.out.println("""
                                Selecciona el tipo de cancha:
                                1. Futbol 5.
                                2. Futbol 7.
                                3. Baloncesto.""");
                        int optionTipo = sc.nextInt();
                        if (optionTipo == 1) {tipo = TipoCancha.FUTBOL_5; break;}
                        else if (optionTipo == 2) {tipo = TipoCancha.FUTBOL_7; break;}
                        else if (optionTipo == 3) {tipo = TipoCancha.BALONCESTO; break;}
                        else System.out.println("No seleccionaste un tipo válido");
                    }

                    EstadoCancha estado = null;
                    while (true) {
                        System.out.println("""
                                Selecciona el estado de la cancha:
                                1. Activa.
                                2. Mantenimineto.""");
                        int optionEstado = sc.nextInt();
                        if (optionEstado == 1) {estado = EstadoCancha.ACTIVA; break;}
                        else if (optionEstado == 2) {estado = EstadoCancha.MANTENIMIENTO; break;}
                        else System.out.println("No seleccionaste un estado válida");
                    }
                    mapCanchas.crearCancha(nombre, tipo, estado);
                    System.out.printf("Se creo la cancha %s correctamente%n%n", nombre);
                    break;
                case 2:
                    List<Cancha> listaCanchas = mapCanchas.listarCanchas();
                    if (listaCanchas.isEmpty()) {System.out.println("No existen canchas en el momento\n________________"); break;}
                    System.out.println("Las canchas existentes son:\n________________");
                    for (Cancha c : listaCanchas) {
                        System.out.printf("""
                                        Nombre: %s
                                        Id: %s
                                        Tipo: %s
                                        Estado: %s
                                        ________________
                                        """,
                                c.getNombre(), c.getId(), c.getTipo(), c.getEstado());
                    }
                    break;
                case 3:
                    System.out.println("Ingresa el id de la cancha a buscar");
                    int ingresaId = sc.nextInt();
                    Cancha result = mapCanchas.obtenerCanchaPorId(ingresaId);
                    if (result == null) {System.out.printf("La cancha con id '%d' no existe.%n", ingresaId); break;}
                    else System.out.printf("""
                                        La cancha con id %d es:
                                        Nombre: %s
                                        Tipo: %s
                                        Estado: %s
                                        ________________
                                        """,
                            result.getId(), result.getNombre(), result.getTipo(), result.getEstado());
                    break;
                case 4:
                    System.out.println("Ingresa el id de la cancha a actualizar: ");
                    int actualizaId = sc.nextInt();
                    sc.nextLine();
                    Cancha actualizar = mapCanchas.obtenerCanchaPorId(actualizaId);
                    if (actualizar == null) {System.out.printf("La cancha con id '%d' no existe.%n", actualizaId); break;}
                    System.out.println("""
                            Introduce los datos a actualizar.
                            Para mantener el nombre actual, deja el campo vacío y pulsa Enter.
                            1. Escribe el nuevo nombre:
                            """);
                    String nombreActualizado = sc.nextLine();
                    if (nombreActualizado.isBlank()) nombreActualizado = null;

                    TipoCancha tipoActualizado = null;
                    while (true) {
                        System.out.println("""
                                Selecciona el nuevo tipo:
                                1. Futbol 5.
                                2. Futbol 7.
                                3. Baloncesto.
                                4. Dejar el valor actual.""");
                        int nuevoTipo = sc.nextInt();
                        if (nuevoTipo == 4) break;
                        else if (nuevoTipo == 1) {tipoActualizado = TipoCancha.FUTBOL_5; break;}
                        else if (nuevoTipo == 2) {tipoActualizado = TipoCancha.FUTBOL_7; break;}
                        else if (nuevoTipo == 3) {tipoActualizado = TipoCancha.BALONCESTO; break;}
                        else System.out.println("No seleccionaste un tipo válido");
                    }

                    EstadoCancha estadoActualizado = null;
                    while (true) {
                        System.out.println("""
                                Selecciona el estado de la cancha:
                                1. Activa.
                                2. Mantenimineto.
                                3. Mantener el valor actual.""");
                        int nuevoEstado = sc.nextInt();
                        if (nuevoEstado == 3) break;
                        else if (nuevoEstado == 1) {estadoActualizado = EstadoCancha.ACTIVA; break;}
                        else if (nuevoEstado == 2) {estadoActualizado = EstadoCancha.MANTENIMIENTO; break;}
                        else System.out.println("No seleccionaste un estado válida");
                    }

                    Cancha actualizado = mapCanchas.actualizarCancha(actualizaId, nombreActualizado, tipoActualizado,estadoActualizado);
                    System.out.printf("""
                                        La cancha con id %d ha sido actualizada:
                                        Nombre: %s
                                        Tipo: %s
                                        Estado: %s
                                        ________________
                                        """,
                            actualizado.getId(), actualizado.getNombre(), actualizado.getTipo(), actualizado.getEstado());
                    break;
                case 5:
                    System.out.println("Ingresa el id de la cancha a eliminar");
                    int eliminarId = sc.nextInt();
                    Cancha eliminar = mapCanchas.eliminarCanchaPorId(eliminarId);
                    if (eliminar == null) {System.out.printf("La cancha con id '%d' no existe.%n", eliminarId); break;}
                    else System.out.printf("""
                                        La cancha con id: %d
                                        Nombre: %s
                                        Tipo: %s
                                        Estado: %s
                                        Ha sido eliminada.
                                        ________________
                                        """,
                            eliminar.getId(), eliminar.getNombre(), eliminar.getTipo(), eliminar.getEstado());
                    break;
                case 6:
                    System.out.println("Gracias por usar, hasta pronto.");
                    return;
            }
        }
    }
}