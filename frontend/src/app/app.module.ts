/**
 * Módulo principal AppModule
 *
 * Este módulo centraliza la configuración e inicialización
 * de la aplicación Angular, incluyendo los módulos de terceros,
 * interceptores, servicios y componentes principales.
 */
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ApiModule, Configuration} from '../openapi';
import {AuthInterceptor} from './core/interceptors/auth.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './core/page-not-found/page-not-found.component';
import {ReactiveFormsModule} from '@angular/forms';
import {FormlyModule} from '@ngx-formly/core';
import {FormlyBootstrapModule} from '@ngx-formly/bootstrap';
import { ModalComponent } from './core/modal/modal.component';
import {FormlyHorizontalWrapper} from './core/horizontal-wrapper/horizontal-wrapper.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgSelectModule} from "@ng-select/ng-select";

@NgModule({
  /**
   * Declaraciones de componentes del módulo, incluyendo:
   * - AppComponent: componente raíz.
   * - PageNotFoundComponent: página para rutas no encontradas.
   */
  declarations: [
    AppComponent,
    PageNotFoundComponent,
  ],
  /**
   * Módulos importados necesarios para el funcionamiento global de la app:
   * - BrowserModule: módulo necesario para apps web.
   * - HttpClientModule: cliente HTTP para consumir APIs.
   * - ApiModule: módulo generado por OpenAPI, configurado con basePath.
   * - AppRoutingModule: rutas principales de la app.
   * - ReactiveFormsModule: soporte para formularios reactivos.
   * - FormlyBootstrapModule y FormlyModule: motor dinámico de formularios.
   * - ModalComponent: componente de modal reutilizable.
   * - BrowserAnimationsModule: soporte para animaciones.
   * - NgSelectModule: componente select avanzado.
   */
  imports: [
    BrowserModule,
    HttpClientModule,
    ApiModule.forRoot(() => new Configuration({
      basePath: 'http://localhost:8080'
    })),
    AppRoutingModule,
    ReactiveFormsModule,
    FormlyBootstrapModule,
    ModalComponent,
    FormlyModule.forRoot({
      wrappers: [{name: 'form-field-horizontal', component: FormlyHorizontalWrapper}],
    }),
    BrowserAnimationsModule,
    NgSelectModule,

    ],
  /**
   * Proveedores globales, incluyendo:
   * - HTTP_INTERCEPTORS: se usa AuthInterceptor para añadir cabeceras Authorization.
   */
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  /**
   * Componente raíz que se inicializa al arrancar la aplicación.
   */
  bootstrap: [AppComponent]
})
export class AppModule { }

