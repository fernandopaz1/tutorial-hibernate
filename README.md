# Tutorial de hibernate

La idea de este proyecto es ver como entender como funciona hibernate para construir un ORM con MySQL.

## Para correr este proyecto

Hay que agregar spring de forma manual, para esto creamos una carpeta en eclipse a la misma altura de la carpeta `src`. Se puede hacer descargando hibernate del siguiente [enlace](https://sourceforge.net/projects/hibernate/files/hibernate-orm/5.2.18.Final/hibernate-release-5.2.18.Final.zip/download). Al descomprimir el archivo se debe copiar el contenido de `/lib/required/` a `lib/` que creamos en eclipse.

También necesitamos el conector de mysql que lo obtenemos de [enlace](https://downloads.mysql.com/archives/c-j/)
se debe elegir la version `5.1.31`. Al descomprimir el proyecto se debe copiar el jar `mysql-connector-java-5.1.38-bin.jar` a `lib/`.
Una vez tenemos los `.jar` en la carpeta `lib` agregamos su contenido al classpath.

## Terminología

### Entity class

Es una clase de java que mediante annotations es mapeada a una tabla de la base de datos. En este caso los campos propios de la clase se van a mapear a columnas de la tabla.

![image](https://miro.medium.com/max/1400/1*DWD7ocJUfwxc0LcjG3ttSg.png)

Para poder modelar esta tabla con una clase, la clase debe coincidir en los campos que tiene la tabla.

```
@Entity
@Table(name="student")
public class Student{
    @Id
    @Column(name="id")
    private int id;

    @column(name="first_name")
    private String firstName;
    ...
}
```

Hibernate, como srping, escanea los paquetes que le indicamos en el archivo de configuraciones buscando las annotations que indiquen que una clase es una entidad.
Con la annotation `@Entity` indicamos que esta clase representa una entidad y con `@Table` que es una tabla. El nombre que le pasamos es el nombre de la tabla, también tenemos la posibilidad de nombrar a la entidad.

Para mapear los campos en el caso de la primary key lo indico con la annotation `@Id` y ademas debemos indicar que el campo es una columna con `@column` con su respectivo nombre.

El nombre del campo puede ser diferente (no es recomendado) por eso lo que determina que columna represente es el nombre que pasamos en la annotation. Esto se ve en el ejemplo del campo `first_name` que es una columna con nombre en snake_case mientras que en la clase esta escrito con camelCase. Si los nombres coinciden no es necesario especificar el nombre.

# Configuration Object

El objeto de configuración es el primer objeto que se crea en cualquier aplicación de hibernate. Se crea por única vez durante la iniciación de la aplicación y nos provee de los mecanismos de conexión a la base de datos y configuración del mapeo entre la base de datos y las clases.

El objeto de configuración también crea un objeto llamado SessionFactory:

## SessionFactory

SessionFactory es un objeto thread safe compartido por todos los threads de la aplicación. Es un objeto heavyweight, es decir creado durante el start up y mantenido en memoria para su uso posterior. Solo se usa un SessionFactory por cada base de datos, en caso de requerir mas conexiones se crean mas SessionFactory.

## Session Object

El SessionObject es un objeto instanciado por SessionFactory utilizado para obtener una conexión a la base de datos. Este objeto es lightweight, es decir esta diseñado para ser instanciado cada vez que se quiera acceder a la base de datos. No esta diseñado para ser mantenido por mucho tiempo ya que no es thread safe y debería ser creado y destruido cuando se necesite.

# Estado de una entidad

Las entidades de hibernate tienen un ciclo de vida en la cual va cambiando de estado y cambiando de propiedades. También hay ciertas operaciones que le ocurren a la entidad durante el ciclo de vida, estas son `Detach`. `Merge`, `Persist`, `Remove`, `Refresh`.

### Detach

Si se le hace detach a la entidad, esta deja estar vinculada a la la sesión de hibernate.

### Merge

Si una instancia es desvinculada de una sesión entonce merge la vincula nuevamente.

### Persist

Permite tomar una nueva instancia de la entidad y transicionarla a un estado `managed`. En estado se guardara en la base de datos si se ejecuta flush/commit.

### Remove

Transiciona una entidad `managed` y la elimina. El próximo flush/commit se eliminará de la base de datos.

### Refresh

Recarga/sincroniza un objeto con datos de la base de datos. Sirve para recuperar consistencia entre los objetos que usamos y los datos

![image](https://dev-mind.fr/img/training/spring-intro/lifecycle-e9f1372424.png)

| <b>Image Credits - Fig.2 - [dev-mind.fr](https://dev-mind.fr/training/spring/spring-data.html)</b>|

## Cascada de operaciones

La cascada es un forma de aplicar operaciones a entidades relacionadas. Por ejemplo si modificamos un elemento de la tabla `instructor` no interesa también guardar los elementos relacionados de la tabla `instructor_detail`. Este caso es importante ya que en el caso de eliminar un instructor también debemos eliminar su correspondiente detalle.

### Tipos de Cascada

- PERSIST: si la entidad es guardada la entidad relacionada también se guardará.
- REMOVE: si la entidad de borrada la relacionada también.
- REFRESH: si recarga la entidad original la relacionada también.
- DETACH: ...
- MERGE: ...
- ALL: incluye todos los tipos de cascada que se muestran arriba.

Por default ninguna operación hace cascada, es algo que debemos agregar con la annotation `OneToOne(cascade=CascadeType.ALL)` especificando el tipo de cascada.

## Tipos de carga de datos

Existen dos formas de hacer un fetch de la base de datoes, Eager y Lazy loading.

### Eager Loading

Va a cargar de una todas las entidades dependientes a la que estoy cargando. Puede ser usado cuando tenemos una cantidad pequeña de elementos pero si el numero crece puede afectar la performance.

Por ejemplo si queremos cargar una lista de cursos y estos tienen una carga eager, deberíamos cargar también todos los estudiantes de estos, ya que es una entidad dependiente.

En estos casos se prefiere la carga lazy.

### Lazy loading

Lazy loading va a cargar la entidad principal primero y luego a medida que se vaya demandando va a cargar las entidades dependientes. Cuando hacemos lazy loading es necesario que haya una sesión de hibernate abierta para poder traer datos cuando sea necesario. Si tratamos de obtener datos de forma lazy con una sesión cerrada hibernate va a tirar una excepción.

### Valores por defecto

Este comportamiento puede ser sobrescrito pasándola a la annotation el parámetro `fetchType`

|    Mapping    | Default Fetch Type |
| :-----------: | ------------------ |
|  `@OneToOne`  | FetchType.EAGER    |
| `@OneToMany`  | FetchType.LAZY     |
| `@ManyToOne`  | FetchType.EAGER    |
| `@ManyToMany` | FetchType.LAZY     |
