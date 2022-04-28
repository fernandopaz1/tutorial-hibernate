# Tutorial de hibernate

La idea de este proyecto es ver como entender como funciona hibernate para construir un ORM con MySQL.

## Para correr este proyecto

Hay que agregar spring de forma manual, para esto creamos una carpeta en eclipse a la misma altura de la carpeta `src`. Se puede hacer descargando hibernate del siguiente [enlace](https://sourceforge.net/projects/hibernate/files/hibernate-orm/5.2.18.Final/hibernate-release-5.2.18.Final.zip/download). Al descomprimir el archivo se debe copiar el contenido de `/lib/required/` a `lib/` que creamos en eclipse.
También necesitamos el conector de mysql que lo obtenemos de [enlace](https://downloads.mysql.com/archives/c-j/)
se debe elegir la version `5.1.31`. Al descomprimir el proyecto se debe copiar el jar `mysql-connector-java-5.1.38-bin.jar` a `lib/`.
Una vez tenemos los `.jar` en la carpeta `lib` agregamos su contenido al classpath.
