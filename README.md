# TimeBee

¡Bienvenido a **TimeBee**!  
Este es un proyecto lleno de esfuerzo, aprendizaje y pasión . Aquí encontrarás tanto el backend como el frontend de una aplicación para gestionar **empresas**, **trabajadores**, **fichajes**, **permisos** y **nóminas**.

---

## ¿Qué es TimeBee?

TimeBee es una solución web pensada para facilitar la administración de personal.  
Imagínate tener todo en un solo lugar: trabajadores que pueden marcar entradas y salidas, solicitar permisos, ver sus nóminas…  
Y empresas que pueden gestionar trabajadores, validar permisos, subir nóminas, ¡y mucho más!

Un panel para la empresa.  
Un panel para el trabajador.  
Todo conectado. Todo sincronizado.

---

## Tecnologías usadas

- **Backend** → Java + Spring Boot  
- **Frontend** → Angular 
- **Base de datos** → MySQL  
- **Autenticación** → Spring Security + JWT  
- **Documentación API** → Swagger/OpenAPI  
- **Herramientas** → IntelliJ IDEA + WebStorm

---

## Cómo ejecutarlo

1. Clona este repositorio:
 ```bash   
 git clone https://github.com/juanjochp/TimeBee.git
 ```  
2. Levanta el backend:
   Entra al directorio backend:   
 ```bash
 cd TimeBee-backend
 ```
  Configura tu application.properties o .yml con la base de datos y otros datos sensibles.
  Arranca Spring Boot desde IntelliJ (o con):   
  ```bash
  mvn spring-boot:run
  ```
3. Levanta el frontend:
  Entra al directorio frontend:
  ```bash 
  cd TimeBee-frontend
  ```
  Instala las dependencias:
  ```bash
  npm install 
  ```
  Lanza Angular:
  ```bash
  ng serve
  ```
¡Y listo! Abre tu navegador en http://localhost:4200 y empieza a explorar.

## Licencia
Este proyecto está protegido bajo licencia CC BY-ND 4.0.
Por favor, consulta el archivo LICENSE para más detalles.

## Contacto
¿Tienes dudas, ideas o simplemente quieres saludar?
Puedes abrir un issue aquí mismo en GitHub o contactarme directamente.
¡Estoy encantado de saber que alguien más se interesa por TimeBee! 
