import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ConexionSQLite conexionSQLite = new ConexionSQLite("db/reservas.db");
        SchemaCreate schema = new SchemaCreate(conexionSQLite);
        schema.crearTablas();

        CanchaRepository repoCanchas = new CanchaRepository(conexionSQLite);
        ReservaRepository mapReservas = new ReservaRepository(repoCanchas, conexionSQLite);
        Scanner sc = new Scanner(System.in);
        System.out.println("\nBienvenido al programa de gestión de resevas");


        while (true) {
            System.out.println("""
                    Seleccione una opción:
                    1. Gestionar Canchas.
                    2. Gestionar Reservas.
                    3. Salir""");
            int a = sc.nextInt();
            sc.nextLine();
            switch (a) {
                case 1:
                    menuCanchas:
                    while (true) {
                        System.out.println("""
                                -----------------------------------
                                1. Crear
                                2. Listar
                                3. Buscar por id
                                4. Actualizar
                                5. Eliminar
                                6. Volver al menú principal.
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
                                    if (optionTipo == 1) {
                                        tipo = TipoCancha.FUTBOL_5;
                                        break;
                                    } else if (optionTipo == 2) {
                                        tipo = TipoCancha.FUTBOL_7;
                                        break;
                                    } else if (optionTipo == 3) {
                                        tipo = TipoCancha.BALONCESTO;
                                        break;
                                    } else System.out.println("No seleccionaste un tipo válido");
                                }

                                EstadoCancha estado = null;
                                while (true) {
                                    System.out.println("""
                                            Selecciona el estado de la cancha:
                                            1. Activa.
                                            2. Mantenimineto.""");
                                    int optionEstado = sc.nextInt();
                                    if (optionEstado == 1) {
                                        estado = EstadoCancha.ACTIVA;
                                        break;
                                    } else if (optionEstado == 2) {
                                        estado = EstadoCancha.MANTENIMIENTO;
                                        break;
                                    } else System.out.println("No seleccionaste un estado válida");
                                }
                                try {
                                    repoCanchas.crearCancha(nombre, tipo, estado);
                                    System.out.printf("Se creo la cancha %s correctamente%n%n", nombre);
                                } catch (RuntimeException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }

                                break;
                            case 2:
                                try {
                                    List<Cancha> listaCanchas = repoCanchas.listarCanchas();
                                    if (listaCanchas.isEmpty()) {
                                        System.out.println("No existen canchas en el momento\n________________");
                                        break;
                                    }
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
                                } catch (RuntimeException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("Ingresa el id de la cancha a buscar");
                                int ingresaId = sc.nextInt();
                                try {
                                    Cancha result = repoCanchas.obtenerCanchaPorId(ingresaId);
                                    System.out.printf("""
                                                    La cancha con id %d es:
                                                    Nombre: %s
                                                    Tipo: %s
                                                    Estado: %s
                                                    ________________
                                                    """,
                                            result.getId(), result.getNombre(), result.getTipo(), result.getEstado());
                                } catch (RecursoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            case 4:
                                System.out.println("Ingresa el id de la cancha a actualizar: ");
                                int actualizaId = sc.nextInt();
                                sc.nextLine();
                                try {
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
                                        else if (nuevoTipo == 1) {
                                            tipoActualizado = TipoCancha.FUTBOL_5;
                                            break;
                                        } else if (nuevoTipo == 2) {
                                            tipoActualizado = TipoCancha.FUTBOL_7;
                                            break;
                                        } else if (nuevoTipo == 3) {
                                            tipoActualizado = TipoCancha.BALONCESTO;
                                            break;
                                        } else System.out.println("No seleccionaste un tipo válido");
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
                                        else if (nuevoEstado == 1) {
                                            estadoActualizado = EstadoCancha.ACTIVA;
                                            break;
                                        } else if (nuevoEstado == 2) {
                                            estadoActualizado = EstadoCancha.MANTENIMIENTO;
                                            break;
                                        } else System.out.println("No seleccionaste un estado válida");
                                    }

                                    Cancha actualizado = repoCanchas.actualizarCancha(actualizaId, nombreActualizado, tipoActualizado, estadoActualizado);
                                    System.out.printf("""
                                                    La cancha con id %d ha sido actualizada:
                                                    Nombre: %s
                                                    Tipo: %s
                                                    Estado: %s
                                                    ________________
                                                    """,
                                            actualizado.getId(), actualizado.getNombre(), actualizado.getTipo(), actualizado.getEstado());
                                } catch (RuntimeException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 5:
                                System.out.println("Ingresa el id de la cancha a eliminar");
                                int eliminarId = sc.nextInt();
                                try {
                                    Cancha eliminar = repoCanchas.eliminarCanchaPorId(eliminarId);
                                    System.out.printf("""
                                                    La cancha con id: %d
                                                    Nombre: %s
                                                    Tipo: %s
                                                    Estado: %s
                                                    Ha sido eliminada.
                                                    ________________
                                                    """,
                                            eliminar.getId(), eliminar.getNombre(), eliminar.getTipo(), eliminar.getEstado());
                                } catch (RuntimeException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 6:
                                System.out.println("---------------------------");
                                break menuCanchas;
                        }
                    }
                    break;
                case 2:
                    menuReservas:
                    while (true) {
                        System.out.println("""
                                Seleccione una opción:
                                1. Crear Reserva.
                                2. Listar Reservas.
                                3. Buscar Reserva por ID.
                                4. Cancelar Reserva.
                                5. Actualizar Reserva.
                                6. Volver al menú principal.""");
                        int b = sc.nextInt();
                        sc.nextLine();
                        switch (b) {
                            case 1:
                                System.out.println("Ingrese los datos de la reserva");
                                List<Cancha> listaCanchas = repoCanchas.listarCanchas();
                                if (listaCanchas.isEmpty()) {
                                    System.out.println("No existen canchas en el momento\n________________");
                                    break;
                                }
                                List<Cancha> listaActivas = new ArrayList<>();
                                for (Cancha c : listaCanchas) {
                                    if (c.getEstado() == EstadoCancha.ACTIVA) {
                                        listaActivas.add(c);
                                    }
                                }
                                if (listaActivas.isEmpty()) {
                                    System.out.println("No existen canchas ACTIVAS en el momento\n________________");
                                    break;
                                }
                                System.out.println("Las canchas existentes son:\n________________");
                                for (Cancha activa : listaActivas) {
                                    System.out.printf("""
                                                    Nombre: %s
                                                    Id: %s
                                                    Tipo: %s
                                                    Estado: %s
                                                    ________________
                                                    """,
                                            activa.getNombre(), activa.getId(), activa.getTipo(), activa.getEstado());
                                }
                                System.out.println("Ingrese el ID de la cancha a reservar");
                                int idCancha = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Ingrese la fecha de la reserva");
                                LocalDate fecha = LocalDate.parse(sc.nextLine());
                                System.out.println("Ingrese la hora de la reserva");
                                LocalTime hora = LocalTime.parse(sc.nextLine());
                                System.out.println("Ingrese el nombre del estudiante");
                                String nombreEstudiante = sc.nextLine();
                                System.out.println("Ingrese el email del estudiante");
                                String emailEstudiante = sc.nextLine();
                                try {
                                    mapReservas.crearReserva(idCancha, fecha, hora, nombreEstudiante, emailEstudiante);
                                } catch (RecursoNoEncontradoException | ReglaNegocioException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            case 2:
                                List<Reserva> listaReservas = mapReservas.listarReservas();
                                if (listaReservas.isEmpty()) {
                                    System.out.println("No existen reservas en el momento\n________________");
                                    break;
                                }
                                System.out.println("Las reservas existentes son:\n________________");
                                for (Reserva r : listaReservas) {
                                    System.out.printf("""
                                                    Nombre del Estudiante: %s
                                                    Email del Estudiante: %s
                                                    Id de la Reserva: %s
                                                    Id de la Cancha: %s
                                                    Fecha: %s
                                                    Hora: %s
                                                    Estado: %s
                                                    ________________
                                                    """,
                                            r.getNombreEstudiante(), r.getEmailEstudiante(),
                                            r.getId(), r.getCanchaId(), r.getFecha(), r.getHoraInicio(), r.getEstado());
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese el ID de la reserva a buscar");
                                int idReserva = sc.nextInt();
                                sc.nextLine();
                                try {
                                    Reserva r = mapReservas.obtenerReservaPorId(idReserva);
                                    System.out.printf("""
                                                    La Reserva con id %d es:
                                                    Nombre del Estudiante: %s
                                                    Email del Estudiante: %s
                                                    Id de la Cancha: %s
                                                    Fecha: %s
                                                    Hora: %s
                                                    Estado: %s
                                                            ________________
                                                    """,
                                            r.getId(), r.getNombreEstudiante(), r.getEmailEstudiante(),
                                            r.getCanchaId(), r.getFecha(), r.getHoraInicio(), r.getEstado());
                                } catch (RecursoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            case 4:
                                System.out.println("Ingrese el ID de la reserva a cancelar");
                                int idReservaCancelar = sc.nextInt();
                                sc.nextLine();
                                try {
                                    mapReservas.cancelarReserva(idReservaCancelar);
                                } catch (RecursoNoEncontradoException | ReglaNegocioException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            case 5:
                                System.out.println("Ingrese el ID de la reserva a actualizar");
                                int idReservaActualizar = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Nota: Si no desea actualizar un campo, dejelo en blanco y presione enter");
                                System.out.println("Ingrese los nuevos datos de la reserva");
                                System.out.println("Ingrese el nuevo ID de la cancha a reservar");
                                String idCanchaActualizar = sc.nextLine();
                                System.out.println("Ingrese la nueva fecha de la reserva");
                                String fechaActualizar = sc.nextLine();
                                System.out.println("Ingrese la nueva hora de la reserva");
                                String horaActualizar = sc.nextLine();
                                System.out.println("Ingrese el nuevo nombre del estudiante");
                                String nombreEstudianteActualizar = sc.nextLine();
                                System.out.println("Ingrese el nuevo email del estudiante");
                                String emailEstudianteActualizar = sc.nextLine();
                                try {
                                    mapReservas.actualizarReserva(idReservaActualizar, idCanchaActualizar, fechaActualizar,
                                            horaActualizar, nombreEstudianteActualizar, emailEstudianteActualizar);
                                } catch (RecursoNoEncontradoException | ReglaNegocioException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            case 6:
                                System.out.println("Volviendo al menú principal");
                                break menuReservas;
                            default:
                                System.out.println("Haz seleccionado una opción invalida");
                                break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("gracias por usar el programa");
                    conexionSQLite.cerrarConexion();
                    return;
                default:
                    System.out.println("Haz seleccionado una opción invalida");
                    break;
            }
        }
    }
}