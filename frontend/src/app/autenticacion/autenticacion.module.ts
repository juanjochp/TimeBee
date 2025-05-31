import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { AltaEmpresaComponent } from './alta-empresa/alta-empresa.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyBootstrapModule } from '@ngx-formly/bootstrap';
import { AuthRoutingModule } from './autenticacion-routing.module';

/**
 * Módulo de autenticación.
 * <p>
 * Este módulo agrupa todos los componentes y configuraciones
 * relacionadas con la autenticación y registro de usuarios,
 * incluyendo el login y el alta de nuevas empresas.
 */
@NgModule({
  /**
   * Declaraciones del módulo.
   * Incluye los componentes que pertenecen a este módulo:
   * - LoginComponent: componente de inicio de sesión.
   * - AltaEmpresaComponent: componente para registrar nuevas empresas.
   */
  declarations: [
    LoginComponent,
    AltaEmpresaComponent
  ],
  /**
   * Importaciones necesarias para este módulo.
   * Aquí se incluyen:
   * - CommonModule: utilidades comunes de Angular.
   * - FormsModule y ReactiveFormsModule: manejo de formularios.
   * - FormlyModule y FormlyBootstrapModule: construcción dinámica de formularios.
   * - AuthRoutingModule: configuración de rutas específicas de autenticación.
   */
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FormlyModule,
    FormlyBootstrapModule,
    AuthRoutingModule
  ]
})
export class AutenticacionModule { }
