/**
 * Módulo principal para la gestión de la empresa.
 *
 * Este módulo agrupa los componentes, rutas y dependencias
 * relacionados con la vista y funcionalidades de empresa:
 * dashboard, trabajadores, permisos, fichajes, etc.
 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpresaDashboardComponent } from './empresa-dashboard/empresa-dashboard.component';
import { NavbarEmpresaComponent } from './navbar-empresa/navbar-empresa.component';
import {EmpresaRoutingModule} from './empresa-routing.module';
import { DashboardTrabajadoresComponent } from './dashboard-trabajadores/dashboard-trabajadores.component';
import { DashboardPermisosComponent } from './dashboard-permisos/dashboard-permisos.component';
import { DashboardFichajesComponent } from './dashboard-fichajes/dashboard-fichajes.component';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import {MatSnackBarModule} from "@angular/material/snack-bar";

/**
 * Declaración del módulo EmpresaModule.
 * Aquí se registran los componentes y módulos necesarios
 * para las funcionalidades de empresa.
 */
@NgModule({
  declarations: [
    /**
     * Componente principal del dashboard de empresa.
     */
    EmpresaDashboardComponent,

  ],
  imports: [
    /**
     * CommonModule es necesario para usar directivas comunes como *ngIf, *ngFor.
     */
    CommonModule,

    /**
     * Módulo de enrutamiento específico para empresa.
     */
    EmpresaRoutingModule,

    /**
     * Navbar superior específico para empresa.
     */
    NavbarEmpresaComponent,

    /**
     * Dashboard que gestiona la vista de trabajadores.
     */
    DashboardTrabajadoresComponent,

    /**
     * Dashboard que gestiona la vista de permisos.
     */
    DashboardPermisosComponent,

    /**
     * Dashboard que gestiona la vista de fichajes.
     */
    DashboardFichajesComponent,

    /**
     * Módulo de paginación de ngx-bootstrap.
     */
    PaginationModule.forRoot(),

    /**
     * Módulo de notificaciones tipo snackbar de Angular Material.
     */
    MatSnackBarModule
  ]
})
export class EmpresaModule { }
