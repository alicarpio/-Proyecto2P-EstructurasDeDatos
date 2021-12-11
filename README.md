# Creando un proyecto JavaFX con Gradle

## Instalar Gradle

Para descargar el binario de Gradle, haz click en el siguiente enlace:
https://gradle.org/next-steps/?version=7.1.1&format=bin

Para configurar tu instalación de Gradle en Windows, sigue estos pasos:

1. Crea una carpeta `C:\Gradle` utilizando tu explorador de archivos
2. En otra ventana del explorador, haz doble click en el Zip que descargaste antes
3. Arrastra la carpeta `gradle-7.1.1` (los números pueden ser diferentes según la versión descargada)
   a la carpeta `C:\Gradle` recién creada
4. En el explorador de archivos, haz click derecho en `Este PC`, 
   luego `Propiedades` -> `Configuración Avanzada del Sistema` -> `Variables de Entorno`
5. Edita la variable de entorno `Path` y añade una entrada para `C:\Gradle\gradle-7.1.1\bin`

Tras seguir estos pasos Gradle debería estar instalado correctamente. Puedes verificar
tu instalación abriendo una consola y ejecutando el comando `gradle -v`.

## Creando un Proyecto con Gradle

Una vez instalado Gradle, podemos crear un proyecto utilizando el siguiente comando.
Si estas utilizando Windows, abre Git Bash en el directorio donde quieres tener tu
proyecto y ejecuta el siguiente comando:

```bash
gradle init --type java-application \
    --dsl groovy  \
    --test-framework junit-jupiter \
    --project-name miproyecto \
    --package mipaquete
```

Reemplaza `mi-proyecto` por el nombre que quieres que tenga tu proyecto y `mi-paquete` por el
nombre del paquete principal del mismo.

Otra forma de hacerlo es simplemente correr `gradle init` e ir respondiendo las preguntas que te
hagan.

Tras hacer esto, se generarán algunos archivos, los más importantes son la carpeta `app/src` y el
archivo `build.gradle`. En `app/src` estará nuestro código fuente, mientras que `build.gradle` es
un script que indicará a gradle cómo debe correr nuestro proyecto, entre otras cosas.

## Un `build.gradle` para JavaFX

Para poder correr tu aplicación JavaFX con Gradle y poder generar un jar que funcione
en Windows, Linux y Mac, copia el siguiente bloque de código en tu `build.gradle`:

```groovy
plugins {
    id "application"
    id 'org.openjfx.javafxplugin' version '0.0.9'
}

application {
    mainClass = "mipaquete.Launcher"
}

javafx {
    version = "16"
    modules = [ 'javafx.controls', 'javafx.fxml']
}

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly "org.openjfx:javafx-base:$javafx.version:win"
    runtimeOnly "org.openjfx:javafx-base:$javafx.version:linux"
    runtimeOnly "org.openjfx:javafx-base:$javafx.version:mac"
    runtimeOnly "org.openjfx:javafx-controls:$javafx.version:win"
    runtimeOnly "org.openjfx:javafx-controls:$javafx.version:linux"
    runtimeOnly "org.openjfx:javafx-controls:$javafx.version:mac"
    runtimeOnly "org.openjfx:javafx-fxml:$javafx.version:win"
    runtimeOnly "org.openjfx:javafx-fxml:$javafx.version:linux"
    runtimeOnly "org.openjfx:javafx-fxml:$javafx.version:mac"
}

jar {
    baseName = 'proyecto_grupo_X'
    duplicatesStrategy = 'include'
    from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }  
    manifest {
        attributes(
            'Main-Class': 'mipaquete.Launcher'
        )
    }
}
```

Con esta configuración podrás ejecutar los siguientes comandos:

- `gradle run` para correr tu aplicación
- `gradle jar` para generar el jar

El jar se genera en la carpeta `app/build/libs`. También puedes correr `gradle clean` para 
limpiar el proyecto y asegurarte de que todo está fresco la próxima vez que construyas el jar.
