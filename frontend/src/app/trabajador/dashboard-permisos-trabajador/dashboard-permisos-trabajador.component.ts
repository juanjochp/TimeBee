/**
 * Componente DashboardPermisosTrabajadorComponent
 *
 * Este componente permite a un trabajador consultar, solicitar,
 * editar y eliminar permisos laborales. Incluye filtros, formularios,
 * modales y validaciones específicas.
 */
import {Component, OnInit, ViewChild} from '@angular/core';
import {PermisosDto, PermisosService, TipoPermisoDto} from "../../../openapi";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NgForOf, NgIf} from "@angular/common";
import {FormlyFieldConfig, FormlyModule} from "@ngx-formly/core";
import {AbstractControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormlyBootstrapModule} from "@ngx-formly/bootstrap";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {ModalComponent} from "../../core/modal/modal.component";
import {formatDate, formatHours, humanizeEnum} from "../../core/library/library";

@Component({
  selector: 'app-dashboard-permisos-trabajador',
  standalone: true,
  templateUrl: './dashboard-permisos-trabajador.component.html',
  imports: [
    NgIf,
    NgForOf,
    FormlyModule,
    ReactiveFormsModule,
    FormlyBootstrapModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ModalComponent,
  ],
  styleUrl: './dashboard-permisos-trabajador.component.scss'
})
export class DashboardPermisosTrabajadorComponent implements OnInit {

  /**
   * Exposición pública del helper para formatear enums.
   */
  public humanizeEnum = humanizeEnum;

  /**
   * Lista de tipos de permiso disponibles.
   */
  tipos: TipoPermisoDto[] = [];

  /**
   * Lista filtrada de permisos mostrados.
   */
  permisos: PermisosDto[] = [];

  /**
   * Lista completa de permisos, sin filtros.
   */
  allPermisos: PermisosDto[] = [];

  /**
   * Formulario de filtro para buscar permisos.
   */
  filterForm = new FormGroup({});

  /**
   * Modelo del formulario de filtro.
   */
  filterModel: any = {};

  /**
   * Configuración de los campos del formulario de filtro.
   */
  filterFields: FormlyFieldConfig[] = [
    {
      key: 'querypermisos',
      type: 'input',
      props: {
        type:'text',
        placeholder: 'Buscar permisos...',
        attributes:{
          class:'form-control form-control-sm mt-3 mb-0',
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
              this.applyFilter();
            });
        },
      },
    }
  ];

  /**
   * Constructor del componente que inyecta servicios.
   *
   * @param permisosService Servicio para permisos.
   * @param snackBar Servicio para mensajes snackbar.
   */
  constructor(private permisosService: PermisosService, private snackBar: MatSnackBar) {}

  /**
   * Hook de inicialización.
   * Carga tipos de permiso y permisos actuales.
   */
  ngOnInit():void {
    this.permisosService.listarTiposPermiso().subscribe({
      next: tipos => {
        this.tipos = tipos.data!
      },
      error: ()    => this.snackBar.open('Error cargando tipos de permiso', '', { panelClass: ['snackbar-error'] })
    });

    this.loadPermisos();
  }

  /**
   * Carga todos los permisos del trabajador y los ordena.
   */
  private loadPermisos() {
    this.permisosService.permisosPorTrabajadorId().subscribe({
      next: datos => {
        this.allPermisos = datos.data!;
        this.permisos    = [...datos.data!];
        this.permisos.sort((a, b) => {
          if (!a.fecha) return 1;
          if (!b.fecha) return -1;

          const dateA = new Date(a.fecha);
          const dateB = new Date(b.fecha);
          return dateB.getTime() - dateA.getTime();
        });
      },
      error: () => this.snackBar.open('No se pudieron recuperar tus permisos', '', { panelClass: ['snackbar-error'] })
    });
  }

/**
 * Aplica el filtro sobre los permisos según la búsqueda.
 */
private applyFilter() {
    const query = (this.filterModel.querypermisos ?? '').toString().toLowerCase().trim();

    if (!query) {
      this.permisos = [...this.allPermisos];
    } else {
      this.permisos = this.allPermisos.filter(p =>
        p.permiso?.toLowerCase().includes(query) ||
        p.estado?.toLowerCase().includes(query)
      );
    }
  }

  /**
   * Referencia al modal de edición.
   */
  @ViewChild("modalEditar") modalEditar!: ModalComponent;

  /**
   * Formulario de edición.
   */
  editarForm = new FormGroup({});

  /**
   * Modelo del formulario de edición.
   */
  editarModel: any = {};

  /**
   * Campos del formulario de edición.
   */
  editarFields: FormlyFieldConfig[] = [
    {
      key: 'permiso',
      type: 'select',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Tipo de permiso',
        required: true,
        hideRequiredMarker: true,
        options: []
      },
      hooks:{
        onInit: field => {
          this.permisosService.listarTiposPermiso().subscribe({
            next: response => {
              field.props!.options = response.data!.map(item => ({
                label: humanizeEnum(item.nombre!),
                value: item.codigo,
              }));
            }
          })
        }
      }
    },
    {
      key: 'fecha',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        type:'date',
        label: 'Fecha',
        required: true,
        hideRequiredMarker: true,
      },
    },
    {
      key: 'hora',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        type:'number',
        label: 'Horas',
        required: true,
        hideRequiredMarker: true,
        min:0.25,
        max:8,
        step: 0.25,
        placeholder:'0.25 - 8'
      },
      validation: {
        messages: {
          min: 'No puede ser menor a 0.25 ',
          max: 'No puedes superar más de 8 '
        }
      },
      validators: {
        stepValue: {
          expression: (control: AbstractControl): boolean => {
            const v = control.value as number;
            return v != null && Number.isFinite(v) && Math.round(v * 4) === v * 4;
          },
          message: 'Solo se permiten incrementos de 0.25h'
        }
      }
    }
  ];

  /**
   * Abre el modal de edición para un permiso.
   * @param p Permiso a editar.
   */
  openModalEditar(p: PermisosDto) {
    this.editarModel = {...p};
    this.editarForm.patchValue(this.editarModel);
    this.modalEditar.open();
  }

  /**
   * Guarda los cambios del permiso editado.
   */
  editarPermiso() {
    this.permisosService.editar(this.editarModel).subscribe({
      next: response => {
        this.loadPermisos();
        this.modalEditar.close();
        this.snackbarok("El permiso se ha editado correctamente!")
      },
      error: () => {
        this.snackbarnok("Ha ocurrido un error al actualizar el permiso.")
      }
    })
  }

  /**
   * Referencia al modal de nuevo permiso.
   */
  @ViewChild("modalNuevo") modalNuevo!: ModalComponent;

  /**
   * Formulario para solicitar nuevo permiso.
   */
  nuevoForm = new FormGroup({});

  /**
   * Modelo del formulario nuevo.
   */
  nuevoModel: any = {};

  /**
   * Campos del formulario nuevo.
   */
  nuevoFields: FormlyFieldConfig[] = [
    {
      key: 'permiso',
      type: 'select',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Tipo de permiso',
        required: true,
        hideRequiredMarker: true,
        options: []
      },
      hooks:{
        onInit: field => {
          this.permisosService.listarTiposPermiso().subscribe({
            next: response => {
              field.props!.options = response.data!.map(item => ({
                label: humanizeEnum(item.nombre!),
                value: item.codigo,
              }));
            }
          })
        }
      }
    },
    {
      key: 'fecha',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        type:'date',
        label: 'Fecha',
        required: true,
        hideRequiredMarker: true,
      },
    },
    {
      key: 'hora',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        type:'number',
        label: 'Horas',
        required: true,
        hideRequiredMarker: true,
        min:0.25,
        max:8,
        step: 0.25,
        placeholder:'0.25 - 8'
      },
      validation: {
        messages: {
          min: 'No puede ser menor a 0.25 ',
          max: 'No puedes superar más de 8 '
        }
      },
      validators: {
        stepValue: {
          expression: (control: AbstractControl): boolean => {
            const v = control.value as number;
            return v != null && Number.isFinite(v) && Math.round(v * 4) === v * 4;
          },
          message: 'Solo se permiten incrementos de 0.25h'
        }
      }
    }
  ];

  /**
   * Abre el modal para solicitar un nuevo permiso.
   */
  abrirModalNuevo(){
    this.nuevoForm.reset();
    this.modalNuevo.open();
  }

  /**
   * Envía la solicitud de nuevo permiso.
   */
  nuevoPermiso(){
    this.permisosService.solicitar(this.nuevoModel).subscribe({
      next: response => {
        this.snackbarok("Solicitud enviadad correctamente.");
        this.loadPermisos();
        this.modalNuevo.close();
      },
      error: () => {
        this.snackbarnok("Ha ocurrido un error al solicitar el permiso.");
        this.modalNuevo.close();
      }
    })
  }

  /**
   * Elimina un permiso dado su ID.
   * @param permisoId ID del permiso.
   */
  delete(permisoId: number) {
    this.permisosService.eliminarPermiso({id:permisoId}).subscribe({
      next: response => {
        this.snackbarok("Permiso eliminado correctamente.");
        this.loadPermisos();
      },
      error: () => {
        this.snackbarnok("Ha ocurrido un error al eliminar el permiso.");
      }
    })
  }

  /**
   * Muestra un mensaje snackbar de éxito.
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
   * Muestra un mensaje snackbar de error.
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
   * Referencia a utilidad para formatear fechas.
   */
  protected readonly formatDate = formatDate;

  /**
   * Referencia a utilidad para formatear horas.
   */
  protected readonly formatHours = formatHours;
}
