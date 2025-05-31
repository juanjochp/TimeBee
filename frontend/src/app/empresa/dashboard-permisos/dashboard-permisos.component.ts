import {Component, OnInit} from '@angular/core';
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {PermisosService, TrabajadorDto} from "../../../openapi";
import {FormlyFieldConfig, FormlyModule} from "@ngx-formly/core";
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {FormlyBootstrapModule} from "@ngx-formly/bootstrap";
import {MatSnackBar} from "@angular/material/snack-bar";
import {formatDate, formatHours, humanizeEnum} from "../../core/library/library";

/**
 * Componente para gestionar el dashboard de permisos.
 */
@Component({
  selector: 'app-dashboard-permisos',
  standalone: true,
  templateUrl: './dashboard-permisos.component.html',
  imports: [
    NgIf,
    NgClass,
    NgForOf,
    FormlyModule,
    ReactiveFormsModule,
    FormlyBootstrapModule
  ],
  styleUrl: './dashboard-permisos.component.scss'
})
export class DashboardPermisosComponent implements OnInit {

  /**
   * Constructor del componente.
   * @param permisosService Servicio para gestionar permisos.
   * @param snackBar Servicio para mostrar notificaciones.
   */
  constructor(private permisosService: PermisosService, private snackBar: MatSnackBar) {
  }

  /**
   * Indica si se muestran los permisos pendientes.
   */
  verPendientes:boolean = true;

  /**
   * Alterna entre la vista de permisos pendientes y resueltos.
   */
  alternarVista() {
    this.verPendientes = !this.verPendientes;
    this.filterModel = {};
    this.permisosSolicitadosFiltrados = [...this.permisosSolicitados];
    this.permisosResueltosFiltrados = [...this.permisosResueltos];
  }

  /**
   * Lista de permisos solicitados.
   */
  permisosSolicitados: PermisosHumanizeDto[] = [];

  /**
   * Lista de permisos ya resueltos (aprobados/rechazados).
   */
  permisosResueltos: PermisosHumanizeDto[] = [];

  /**
   * Método de inicialización del componente.
   */
  ngOnInit(): void {
    this.cargarPermisos();
  }

  /**
   * Aprueba un permiso específico.
   * @param permiso Permiso a aprobar.
   */
  aprobarPermiso(permiso: PermisosHumanizeDto) {
    this.permisosService.validar({id:permiso.id!,estado:'APROBADO'}).subscribe({
      next: data => {
        this.cargarPermisos();
        this.snackbarok('Permiso aprobado');
      },
      error: err => {
        this.snackbarnok('Hubo un error al aprobar el permiso');
      }
    })
  }

  /**
   * Rechaza un permiso específico.
   * @param permiso Permiso a rechazar.
   */
  rechazarPermiso(permiso: PermisosHumanizeDto) {
    this.permisosService.validar({id:permiso.id!,estado:'RECHAZADO'}).subscribe({
      next: data => {
        this.cargarPermisos();
        this.snackbarok('Permiso rechazado');
      },
      error: err => {
        this.snackbarnok('Hubo un error al rechazar el permiso');
      }
    })
  }

  /**
   * Carga todos los permisos desde el backend.
   */
  private cargarPermisos() {
    this.permisosService.permisosPorEmpresaId().subscribe({
      next: data => {
        const permisos = data.data ?? [];

        this.permisosSolicitados = permisos
            .filter(p => p.estado === 'SOLICITADO')
            .sort((a, b) => new Date(a.fecha!).getTime() - new Date(b.fecha!).getTime())

        this.permisosSolicitadosFiltrados = [...this.permisosSolicitados];

        this.permisosResueltos = permisos
            .filter(p => p.estado === 'APROBADO' || p.estado === 'RECHAZADO')
            .sort((a, b) => new Date(b.fecha!).getTime() - new Date(a.fecha!).getTime())

        this.permisosResueltosFiltrados = [...this.permisosResueltos];
      },
      error: err => {
        this.snackbarnok('Hubo un error al cargar los permisos');
      }
    });
  }

  /**
   * Formulario para búsqueda de permisos.
   */
  filterForm = new FormGroup({});

  /**
   * Modelo para búsqueda de permisos.
   */
  filterModel: any = {};

  /**
   * Campos del formulario de búsqueda.
   */
  filterFields: FormlyFieldConfig[] = [
    {
      key: 'querypermisos',
      type: 'input',
      props: {
        type:'text',
        placeholder: 'Buscar permisos...',
        attributes:{
          class:'form-control form-control-sm w-100',
        }
      },
      hooks: {
        onInit: (field) => {
          field.formControl!.valueChanges
              .pipe(
                  debounceTime(200),
                  distinctUntilChanged(),
              )
              .subscribe(value => {
                this.filtrarPermisos();
              });
        },
      },
    }
  ];

  /**
   * Lista filtrada de permisos resueltos.
   */
  permisosResueltosFiltrados: PermisosHumanizeDto[] = [];

  /**
   * Lista filtrada de permisos solicitados.
   */
  permisosSolicitadosFiltrados: PermisosHumanizeDto[] = [];

  /**
   * Filtra los permisos según el texto de búsqueda.
   */
  filtrarPermisos() {
    const query = (this.filterModel.querypermisos ?? '').toString().toLowerCase().trim();
    if (!query) {
      this.permisosResueltosFiltrados = [...this.permisosResueltos];
      this.permisosSolicitadosFiltrados = [...this.permisosSolicitados]
      return;
    }

    if (this.verPendientes) {
      this.permisosSolicitadosFiltrados = this.permisosSolicitados.filter(p => {
        const campos = [
          p.permiso,
          p.estado,
          p.fecha,
          p.hora,
          p.trabajador?.nombre,
          p.trabajador?.apellidos,
          p.trabajador?.dni,
          p.trabajador?.email,
          p.trabajador?.categoria
        ].filter(v => v != null) as any[];

        return campos.some(c => c.toString().toLowerCase().includes(query));
      });
    } else {
      this.permisosResueltosFiltrados = this.permisosResueltos.filter(p => {
        const campos = [
          p.permiso,
          p.estado,
          p.fecha,
          p.hora,
          p.trabajador?.nombre,
          p.trabajador?.apellidos,
          p.trabajador?.dni,
          p.trabajador?.email,
          p.trabajador?.categoria
        ].filter(v => v != null) as any[];

        return campos.some(c => c.toString().toLowerCase().includes(query));
      });
    }
  }

  /**
   * Muestra un snackbar de éxito.
   * @param mensaje Mensaje a mostrar.
   */
  snackbarok(mensaje:string){
    this.snackBar.open(
        mensaje,
        '',
        {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'bottom',
          panelClass: ['snackbar-success'],
        }
    );
  }

  /**
   * Muestra un snackbar de error.
   * @param mensaje Mensaje a mostrar.
   */
  snackbarnok(mensaje:string){
    this.snackBar.open(
        mensaje,
        '',
        {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'bottom',
          panelClass: ['snackbar-error'],
        }
    );
  }

  /**
   * Referencia al helper humanizeEnum.
   */
  protected readonly humanizeEnum = humanizeEnum;

  /**
   * Referencia al helper formatDate.
   */
  protected readonly formatDate = formatDate;

  /**
   * Referencia al helper formatHours.
   */
  protected readonly formatHours = formatHours;
}

/**
 * Interfaz para representar permisos con datos enriquecidos.
 */
export interface PermisosHumanizeDto {
  /**
   * Identificador del permiso.
   */
  id?: number
  /**
   * Tipo de permiso.
   */
  permiso?: string
  /**
   * Datos del trabajador.
   */
  trabajador?: TrabajadorDto
  /**
   * Fecha del permiso.
   */
  fecha?: string
  /**
   * Hora del permiso.
   */
  hora?: number
  /**
   * Estado del permiso.
   */
  estado?: string
}
