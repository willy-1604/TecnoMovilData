package tecnomovil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contiene las operaciones de procesamiento funcional.
 * Los métodos no modifican la lista original de registros.
 */
public final class ProcesadorTransporte {

    private ProcesadorTransporte() {
    }

    // a) Cálculo de afluencia por estación.
    public static Map<String, Long> calcularAfluenciaPorEstacion(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .filter(r -> r.accion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::estacion,
                        Collectors.counting()
                ));
    }

    // b) Identificación de horas pico.
    public static Map<Integer, Long> identificarHorasPico(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .filter(r -> r.accion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        r -> r.timestamp().getHour(),
                        Collectors.counting()
                ));
    }

    // c) Rutas más utilizadas.
    public static Map<String, Long> obtenerRutasMasUtilizadas(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .filter(r -> r.accion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::ruta,
                        Collectors.counting()
                ));
    }

    // d) Patrones de viaje por usuario.
    public static Map<String, List<String>> obtenerPatronesPorUsuario(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .sorted(Comparator.comparing(RegistroTransporte::timestamp))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::idUsuario,
                        Collectors.mapping(
                                RegistroTransporte::estacion,
                                Collectors.toUnmodifiableList()
                        )
                ));
    }

    // e) Tiempo promedio entre registros consecutivos de cada usuario.
    public static double calcularTiempoPromedioEntreEstaciones(
            List<RegistroTransporte> registros) {

        Map<String, List<RegistroTransporte>> porUsuario =
                registros.stream()
                        .sorted(Comparator.comparing(
                                RegistroTransporte::timestamp))
                        .collect(Collectors.groupingBy(
                                RegistroTransporte::idUsuario
                        ));

        return porUsuario.values().stream()
                .flatMap(lista -> calcularDiferencias(lista).stream())
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
    }

    private static List<Long> calcularDiferencias(
            List<RegistroTransporte> registros) {

        return java.util.stream.IntStream.range(1, registros.size())
                .mapToObj(i -> {
                    LocalDateTime anterior =
                            registros.get(i - 1).timestamp();
                    LocalDateTime actual =
                            registros.get(i).timestamp();

                    return Duration.between(anterior, actual).toMinutes();
                })
                .toList();
    }

    // f) Detección de rutas que superan el umbral.
    public static Map<String, String> detectarRutasCriticas(
            List<RegistroTransporte> registros,
            long umbral) {

        return obtenerRutasMasUtilizadas(registros)
                .entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entrada -> entrada.getValue() > umbral
                                ? "CRÍTICA"
                                : "NORMAL"
                ));
    }

    // Devuelve las horas ordenadas por cantidad de entradas.
    public static List<Map.Entry<Integer, Long>> obtenerHorasOrdenadas(
            List<RegistroTransporte> registros) {

        return identificarHorasPico(registros)
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .toList();
    }

    // Devuelve las rutas ordenadas por cantidad de usos.
    public static List<Map.Entry<String, Long>> obtenerRutasOrdenadas(
            List<RegistroTransporte> registros) {

        return obtenerRutasMasUtilizadas(registros)
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();
    }
}
