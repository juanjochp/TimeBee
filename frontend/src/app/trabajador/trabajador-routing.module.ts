/**
 * Módulo de enrutamiento TrabajadorRoutingModule
 *
 * Este módulo define las rutas específicas para el área del trabajador dentro de la aplicación.
 */
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {TrabajadorDashboardComponent} from "./trabajador-dashboard/trabajador-dashboard.component";

/**
 * Definición de rutas:
 * - Ruta raíz ('') que carga el componente TrabajadorDashboardComponent.
 */
const routes: Routes = [
  { path: '', component: TrabajadorDashboardComponent },
];

@NgModule({
  /**
   * Importa RouterModule configurado con las rutas definidas para el trabajador.
   */
  imports: [ RouterModule.forChild(routes) ],
  /**
   * Exporta RouterModule para que esté disponible en el resto del módulo TrabajadorModule.
   */
  exports: [ RouterModule ]
})
export class TrabajadorRoutingModule { }
