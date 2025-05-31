/**
 * Módulo de enrutamiento principal AppRoutingModule
 *
 * Este módulo define las rutas principales de la aplicación,
 * utilizando lazy loading para cargar módulos de manera diferida.
 */
import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

/**
 * Rutas principales de la aplicación:
 * - path '': módulo de autenticación.
 * - path 'empresa': módulo para el dashboard de empresas.
 * - path 'trabajador': módulo para el dashboard de trabajadores.
 * - path '**': redirección a la raíz para rutas no encontradas.
 */
const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./autenticacion/autenticacion.module')
        .then(m => m.AutenticacionModule)
  },
  {
    path: 'empresa',
    loadChildren: () => import('./empresa/empresa.module').then(m => m.EmpresaModule)
  },
  {
    path: 'trabajador',
    loadChildren: () => import('./trabajador/trabajador.module').then(m => m.TrabajadorModule)
  },
  { path: '**', redirectTo: '' },
];

/**
 * Decorador NgModule para configurar el módulo de rutas:
 * - imports: se cargan las rutas con RouterModule.forRoot().
 * - exports: se exporta RouterModule para usarlo en toda la app.
 */
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
