/**
 * Módulo TrabajadorModule
 *
 * Este módulo agrupa todos los componentes, rutas y dependencias necesarias
 * para gestionar la vista del trabajador, incluyendo dashboard, fichajes,
 * permisos, nóminas y navegación.
 */
import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { TrabajadorDashboardComponent } from './trabajador-dashboard/trabajador-dashboard.component';
import {TrabajadorRoutingModule} from "./trabajador-routing.module";
import { NavbarTrabajadorComponent } from './navbar-trabajador/navbar-trabajador.component';
import {FormlyModule} from "@ngx-formly/core";
import {ModalComponent} from "../core/modal/modal.component";
import { DashboardFichajesTrabajadorComponent } from './dashboard-fichajes-trabajador/dashboard-fichajes-trabajador.component';
import { DashboardPermisosTrabajadorComponent } from './dashboard-permisos-trabajador/dashboard-permisos-trabajador.component';
import { DashboardNominasTrabajadorComponent } from './dashboard-nominas-trabajador/dashboard-nominas-trabajador.component';

@NgModule({
  /**
   * Declaración de los componentes propios del módulo del trabajador.
   */
  declarations: [
    TrabajadorDashboardComponent,
  ],
  /**
   * Importación de módulos necesarios:
   * - CommonModule: funcionalidades básicas de Angular.
   * - TrabajadorRoutingModule: rutas específicas del módulo.
   * - FormlyModule: formularios dinámicos.
   * - ModalComponent: modales reutilizables.
   * - NgOptimizedImage: optimización de imágenes.
   * - NavbarTrabajadorComponent: barra superior específica del trabajador.
   * - DashboardFichajesTrabajadorComponent: dashboard de fichajes.
   * - DashboardPermisosTrabajadorComponent: dashboard de permisos.
   * - DashboardNominasTrabajadorComponent: dashboard de nóminas.
   */
  imports: [
    CommonModule,
    TrabajadorRoutingModule,
    FormlyModule,
    ModalComponent,
    NgOptimizedImage,
    NavbarTrabajadorComponent,
    DashboardFichajesTrabajadorComponent,
    DashboardPermisosTrabajadorComponent,
    DashboardNominasTrabajadorComponent
  ]
})
export class TrabajadorModule { }
