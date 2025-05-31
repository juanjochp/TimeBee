/**
 * Componente raíz AppComponent
 *
 * Este es el componente principal de la aplicación Angular.
 * Sirve como punto de entrada de la estructura visual y contiene
 * el contenedor donde se inyectan las rutas y componentes hijos.
 */
import { Component } from '@angular/core';

@Component({
  /**
   * Selector del componente, utilizado como etiqueta HTML principal en index.html.
   */
  selector: 'app-root',
  /**
   * Ruta al archivo de plantilla HTML asociado.
   */
  templateUrl: './app.component.html',
  /**
   * Indica que no es un componente standalone (forma parte de un módulo).
   */
  standalone: false,
  /**
   * Ruta al archivo de estilos SCSS asociado.
   */
  styleUrl: './app.component.scss'
})
export class AppComponent {


}
