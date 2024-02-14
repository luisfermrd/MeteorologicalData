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
4. [Autenticación y Autorización](#autenticación-y-autorización)
5. [Endpoints de la API](#endpoints-de-la-api)
6. [Detener servicios](#detener-servicios)
7. [Limitación de Tasa de la API](#limitación-de-tasa-de-la-api)
8. [Auditoría y Registro](#auditoría-y-registro)
9. [Documentación Automática con Swagger](#documentación-automática-con-swagger)

## Requisitos

Para ejecutar este proyecto, tienes dos opciones. Dependiendo de tus 
necesidades, deberás tener [Docker](https://www.docker.com) instalado. Si no deseas usar Docker, 
deberás tener una base de datos MySQL de forma local como mínimo. 
De igual manera, se necesita [Maven](https://maven.apache.org) para la compilación del proyecto.

## Configuración

Como primer paso, debemos abrir una terminal y ubicarnos en la raíz de 
nuestro proyecto para ejecutar el siguiente comando, para construir el proyecto.
Si no deseas usar Docker, deberás tener un servicio de MySQL disponible
en tu ordenador, crear la base de datos `meteorologicalDataDb`, y
configurar en el archivo `application.properties` el usuario y
contraseña correspondientes.

````shell
mvn clean install
````
Si vamos a utilizar Docker, el siguiente paso sería construir nuestras 
imágenes para luego levantar los servicios. Esto lo haremos ejecutando el 
siguiente comando. (Si estás usando Linux, deberás descargar Docker Compose).

````shell
 docker-compose build
````

## Ejecución

La ejecución es tan simple como ejecutar el siguiente comando, 
si estás usando Docker.

````shell
docker-compose up
````

Los servicios se pueden ejecutar en segundo plano con el comando:

````shell
docker-compose up -d
````

Para los que no van a usar Docker, pueden ejecutar el 
siguiente comando.

````shell
mvn spring-boot:run
````

## Autenticación y Autorización

Luego de haber levantado el servidor y configurado la base de datos, 
podemos proceder a registrarnos en nuestra aplicación enviando una 
petición al siguiente endpoint.


````http request
localhost:8080/auth/register
````

Los datos se deben enviar en el cuerpo de la petición en formato JSON 
con la siguiente estructura:

````json lines
{
  "email": "user@correo.com",
  "name": "user",
  "username": "username",
  "password": "password"
}
````

Si todo sale bien, recibirás el siguiente mensaje:

````json lines
{
    "message": "Registered user successfully"
}
````

El siguiente paso será obtener el token de autenticación, 
para lo cual debemos hacer una petición a la siguiente URL:

````http request
localhost:8080/auth/login
````

Con los siguientes datos:

````json lines
{
    "username":"username",
    "password":"password"
}
````

Si todo sale bien, obtendrás una respuesta con el token:

````json lines
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTcwNzg0NzQxMSwiZXhwIjoxNzA3ODUxMDExfQ.ybs5qcd4m0QuXBMZv2oL3c0EymURSjYeTnQ2DLODQ_A",
  "message": "Successful login"
}
````

## Endpoints de la API

Recuerda que debes estar autorizado para solicitar información a los 
endpoints, por lo que deberás enviar el token en la cabecera de la 
solicitud, en una autorización de tipo `Bearer Token`.

- ### Clima actual

El clima actual lo podrás obtener mediante el siguiente endpoint, donde 
deberás reemplazar {ciudad} por el nombre de la ciudad que estés buscando.

````http request
localhost:8080/api/weather/{ciudad}
````
Si buscamos London, obtendremos un resultado similar al siguiente:

````json lines
{
  "base": "stations",
  "clouds": {
    "all": 100
  },
  "cod": 200,
  "coord": {
    "lat": 51.5073,
    "lon": -0.1276
  },
  "dt": 1707847772,
  "id": 2643743,
  "main": {
    "humidity": 93,
    "pressure": 1008,
    "temp": 283.27,
    "feels_like": 282.77,
    "temp_max": 284.43,
    "temp_min": 281.98
  },
  "name": "London",
  "rain": null,
  "sys": {
    "country": "GB",
    "id": 2075535,
    "sunrise": 1707808778,
    "sunset": 1707844202,
    "type": 2
  },
  "timezone": 0,
  "visibility": 10000,
  "weather": [
    {
      "description": "overcast clouds",
      "icon": "04n",
      "id": 804,
      "main": "Clouds"
    }
  ],
  "wind": {
    "deg": 200,
    "speed": 5.14
  }
}
````
- ### Pronóstico del tiempo para los próximos 5 días.

De igual forma, debes hacer una solicitud al siguiente endpoint:

````http request
localhost:8080/api/forecast/{ciudad}
````
Y obtendremos una respuesta como la siguiente (La llave `list` contiene más
objetos, pero en este ejemplo solo se ha limitado a 1):

````json lines
{
    "cod": "200",
    "message": 0,
    "cnt": 40,
    "list": [
        {
            "dt": 1707858000,
            "main": {
                "temp": 283.64,
                "feels_like": 283.12,
                "temp_min": 283.64,
                "temp_max": 284.06,
                "pressure": 1009,
                "sea_level": 1009,
                "grnd_level": 1007,
                "humidity": 91,
                "temp_kf": -0.42
            },
            "weather": [
                {
                    "id": 500,
                    "main": "Rain",
                    "description": "light rain",
                    "icon": "10n"
                }
            ],
            "clouds": {
                "all": 100
            },
            "wind": {
                "speed": 5.7,
                "deg": 226,
                "gust": 14.3
            },
            "visibility": 10000,
            "pop": 0.39,
            "rain": {
                "3h": 0.12
            },
            "sys": {
                "pod": "n"
            },
            "dt_txt": "2024-02-13 21:00:00"
        }
    ],
    "city": {
        "id": 2643743,
        "name": "London",
        "coord": {
            "lat": 51.5073,
            "lon": -0.1276
        },
        "country": "GB",
        "population": 1000000,
        "timezone": 0,
        "sunrise": 1707808778,
        "sunset": 1707844202
    }
}
````
- ### Contaminación del aire actual

De igual forma, debes hacer una solicitud al siguiente endpoint:

````http request
localhost:8080/api/air-pollution/{ciudad}
````
Y obtendremos una respuesta como la siguiente:

````json lines
{
    "coord": {
        "lon": -0.1276,
        "lat": 51.5073
    },
    "list": [
        {
            "main": {
                "aqi": 1
            },
            "components": {
                "co": 263.69,
                "no": 0,
                "no2": 23.31,
                "o3": 50.78,
                "so2": 7.75,
                "pm2_5": 2.28,
                "pm10": 2.51,
                "nh3": 0.38
            },
            "dt": 1707848911
        }
    ]
}
````
## Detener servicios

Si usaste Docker para probar esta aplicación, podrás detener los
contenedores en ejecución de forma muy sencilla con el siguiente comando:

````shell
docker compose down
````

Si necesita detener y eliminar todos los contenedores, redes y todas las 
imágenes utilizadas por cualquier servicio en el archivo docker-compose.yml, 
use el comando:

````shell
docker compose down --rmi all
````

Si no estás usando Docker, bastará con presionar `Ctrl + C` en la terminal
que se esta ejecutando el servicio para detenerlo.

## Limitación de Tasa de la API

Si bien es conveniente utilizar este servicio, es importante tener en cuenta las
limitaciones tanto del servidor como del usuario. El servidor tiene un límite de 1000
peticiones al día, mientras que el usuario está limitado a 100 peticiones por hora. 
Estas restricciones se han establecido con el fin de evitar posibles errores debidos 
al consumo excesivo de la API externa. Además, estas limitaciones contribuyen a 
mantener el correcto funcionamiento del servidor y a prevenir posibles colapsos debido
a un uso excesivo.

## Auditoría y Registro

Con fines de auditoría, todas las solicitudes realizadas por los usuarios se registrarán
en la base de datos. Los datos registrados incluirán:

- Nombre del usuario que realizó la petición.
- Fecha y hora de la petición.
- Tipo de petición realizada.
- Respuesta obtenida por parte del servidor.

Estos datos estarán disponibles únicamente al revisar la tabla correspondiente en la base de datos, 
garantizando la trazabilidad y transparencia en el uso del sistema.

## Documentación Automática con Swagger

Para acceder a la documentación que automáticamente genera Swagger de los endpoints de la API,
mientras nuestro servicio aún esté en ejecución, podemos ingresar en nuestro navegador a la siguiente URL:

````http request
localhost:8080/swagger-ui.html
````
Esto nos redireccionará a la página donde se encuentra toda la documentación 
con el fin de entender mejor cómo funciona nuestra aplicación.

Si deseas probar los endpoints en esta sección para los cuales necesitas 
autorización, primero deberás obtener el `token` (si no tienes uno disponible ya) 
en el endpoint de inicio de sesión. En la parte superior derecha estará un 
botón llamado `Authorize`, donde deberás suministrar el `token` para poder utilizar 
los endpoints protegidos.