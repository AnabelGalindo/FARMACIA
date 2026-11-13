# FARMASTOCK

Muchas farmacias de barrio pequeñas administran su inventario de medicamentos de forma manual,
mediante cuadernos de registro u hojas de cálculo aisladas. Esta forma de trabajo dificulta conocer
con precisión qué productos están próximos a vencer, cuántas unidades hay disponibles de cada
referencia y cuáles ya no deberían venderse por haber superado su fecha de vencimiento.

**FARMASTOCK** es un programa de escritorio en Java (Swing) que resuelve este problema. Permite
registrar medicamentos, venderlos, controlar el stock y ver rápidamente cuáles están vencidos o por
vencer. Los datos se guardan en un archivo CSV, por lo que no necesita instalar ninguna base de datos.

## ¿Qué puede hacer?

- Agregar, buscar, vender y eliminar medicamentos.
- Ver el inventario en una tabla ordenable, con colores según el estado de cada producto.
- Consultar los medicamentos próximos a vencer.
- Agrupar los medicamentos por categoría.
- Ver gráficos: stock por categoría y estado general del inventario.
- Importar y exportar el inventario en formato CSV.

## Requisitos

- **Java 25** (JDK), según lo configurado en `pom.xml`.
- Un IDE como IntelliJ IDEA o NetBeans o Maven desde terminal.

## Cómo ejecutarlo

1. Descarga o clona el proyecto:
   ```
   git clone https://github.com/AnabelGalindo/FARMACIA.git
   ```
2. Ábrelo en tu IDE como proyecto Maven y espera a que se descarguen las dependencias.
3. Ejecuta la clase `Main`.

> Ejecuta el programa desde la carpeta raíz del proyecto: ahí es donde busca el archivo
> `medicamentos.csv`. Si no lo encuentra, inicia con el inventario vacío.

## Cómo usarlo

Al abrir el programa verás una tabla con el inventario, una fila de botones arriba y un menú
(**Archivo**, **Medicamentos**, **Reportes**) que ofrece las mismas opciones.

| Acción | Qué hace |
| --- | --- |
| **Agregar** | Pide ID, nombre, categoría, precio, cantidad y fecha de vencimiento (se elige en un calendario). El ID no puede repetirse. |
| **Vender** | Descuenta unidades del stock. Selecciona una fila de la tabla (o escribe el ID cuando te lo pida) e indica la cantidad. |
| **Eliminar** | Quita el medicamento seleccionado, previa confirmación. |
| **Buscar** | Busca por ID o por nombre y muestra solo ese resultado. |
| **Por vencer** | Muestra los medicamentos que vencen dentro de los días que indiques (30 por defecto). |
| **Por categoría** | Muestra una lista de los medicamentos agrupados por categoría. |
| **Gráficos** | Abre un gráfico de barras (stock por categoría) y uno circular (estado del inventario). |
| **Ver todos** | Vuelve al listado completo después de una búsqueda o filtro. |
| **Importar CSV** | Carga un archivo CSV y **reemplaza** el inventario actual. |
| **Exportar CSV** | Guarda el inventario en un archivo CSV a tu elección. |

Puedes ordenar la tabla haciendo clic en el encabezado de cualquier columna.

### Colores de la tabla

- 🔴 **Rojo (VENCIDO):** el medicamento ya pasó su fecha de vencimiento.
- 🟠 **Naranja (Por vencer):** vence en los próximos 30 días.
- ⚪ **Blanco (OK):** vigente.

### Reglas de venta

El programa no permite vender cuando:

- el medicamento está **vencido**,
- se pide **más cantidad** de la que hay en stock, o
- la cantidad es **cero o negativa**.

En esos casos muestra un mensaje de error y no modifica el inventario.

### Guardado de datos

- Al **cerrar la ventana** el inventario se guarda automáticamente en el CSV.
- También puedes usar **Archivo → Guardar** en cualquier momento.

## Formato del archivo CSV

El archivo `medicamentos.csv` tiene una fila de encabezado y una fila por medicamento:

```csv
id,nombre,categoria,precio,cantidad,fechaVencimiento
1,Paracetamol,Analgésicos,10.0,11,2027-11-22
```

- `precio`: número con punto decimal (se muestra en soles, S/).
- `fechaVencimiento`: formato `aaaa-mm-dd`.
- Si importas un archivo con filas inválidas (datos incorrectos o ID repetido), esas filas se omiten
  y el programa te muestra un aviso con el detalle.
