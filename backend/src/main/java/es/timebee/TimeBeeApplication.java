package es.timebee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación TimeBee.
 * <p>
 * Este es el punto de entrada del proyecto Spring Boot.
 * Al ejecutarse, inicializa todo el contexto de la aplicación,
 * arranca los componentes, configura las dependencias,
 * y, en resumen, pone en marcha toda la maquinaria.
 */
@SpringBootApplication
public class TimeBeeApplication {

	/**
	 * Método main que lanza la aplicación.
	 * <p>
	 * Aquí empieza todo. Al ejecutarse,
	 * Spring Boot se encarga de levantar el contexto,
	 * buscar beans, inyectar dependencias y arrancar los servicios.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(TimeBeeApplication.class, args);
	}

}
