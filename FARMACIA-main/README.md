# FARMASTOCK — Práctica de archivos en Java

## Parte 2: lectura y escritura de archivos

Esta versión amplía el proyecto FarmaStock para guardar el inventario de medicamentos en `inventario_farmacia.txt` y luego volver a leerlo.

### ¿Qué se agregó?
- `guardarInventario(String nombreArchivo)` en `InventarioController`: guarda ID, nombre, categoría, precio, cantidad y fecha de vencimiento.
- `cargarInventario(String nombreArchivo)` en `InventarioController`: lee el archivo y reconstruye los objetos `Medicamento`.
- `Main.java`: después de las operaciones del inventario, guarda el archivo y muestra los registros recuperados.

### Cómo ejecutar en Visual Studio Code
1. Abre la carpeta `FARMACIA-main`.
2. Asegúrate de tener instalado un JDK y la extensión Java.
3. Ejecuta `src/Main.java` con **Run Java**. También puedes compilar desde una terminal situada en `src`:
   `javac *.java`
4. Ejecuta:
   `java Main`
5. El archivo `inventario_farmacia.txt` se crea en el directorio de trabajo desde el que se ejecuta el programa.

### Capturas que debes adjuntar
1. La consola mostrando `Archivo guardado correctamente: inventario_farmacia.txt`.
2. La consola mostrando `Inventario recuperado desde el archivo` con los medicamentos.
3. El archivo `inventario_farmacia.txt` abierto en VS Code o Bloc de notas.

### Explicación breve para el informe
La aplicación FarmaStock administra medicamentos con datos como identificador, nombre, categoría, precio, stock y fecha de vencimiento. Para la práctica de archivos se añadieron métodos que guardan estos datos en un archivo de texto y los leen posteriormente. Se utiliza `BufferedWriter` para escribir, `BufferedReader` para leer, `Files` y `Paths` para manejar la ruta, y `try-catch` para controlar errores de entrada y salida. Así, el inventario puede conservarse en un archivo y recuperarse para mostrar nuevamente los medicamentos.

**Formato del archivo:** cada línea representa un medicamento y sus campos se separan con punto y coma.
