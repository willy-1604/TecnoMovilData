# TecnoMovil Data

Proyecto académico en Java para el caso de estudio de procesamiento funcional de datos de un sistema de gestión de transporte urbano.

## Objetivo

Implementar un módulo funcional que procese registros de transporte mediante:

- Streams
- Lambdas
- Funciones sin efectos secundarios
- Colecciones inmutables
- Agrupaciones y transformaciones declarativas
- Posibilidad de paralelización con `parallelStream()`

## Estructura

```text
TecnoMovilData
├── src
│   └── tecnomovil
│       ├── RegistroTransporte.java
│       ├── ProcesadorTransporte.java
│       └── Main.java
└── README.md
```

## Requisitos

Java 17 o superior. El proyecto también funciona con Java 26.

## Compilar

Desde la carpeta raíz del proyecto:

```bash
javac -d out src/tecnomovil/*.java
```

## Ejecutar

```bash
java -cp out tecnomovil.Main
```

## Operaciones implementadas

1. Cálculo de afluencia por estación.
2. Identificación de horas de mayor flujo.
3. Determinación de rutas más utilizadas.
4. Patrones de viaje por usuario.
5. Cálculo del tiempo promedio entre registros consecutivos.
6. Detección de rutas críticas según un umbral.
7. Ejemplo de procesamiento mediante `parallelStream()`.

## Nota

Los registros incluidos en `Main.java` son datos simulados para comprobar el funcionamiento del módulo. El caso de estudio plantea volúmenes mucho mayores; en una implementación real se utilizarían fuentes de datos masivas y mecanismos de procesamiento adecuados.
