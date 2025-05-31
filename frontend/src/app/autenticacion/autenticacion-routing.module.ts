import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AltaEmpresaComponent } from './alta-empresa/alta-empresa.component';

/**
 * Configuración de rutas para el módulo de autenticación.
 * <p>
 * Define las rutas específicas que gestionan el acceso
 * a los componentes relacionados con el login
 * y el alta de nuevas empresas.
 */
const routes: Routes = [
  /**
   * Ruta raíz ('').
   * Al acceder a este módulo, carga el componente de login.
   */
  { path: '',      component: LoginComponent },
  /**
   * Ruta '/alta'.
   * Al acceder a esta ruta, se muestra el componente
   * para registrar una nueva empresa.
   */
  { path: 'alta',  component: AltaEmpresaComponent },
];

@NgModule({
  /**
   * Importa y configura las rutas hijas de este módulo.
   * Usa RouterModule.forChild, ya que forma parte de un módulo específico.
   */
  imports: [ RouterModule.forChild(routes) ],

  /**
   * Exporta RouterModule para que las rutas definidas
   * puedan ser usadas por otros módulos.
   * Este paso es esencial para que el enrutamiento funcione correctamente.
   */
  exports: [ RouterModule ]
})
export class AuthRoutingModule { }
