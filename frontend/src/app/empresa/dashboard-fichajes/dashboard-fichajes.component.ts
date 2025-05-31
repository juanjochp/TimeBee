import { Component, OnInit, ViewChild} from '@angular/core';
import {
  EmpresasService,
  FichajeEditRequestDto,
  FichajeRequestDto,
  FichajesDto,
  FichajesService
} from "../../../openapi";
import {NgForOf, NgIf} from "@angular/common";
import {NgxPaginationModule} from "ngx-pagination";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FormlyFieldConfig, FormlyModule} from "@ngx-formly/core";
import {ModalComponent} from "../../core/modal/modal.component";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

/**
 * @Component
 * Componente del dashboard de fichajes.
 * <p>
 * Este componente permite listar, buscar, crear, editar y eliminar fichajes
 * para una empresa, mostrando la información en una vista paginada y gestionando
 * los formularios a través de modales y Formly.
 */
@Component({
  selector: 'app-dashboard-fichajes',
  standalone: true,
  templateUrl: './dashboard-fichajes.component.html',
  imports: [
    NgForOf,
    NgIf,
    NgxPaginationModule,
    FormsModule,
    FormlyModule,
    ModalComponent,
    ReactiveFormsModule
  ],
  styleUrl: './dashboard-fichajes.component.scss'
})
export class DashboardFichajesComponent implements OnInit {

  constructor(private empresasService: EmpresasService, private fichajesService: FichajesService,  private snackBar: MatSnackBar) { }

  /**
   * Lista filtrada de fichajes.
   */
  fichajes: FichajeView[] = [];

  /**
   * Lista completa de fichajes cargados.
   */
  allFichajes: FichajeView[] = [];

  /**
   * Método de inicialización del componente.
   */
  ngOnInit(): void {
    this.cargarFichajes();
  }

  /**
   * Carga todos los fichajes y los procesa para visualización.
   */
  private cargarFichajes() {
    this.empresasService.fichajesPorEmpresa().subscribe({
      next: response => {
        const datos = response.data ?? [];

        datos.sort((a, b) =>
            new Date(b.fechaInicio!).getTime() -
            new Date(a.fechaInicio!).getTime()
        );

        this.fichajes = datos.map(f => {
          const inicio = new Date(f.fechaInicio!);
          const fin    = f.fechaFin ? new Date(f.fechaFin) : new Date();
          const fmt    = (d: Date) =>
              `${d.getDate().toString().padStart(2,'0')}/${
                  (d.getMonth()+1).toString().padStart(2,'0')}/${
                  d.getFullYear()} ${
                  d.getHours().toString().padStart(2,'0')}:${
                  d.getMinutes().toString().padStart(2,'0')}`;
          let diff = fin.getTime() - inicio.getTime();
          const hrs = Math.floor(diff / 36e5);
          diff %= 36e5;
          const mins = Math.floor(diff / 6e4);

          return {
            ...f,
            inicioFmt: fmt(inicio),
            finFmt:    f.fechaFin ? fmt(fin) : '—',
            tiempoTrabajado: `${hrs} h ${mins.toString().padStart(2,'0')} m`,
          };
        });

        this.allFichajes = [...this.fichajes];
      },
      error: () => this.snackbarnok('No se pudieron recuperar los fichajes'),
    });
  }

  /**
   * Referencia al modal de eliminación.
   */
  @ViewChild('modalEliminar') modalEliminar!: ModalComponent;

  /**
   * Formulario para eliminar fichajes.
   */
  eliminarForm = new FormGroup({});

  /**
   * Modelo para eliminar fichajes.
   */
  eliminarModel: any = {};

  /**
   * Campos del formulario de eliminación.
   */
  eliminarFields: FormlyFieldConfig[] = [
    {
      key: 'email',
      type: 'input',
      props: {
        type:'hidden'
      }
    }
  ];

  /**
   * ID actual del fichaje seleccionado.
   */
  currentId!:number;

  /**
   * Abre el modal de confirmación de eliminación.
   * @param fichaje Fichaje seleccionado.
   */
  abrirModalEliminar(fichaje: FichajesDto) {
    this.currentId = fichaje.id!;
    this.modalEliminar.open();
  }

  /**
   * Elimina un fichaje usando el servicio.
   */
  eliminarFichaje() {
    this.fichajesService.eliminarFichaje({id: this.currentId}).subscribe({
      next: (result) => {
        this.cargarFichajes();
        this.modalEliminar.close();
        this.snackbarok('Fichaje eliminado');
      },
      error: (err) => {
        this.snackbarnok('Hubo un error al eliminar el fichaje');
      }

    })
  }

  /**
   * Referencia al modal de edición.
   */
  @ViewChild('modalEditar') modalEditar!: ModalComponent;

  /**
   * Formulario de edición.
   */
  editarForm = new FormGroup({});

  /**
   * Modelo de edición.
   */
  editarModel: any = {};

  /**
   * Campos del formulario de edición.
   */
  editarFields: FormlyFieldConfig[] = [
    {
      key: 'id',
      type: 'input',
      props: {
        type:'hidden'
      }
    },
      {
      key: 'trabajadorId',
      type: 'input',
      props: {
        type:'hidden'
      }
    },
      {
      key: 'empresaId',
      type: 'input',
      props: {
        type:'hidden'
      }
    },
    {
      key: 'fechaInicio',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Inicio',
        type: 'datetime-local',
        required: true,
        hideRequiredMarker: true
      },
      validators: {
        dateOrderStart: {
          expression: (c: FormControl) => {
            const form = c.parent;
            if (!form) { return true; }
            const start = c.value;
            const end = form.get('fechaFin')?.value;
            return !start || !end || new Date(start) <= new Date(end);
          },
        },
      },
    },
    {
      key: 'fechaFin',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fin',
        type: 'datetime-local',
      },
      validators: {
        dateOrder: {
          expression: (c: FormControl) => {
            const form  = c.parent;
            if (!form)                     return true;
            const start = form.get('fechaInicio')?.value;
            const end   = c.value;
            return !start || !end || new Date(end) >= new Date(start);
          },
        },
      },
    },
  ];

  /**
   * Abre el modal de edición de fichaje.
   * @param fichaje Fichaje a editar.
   */
  abrirModalEditar(fichaje: FichajesDto) {
    this.currentId = fichaje.id!;
    this.editarModel = fichaje;
    this.editarForm.patchValue(this.editarModel);
    this.modalEditar.open();
  }

  /**
   * Edita un fichaje usando el servicio.
   */
  editarFichaje() {
    let request:FichajeEditRequestDto = {
      fechaInicio: this.editarModel.fechaInicio!,
      fechaFin: this.editarModel.fechaFin?? null,
      trabajadorId: this.editarModel.trabajador.id,
      empresaId: this.editarModel.empresa.id,
      id:this.editarModel.id
    };

    request.fechaInicio += this.editarModel.fechaInicio.length === 16 ? ':00' : '';
    if(this.editarModel.fechaFin){
      request.fechaFin += this.editarModel.fechaFin.length === 16 ? ':00' : '';
    }


    this.fichajesService.editarFichaje(request).subscribe({
      next: (result) => {
        this.cargarFichajes();
        this.modalEditar.close();
        this.snackbarok('Fichaje actualizado');
      },
      error: (err) => {
        this.snackbarnok('Hubo un error al actualizar el fichaje');
      }
    })
  }

  /**
   * Referencia al modal de creación.
   */
  @ViewChild('modalNuevo') modalNuevo!: ModalComponent;

  /**
   * Formulario para nuevo fichaje.
   */
  nuevoForm = new FormGroup({});

  /**
   * Modelo para nuevo fichaje.
   */
  nuevoModel: any = {};

  /**
   * Campos del formulario para nuevo fichaje.
   */
  nuevoFields: FormlyFieldConfig[] = [
    {
      key: 'id',
      type: 'input',
      props: {
        type:'hidden'
      }
    },
    {
      key: 'trabajadorId',
      type: 'select',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Trabajadores',
        placeholder: 'Seleccione uno…',
        options: [],
        searchEnabled: true,
        required: true,
        hideRequiredMarker: true
      },
      hooks: {
        onInit: field => {
          this.empresasService.trabajadoresPorEmpresa().subscribe({
            next: response => {
              field.props!.options = response.data!.map(item => ({
                label: item.nombre + " " + item.apellidos,
                value: item.id,
              }));
            }
          })
        }
      }
    },
    {
      key: 'empresaId',
      type: 'input',
      props: {
        type:'hidden'
      }
    },
    {
      key: 'fechaInicio',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Inicio',
        type: 'datetime-local',
        required: true,
        hideRequiredMarker: true
      },
      validators: {
        dateOrderStart: {
          expression: (c: FormControl) => {
            const form = c.parent;
            if (!form) { return true; }
            const start = c.value;
            const end = form.get('fechaFin')?.value;
            return !start || !end || new Date(start) <= new Date(end);
          },
        },
        notFutureDate: {
          expression: (c: FormControl) => {
            const start = c.value;
            if (!start) { return true; }
            return new Date(start) <= new Date();
          },
          message: 'La fecha de inicio no puede ser futura',
        },
      },
    },
    {
      key: 'fechaFin',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fin',
        type: 'datetime-local',
      },
      validators: {
        dateOrder: {
          expression: (c: FormControl) => {
            const form  = c.parent;
            if (!form)                     return true;
            const start = form.get('fechaInicio')?.value;
            const end   = c.value;
            return !start || !end || new Date(end) >= new Date(start);
          },

        },
      },
    },
  ];

  /**
   * Abre el modal para crear un nuevo fichaje.
   */
  abrirModalNuevo() {
    this.nuevoForm.reset();
    this.modalNuevo.open();
  }

  /**
   * Crea un nuevo fichaje usando el servicio.
   */
  nuevoFichaje() {
    let request:FichajeRequestDto = {
      fechaInicio: this.nuevoModel.fechaInicio!,
      fechaFin: this.nuevoModel.fechaFin ?? null,
      trabajadorId: this.nuevoModel.trabajadorId!,
      empresaId: 0,
    };

    request.fechaInicio += this.nuevoModel.fechaInicio.length === 16 ? ':00' : '';
    if (request.fechaFin) {
      request.fechaFin += this.nuevoModel.fechaFin   .length === 16 ? ':00' : '';
    }
    this.fichajesService.crearFichaje(request).subscribe({
      next: (result) => {
        this.cargarFichajes();
        this.modalNuevo.close()
        this.snackbarok('Fichaje creado');
      },
      error: (err) => {
        this.snackbarnok('Hubo un error al crear el fichaje');
      }
    })
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
   * Formulario de filtro de búsqueda.
   */
  filterForm = new FormGroup({});

  /**
   * Modelo del filtro de búsqueda.
   */
  filterModel: any = {};

  /**
   * Campos del formulario de búsqueda.
   */
  filterFields: FormlyFieldConfig[] = [
    {
      key: 'query',
      type: 'input',
      props: {
        type:'text',
        placeholder: 'Buscar fichaje...',
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
              this.onSearch();
            });
        },
      },
    }
  ];

  /**
   * Realiza la búsqueda de fichajes según el texto ingresado.
   */
  onSearch() {
    const q = (this.filterModel.query ?? '').toString().trim().toLowerCase();
    if (!q) {
      this.fichajes = [...this.allFichajes];
      return;
    }
    this.fichajes = this.allFichajes.filter(f =>
        [
          f.trabajador?.nombre,
          f.trabajador?.apellidos,
          f.inicioFmt,
          f.finFmt,
          f.tiempoTrabajado,
        ]
            .filter(v => !!v)
            .some(v => v!.toString().toLowerCase().includes(q))
    );
  }
}

/**
 * Interfaz extendida de FichajesDto para la vista.
 */
interface FichajeView extends FichajesDto {
  inicioFmt: string;
  finFmt: string;
  tiempoTrabajado: string;
}