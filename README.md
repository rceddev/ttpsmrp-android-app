## TT Prototipo de Sistema para Monitoreo Remoto de Plantas - Android App

Aplicación movil Android para el proyecto de trabajo terminal: Prototipo de Sistema para Monitoreo Remoto de Plantas `2020-B031`. Desarrollada para tener acceso a funcionalidades como monitorización, configuración
de nodos y la gestión del usuario  Este sistema es el encargado de englobar el funcionamiento del
resto de módulos desarrollados para este proyecto. Los modulos que componen la aplicacion son:

### Gestion de usuarios
Este modulo tiene como funcion realizar la gestion de usuarios para acceder al sistema.

![UserManagement](https://user-images.githubusercontent.com/89185629/179671703-566b6f68-f208-4dad-8e07-178804547c2f.png)

1. Sign in
2. Sign up
3. Confirmation code

### Home
En este modulo se muestra un pantalla con los nodos asi como la planta que esta vinculado con este, previamente registrados por el usuario. Permite seleccionar uno de estos para ir a la monitorizacion asi como agregar un nuevo usuario.

<p align="center">
  <img width="240" height="500" src="https://user-images.githubusercontent.com/89185629/179673701-2431edcf-9f43-4ba0-a36c-409d4d1869cc.PNG">
</p>


### Monitorizacion
Modulo principal encargado de realizar la monitorizacion en tiempo real de la planta que se vinculo con el nodo que registra la informacion. Cuenta con 4 pantallas principales en las que cada una tendra una funcion diferente para brindar a usuario una informacion detallada del estado de su planta.

<p align="center">
  <img src="https://user-images.githubusercontent.com/89185629/179675133-0d178b47-7486-4671-babd-2791a77e9d61.PNG">
</p>

1. Monitorizacion en tiempo real - Realiza la lectura del servidor del estado de las variables fisicas. Proporciona informacion y consejos en base al valor medido comparados con un banco de conocimientos con las condiciones sustentables para el desarrollo de la especie en cuestion. 
2. Graficacion - Consulta las mediciones que se han llevado a lo largo del dia para visulizarlos en una grafica y poder ver de manera mas sencilla el cambio que ha sufrido en cada variable fisica. 
3. Lista de mediciones - Consula las mismas mediciones que en 2 pero muestra los datos de una forma mas convencional y precisa.
4. Informacion de la planta - Provee informacion de la especie de la planta que el nodo esta monitorizando.

### Extras

Acontinuacion se enlistan las bibliotecas utilizadas en este proyecto.

* [Retrofit](https://square.github.io/retrofit/). Cliente Http para consumir servicios web necesarios para gestion de usuarios, registro de nodos y monitorizacion.
* [Picasso](https://square.github.io/picasso/). Libreria para descarga de imagenes
* [CircleImageView](https://github.com/hdodenhof/CircleImageView). Libreria para mostrar ImageView circulares.
* [MPAndroidChart](https://weeklycoding.com/mpandroidchart/). Libreria para graficacion. 

## Advertencia

Este documento contiene información desarrollada por la Escuela
Superior de Cómputo del Instituto Politécnico Nacional, a partir de datos
y documentos con derecho de propiedad y por lo tanto, su uso quedará
restringido a las aplicaciones que explícitamente se convengan.”
La aplicación no convenida exime a la escuela su responsabilidad técnica y
da lugar a las consecuencias legales que para tal efecto se determinen.
Información adicional sobre este reporte técnico podrá obtenerse en:
La Subdirección Académica de la Escuela Superior de Cómputo del
Instituto Politécnico Nacional, situada en Av. Juan de Dios Bátiz s/n
Teléfono: 57296000, extensión 52000.


## Warning

This document contains information developed by the Escuela
Superior de Cómputo from Instituto Politécnico Nacional, from data and documents with 
property rights and therefore, its use will be restricted to applications that 
are cleanly agreed upon.” The non-agreed application exempts the school from its 
technical responsibility and gives rise to the legal consequences that are determined for 
this purpose. Additional information on this technical report may be obtained at: The Academic Subdirectorate
of the Escuela Superior de Cómputo del Instituto Politécnico Nacional, located at Av. Juan de Dios Bátiz s/n
Teléfono: 57296000, extensión 52000.

<p align="center">
  <img width="240" height="200" src="https://user-images.githubusercontent.com/89185629/179432052-a5e258fd-b915-4664-b413-3416cb3fc495.png">
</p>

