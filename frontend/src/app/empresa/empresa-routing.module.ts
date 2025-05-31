/**
 * Módulo de enrutamiento para el área de empresa.
 *
 * Define las rutas hijas específicas para las vistas de empresa.
 */
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {EmpresaDashboardComponent} from './empresa-dashboard/empresa-dashboard.component';

/**
 * Declaración de las rutas:
 * - path: '' → carga el componente principal del dashboard de empresa.
 */
const routes: Routes = [
  { path: '',      component: EmpresaDashboardComponent },
];

/**
 * EmpresaRoutingModule
 *
 * Este módulo configura e importa las rutas definidas para el área de empresa.
 * Luego las exporta para que puedan ser utilizadas por otros módulos.
 */
@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class EmpresaRoutingModule { }
