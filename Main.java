package tecnomovil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Lista inmutable de datos simulados.
        List<RegistroTransporte> registros = List.of(

                new RegistroTransporte("U001", "R10", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 25, 7, 10)),
                new RegistroTransporte("U001", "R10", "Universidad", "salida",
                        LocalDateTime.of(2026, 9, 25, 7, 30)),

                new RegistroTransporte("U002", "R20", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 25, 7, 15)),
                new RegistroTransporte("U002", "R20", "Terminal", "salida",
                        LocalDateTime.of(2026, 9, 25, 7, 45)),

                new RegistroTransporte("U003", "R10", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 25, 8, 5)),
                new RegistroTransporte("U003", "R10", "Universidad", "salida",
                        LocalDateTime.of(2026, 9, 25, 8, 25)),

                new RegistroTransporte("U004", "R30", "Terminal", "entrada",
                        LocalDateTime.of(2026, 9, 25, 8, 10)),
                new RegistroTransporte("U004", "R30", "Centro", "salida",
                        LocalDateTime.of(2026, 9, 25, 8, 40)),

                new RegistroTransporte("U005", "R10", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 25, 17, 20)),
                new RegistroTransporte("U005", "R10", "Universidad", "salida",
                        LocalDateTime.of(2026, 9, 25, 17, 50)),

                new RegistroTransporte("U006", "R20", "Terminal", "entrada",
                        LocalDateTime.of(2026, 9, 25, 17, 25)),
                new RegistroTransporte("U006", "R20", "Centro", "salida",
                        LocalDateTime.of(2026, 9, 25, 17, 55)),

                new RegistroTransporte("U007", "R10", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 25, 18, 0)),
                new RegistroTransporte("U007", "R10", "Universidad", "salida",
                        LocalDateTime.of(2026, 9, 25, 18, 25)),

                new RegistroTransporte("U008", "R10", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 25, 18, 5)),
                new RegistroTransporte("U008", "R10", "Universidad", "salida",
                        LocalDateTime.of(2026, 9, 25, 18, 35))
        );

        System.out.println("==============================================");
        System.out.println("             TECNOMOVIL DATA");
        System.out.println("     PROCESAMIENTO FUNCIONAL DE DATOS");
        System.out.println("==============================================");

        System.out.println("\nCantidad de registros procesados: " + registros.size());

        System.out.println("\n1. AFLUENCIA POR ESTACIÓN");
        Map<String, Long> afluencia =
                ProcesadorTransporte.calcularAfluenciaPorEstacion(registros);
        afluencia.forEach((estacion, cantidad) ->
                System.out.println(estacion + " -> " + cantidad + " entradas"));

        System.out.println("\n2. HORAS DE MAYOR FLUJO");
        ProcesadorTransporte.obtenerHorasOrdenadas(registros)
                .forEach(e ->
                        System.out.println(e.getKey() + ":00 -> "
                                + e.getValue() + " entradas"));

        System.out.println("\n3. RUTAS MÁS UTILIZADAS");
        ProcesadorTransporte.obtenerRutasOrdenadas(registros)
                .forEach(e ->
                        System.out.println(e.getKey() + " -> "
                                + e.getValue() + " usos"));

        System.out.println("\n4. PATRONES DE VIAJE POR USUARIO");
        ProcesadorTransporte.obtenerPatronesPorUsuario(registros)
                .forEach((usuario, estaciones) ->
                        System.out.println(usuario + " -> " + estaciones));

        System.out.println("\n5. TIEMPO PROMEDIO ENTRE REGISTROS");
        double promedio =
                ProcesadorTransporte.calcularTiempoPromedioEntreEstaciones(registros);
        System.out.printf("%.2f minutos%n", promedio);

        System.out.println("\n6. DETECCIÓN DE RUTAS CRÍTICAS");
        long umbral = 3;
        ProcesadorTransporte.detectarRutasCriticas(registros, umbral)
                .forEach((ruta, estado) ->
                        System.out.println(ruta + " -> " + estado
                                + " (umbral: " + umbral + ")"));

        System.out.println("\n7. PROCESAMIENTO CON STREAM PARALELO");
        long entradasParalelas = registros.parallelStream()
                .filter(r -> r.accion().equalsIgnoreCase("entrada"))
                .count();

        System.out.println("Entradas procesadas con parallelStream(): "
                + entradasParalelas);

        System.out.println("\n==============================================");
        System.out.println("        PROCESAMIENTO FINALIZADO");
        System.out.println("==============================================");
    }
}
