# Meteorological Data

Meteorological Data es una API desarrollada con Java, utilizando el
framework Spring Boot. Su función se basa en consumir datos de una API
externa, en este caso, OpenWeatherMap, y proporcionar esos datos a los
usuarios registrados en ella.

En esta API, se podrá consultar el clima actual, el pronóstico del
tiempo para los próximos 5 días y la contaminación del aire actual,
todo ello a partir de la ciudad proporcionada por el usuario.

La API tiene un límite de peticiones tanto a nivel del servidor (1000
peticiones diarias) como a nivel de usuario (100 peticiones por hora).
Además, cuenta con la implementación de caché, lo cual mejora la
eficiencia de las respuestas al realizar consultas en diferentes
ciudades.

## Índice

1. [Requisitos](#requisitos)
2. [Configuración](#configuración)
3. [Ejecución](#ejecución)
4. [Endpoints de la API](#endpoints-de-la-api)
5. [Autenticación y Autorización](#autenticación-y-autorización)
6. [Almacenamiento en Base de Datos](#almacenamiento-en-base-de-datos)
7. [Limitación de Tasa de la API](#limitación-de-tasa-de-la-api)
8. [Pruebas y Validación](#pruebas-y-validación)
9. [Contenerización](#contenerización)
10. [Auditoría y Registro](#auditoría-y-registro)
11. [Documentación Automática con Swagger](#documentación-automática-con-swagger)
12. [Contribución](#contribución)
13. [Licencia](#licencia)

## Requisitos

Lista de requisitos previos para configurar y ejecutar el proyecto.

## Configuración

Instrucciones detalladas sobre cómo configurar el proyecto. Esto puede incluir configuraciones de base de datos, credenciales, etc.

## Ejecución

Pasos para ejecutar el proyecto. Puede incluir comandos de terminal o configuraciones específicas.

## Autenticación y Autorización

Luego de haber levantado el servidor y configurado la base de datos,
podemos proceder a registrarnos en nuestra aplicacion enviando una peticion
al siguiente endpoint.

```url
localhost:8080/auth/register
```

Los datos se deben enviar en el cuerpo de la peticion en formato json
con la siguiente estructura

```json
{
  "email": "user@correo.com",
  "name": "user",
  "username": "username",
  "password": "password"
}
```

Si todo sale bien recibiras el sigueinte mensaje:

```json

```

## Endpoints de la API

Detalles sobre cómo se maneja la autenticación y autorización. Incluye cómo obtener tokens JWT y restricciones de acceso.

## Almacenamiento en Base de Datos

Explicación de la elección de la base de datos, estructura de tablas y ejemplos de consultas SQL.

## Limitación de Tasa de la API

Información sobre cómo se implementa la limitación de tasa en la API, con detalles sobre los límites.

## Pruebas y Validación

Instrucciones para ejecutar las pruebas unitarias y verificar la funcionalidad.

## Contenerización

Pasos para construir y ejecutar el contenedor Docker.

## Auditoría y Registro

Explicación de cómo se registran las solicitudes y respuestas para fines de auditoría.

## Documentación Automática con Swagger

Enlace y descripción de la documentación generada automáticamente por Swagger.

## Contribución

Instrucciones sobre cómo contribuir al proyecto.

## Licencia

Detalles sobre la licencia del proyecto.

# Título 1

## Título 2

### Título 3

#### Título 4

##### Título 5

###### Título 6

**Texto en negrita**
_Texto en cursiva_

- Elemento 1
- Elemento 2
  - Elemento anidado
- Elemento 3

1. Primer elemento
2. Segundo elemento
3. Tercer elemento
   [Texto del enlace](URL)
   ![Texto alternativo](URL_de_la_imagen)
   > Esto es una cita.

```java
public class Ejemplo {
    public static void main(String[] args) {
        System.out.println("Hola, mundo!");
    }
}
```

## `Código en línea`

---

| Encabezado 1 | Encabezado 2 |
| ------------ | ------------ |
| Celda 1,1    | Celda 1,2    |
| Celda 2,1    | Celda 2,2    |

```sql
SELECT * FROM employess;
```

```java
public class Ejemplo {
    public static void main(String[] args) {
        System.out.println("Hola, mundo!");
    }
}
```
