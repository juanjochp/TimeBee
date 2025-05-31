import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {
  EmpresasService,
  GenerosService, IdRequestDto, NominaMetadataDto,
  NominasService,
  TrabajadorDto
} from '../../../openapi';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {FormlyFieldConfig, FormlyModule} from '@ngx-formly/core';
import {debounceTime, distinctUntilChanged} from 'rxjs';
import {ModalComponent} from '../../core/modal/modal.component';
import { trigger, style, animate, transition,} from '@angular/animations';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {formatDate, humanizeEnum} from "../../core/library/library";

/**
 * Componente que gestiona el dashboard de trabajadores.
 * Permite visualizar, buscar, editar, eliminar y consultar nóminas.
 */
@Component({
  selector: 'app-dashboard-trabajadores',
  standalone: true,
  templateUrl: './dashboard-trabajadores.component.html',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormlyModule,
    ModalComponent,
    NgIf,
    NgClass
  ],
  styleUrl: './dashboard-trabajadores.component.scss',
  animations: [
    trigger('fadeAlert', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms ease-out', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ opacity: 0 })),
      ]),
    ]),
  ],
})
export class DashboardTrabajadoresComponent implements OnInit, AfterViewInit {

  /**
   * Lista visible de trabajadores.
   */
  trabajadores: any[] = [];

  /**
   * Lista completa de trabajadores cargados.
   */
  private allTrabajadores: TrabajadorDto[] = [];

  constructor(private empresaService: EmpresasService, private generoService: GenerosService, private nominaService:NominasService,
              private fb: FormBuilder, private http: HttpClient, private snackBar: MatSnackBar) {
    this.uploadForm = this.fb.group({
      trabajadorId: [null, Validators.required],
      periodo: [null, Validators.required],
      file: [null, Validators.required],
    });
  }

  /**
   * Hook de inicialización. Carga la lista de trabajadores.
   */
  ngOnInit(): void {
    this.cargarTrabajadores();
  }

  /**
   * Carga la lista de trabajadores desde el backend.
   * Ordena por fecha de antigüedad de forma descendente.
   */
  private cargarTrabajadores() {
    this.empresaService.trabajadoresPorEmpresa().subscribe(resp => {
      const data = resp.data ?? [];

      this.allTrabajadores = data.sort((a, b) => {
        const timeA = a.fechaAntiguedad
          ? new Date(a.fechaAntiguedad).getTime()
          : 0;
        const timeB = b.fechaAntiguedad
          ? new Date(b.fechaAntiguedad).getTime()
          : 0;
        return timeB - timeA;
      });

      // Copiamos al array de visualización
      this.trabajadores = [...this.allTrabajadores];
    });
  }

  /**
   * Hook que se ejecuta después de que la vista ha sido inicializada.
   * Configura listeners de búsqueda y scroll horizontal.
   */
  ngAfterViewInit(): void {
    // Ahora sí existe el control 'query'
    const queryControl = this.filterForm.get('query');
    if (queryControl) {
      queryControl.valueChanges
        .pipe(
          debounceTime(200),
          distinctUntilChanged()
        )
        .subscribe(() => this.onSearch());
    }
    this.addScrollListeners();
  }

  /**
   * Elemento del DOM que contiene las tarjetas de trabajadores con scroll horizontal.
   */
  @ViewChild('scrollArea', {static: false}) scrollArea!: ElementRef<HTMLElement>;

  /**
   * Cantidad de píxeles que se desplaza al hacer scroll horizontal.
   */
  private cardStep = 266 * 1;

  /**
   * Intervalo activo para auto-scroll.
   */
  private scrollInterval: any;

  /**
   * Realiza un desplazamiento horizontal en la dirección indicada.
   * @param direction Dirección del desplazamiento (-1 izquierda, 1 derecha).
   */
  scroll(direction: number) {
    this.scrollArea.nativeElement.scrollBy({
      left: direction * this.cardStep,
      behavior: 'smooth'
    });
  }

  /**
   * Comienza un desplazamiento automático repetido.
   * @param direction Dirección del desplazamiento (-1 izquierda, 1 derecha).
   */
  startScroll(direction: number) {
    this.scroll(direction);
    this.scrollInterval = setInterval(() => this.scroll(direction), 150);
  }

  /**
   * Detiene el desplazamiento automático.
   */
  stopScroll() {
    if (this.scrollInterval) {
      clearInterval(this.scrollInterval);
      this.scrollInterval = null;
    }
  }

  /**
   * Formulario reactivo para el filtro de búsqueda.
   */
  filterForm = new FormGroup({});

  /**
   * Modelo de datos para el filtro de búsqueda.
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
        placeholder: 'Buscar trabajador...',
        attributes:{
          class: 'form-control form-control-sm w-100'
        }
      },
    }
  ];

  /**
   * Filtra la lista de trabajadores según el texto ingresado.
   */
  onSearch() {
    const q = (this.filterModel.query ?? '').toString().trim().toLowerCase();
    if (!q) {
      this.trabajadores = [...this.allTrabajadores];
      return;
    }
    this.trabajadores = this.allTrabajadores.filter(t => {
      const campos = [
        t.nombre, t.apellidos, t.dni, t.naf,
        t.categoria, t.email, t.iban,
        t.genero, t.rol,
        t.telefono, t.id
      ].filter(v => v != null) as any[];
      return campos.some(c => c.toString().toLowerCase().includes(q));
    });
  }

  /**
   * Referencia al modal de detalle del trabajador.
   */
  @ViewChild('modalDetalle') modalDetalle!: ModalComponent;

  /**
   * Formulario reactivo para mostrar detalles del trabajador.
   */
  detalleForm = new FormGroup({});

  /**
   * Modelo de datos para los detalles del trabajador.
   */
  detalleModel: any = {};

  /**
   * Campos del formulario de detalle del trabajador.
   */
  detalleFields: FormlyFieldConfig[] = [
    {
      key: 'nombre',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Nombre',
        type:'text',
        disabled: true,
      },
    },
    {
      key: 'apellidos',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Apellidos',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'dni',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'DNI',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'naf',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'NAF',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'generoText',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Género',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'fechaNacimiento',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha Nacimiento',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'fechaAntiguedad',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha Antigüedad',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'categoria',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Categoría',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'iban',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'IBAN',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'telefono',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Teléfono',
        type:'text',
        disabled: true
      }
    },
    {
      key: 'email',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Email',
        type:'text',
        disabled: true
      }
    },

  ];

  /**
   * Abre el modal de detalle con la información formateada del trabajador.
   * @param trabajador Datos del trabajador a mostrar.
   */
  verDetalleTrabajador(trabajador:TrabajadorDto) {
    let trabajadorModal: any = {...trabajador}
    trabajadorModal.generoText = humanizeEnum(trabajador.genero!);
    trabajadorModal.fechaNacimiento = formatDate(trabajador.fechaNacimiento!);
    trabajadorModal.fechaAntiguedad = formatDate(trabajador.fechaAntiguedad!);
    this.detalleModel = trabajadorModal;
    this.modalDetalle.open();
  }

  /**
   * Referencia al modal de confirmación de eliminación.
   */
  @ViewChild('modalEliminar') modalEliminar!: ModalComponent;

  /**
   * Formulario para eliminar un trabajador.
   */
  eliminarForm = new FormGroup({});

  /**
   * Modelo para eliminar un trabajador.
   */
  eliminarModel: any = {};

  /**
   * Campos del formulario para confirmar la eliminación del trabajador.
   */
  eliminarFields: FormlyFieldConfig[] = [
    {
      key: 'email',
      type: 'input',
      props: {
        type:'hidden'
      }
    },


  ];

  /**
   * Abre el modal de eliminación para un trabajador específico.
   * @param trabajador Trabajador seleccionado para eliminar.
   */
  confirmarEliminacion(trabajador: TrabajadorDto) {
    this.eliminarModel.email = trabajador.email;
    this.modalEliminar.open();
  }

  /**
   * Elimina al trabajador seleccionado y actualiza la lista.
   */
  eliminarTrabajador(){
    this.empresaService.bajaTrabajador(this.eliminarModel).subscribe({
      next: data => {
        this.empresaService.trabajadoresPorEmpresa().subscribe(resp => {
          this.allTrabajadores = resp.data ?? [];
          this.trabajadores = [...this.allTrabajadores];
          this.modalEliminar.close();
          this.snackbarok('Se ha eliminado correctamente');
        });
      },
      error: err => {
        this.snackbarnok('Hubo un error al eliminar');
      }
    })
  }

  /**
   * Formulario para editar los datos del trabajador.
   */
  editarForm = new FormGroup({});

  /**
   * Modelo de datos para la edición del trabajador.
   */
  editarModel: any = {};

  /**
   * Campos del formulario de edición del trabajador.
   */
  editarFields: FormlyFieldConfig[] = [
    {
      key: 'nombre',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Nombre',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'apellidos',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Apellidos',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'dni',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'DNI',
        maxLength:9,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'naf',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'NAF',
        maxLength: 12,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'genero',
      type: 'select',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Género',
        required: true,
        hideRequiredMarker: true,
        options: []
      },
      hooks:{
        onInit: field => {
          this.generoService.listarGeneros().subscribe({
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
      key: 'fechaNacimiento',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha Nacimiento',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'fechaAntiguedad',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha Antigüedad',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'categoria',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Categoría',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'iban',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'IBAN',
        maxLength: 24,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'telefono',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Teléfono',
        maxLength: 9,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'email',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Email',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
  ];

  /**
   * Referencia al modal de edición del trabajador.
   */
  @ViewChild('modalEditar') modalEditar!: ModalComponent;

  /**
   * Trabajador actualmente seleccionado para edición.
   */
  trabajadorEditado!: TrabajadorDto;

  /**
   * Abre el modal de edición con los datos del trabajador seleccionado.
   * @param trabajador Trabajador que se va a editar.
   */
  openModalEditar(trabajador: TrabajadorDto) {
    this.trabajadorEditado = {...trabajador}
    this.editarModel = this.trabajadorEditado;
    this.modalEditar.open();
  }

  /**
   * Envía los datos editados del trabajador al backend para actualizarlo.
   * Tras actualizar, recarga la lista de trabajadores.
   */
  actualizarTrabajador() {
    this.empresaService.actualizarTrabajador1(this.trabajadorEditado).subscribe({
      next: resp => {
        this.empresaService.trabajadoresPorEmpresa().subscribe(resp => {
          this.allTrabajadores = resp.data ?? [];
          this.trabajadores = [...this.allTrabajadores];
          this.modalEditar.close();
          this.snackbarok('Se ha actualizado correctamente')
        });
      },
      error: err => {
        this.snackbarnok('Hubo un error al actualizar');
      }
    })
  }

  /**
   * Referencia al modal que muestra las nóminas del trabajador.
   */
  @ViewChild('modalNomina') modalNomina!: ModalComponent;

  /**
   * Trabajador actualmente seleccionado para gestionar nóminas.
   */
  currentTrabajador!: TrabajadorDto;

  /**
   * Nombre completo del trabajador (usado para mostrar en la vista).
   */
  nombre: string = '';

  /**
   * Lista de nóminas del trabajador actual.
   */
  nominasTrabajador: NominaMetadataDto[] = [];

  /**
   * Abre el modal de nóminas para el trabajador seleccionado.
   * Carga las nóminas desde el backend.
   * @param trabajador Trabajador seleccionado.
   */
  openModalNomina(trabajador: TrabajadorDto) {
    this.currentTrabajador = {...trabajador}
    this.nombre = trabajador.nombre! + ' ' + trabajador.apellidos!;
    this.nominaService.listarNominasDeTrabajador(this.currentTrabajador).subscribe({
      next: resp => {
        this.nominasTrabajador = resp.data ?? [];
        this.modalNomina.open();
      },
      error: err => {
        this.snackbarnok('Hubo un error al buscar las nóminas');
      }
    })
    this.uploadForm.patchValue({ trabajadorId: trabajador.id });
    this.showUploadForm = false;
  }

  /**
   * Descarga la nómina seleccionada como archivo PDF.
   * @param nomina Nómina a descargar.
   */
  descargarNomina(nomina: NominaMetadataDto) {

    const payload: IdRequestDto = { id: nomina.id! };

    const url = `${this.nominaService.configuration.basePath}/nominas/descargar`;

    this.http.post(url, payload, {
      responseType: 'blob',
      observe: 'response'
    }).subscribe({
      next: (resp: HttpResponse<Blob>) => {
        const contentDisp = resp.headers.get('content-disposition') || '';
        const match = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/.exec(contentDisp);
        const filename = match ? match[1].replace(/['"]/g, '') : `nomina-${nomina.periodo}-${this.currentTrabajador.nombre + '-' + this.currentTrabajador.apellidos}.pdf`;

        const blob = new Blob([resp.body!], { type: resp.body!.type });
        const urlBlob = window.URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = urlBlob;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);

        window.URL.revokeObjectURL(urlBlob);
      },
      error: err => {
        this.snackbarnok('Hubo un error con la descarga');
      }
    });
  }


  /**
   * Elimina la nómina seleccionada del trabajador.
   * Tras eliminar, recarga la lista de nóminas.
   * @param nomina Nómina a eliminar.
   */
  eliminarNomina(nomina: NominaMetadataDto) {
    this.nominaService.eliminarNomina(nomina).subscribe({
      next: resp => {
        this.nominaService.listarNominasDeTrabajador(this.currentTrabajador).subscribe({
          next: resp => {
            this.nominasTrabajador = resp.data ?? [];
            this.snackbarok('Se ha eliminado correctamente');
          },
          error: err => {
            this.snackbarnok('Hubo al recargar el listado de nóminas');
          }
        })
      },
      error: err => {this.snackbarnok('Hubo un error al eliminar');}
    })
  }

  /**
   * Bandera que indica si se muestra el formulario de subida de nómina.
   */
  showUploadForm: boolean = false;

  /**
   * Formulario reactivo para subir una nueva nómina.
   */
  uploadForm: FormGroup;

  /**
   * Alterna entre mostrar el formulario de subida de nómina o consultar las nóminas.
   * También resetea el formulario y precarga el ID del trabajador actual.
   */
  cambiarFormularioSubirNomina() {
    this.uploadForm.reset();
    this.uploadForm.patchValue({ trabajadorId: this.currentTrabajador.id });
    this.showUploadForm = !this.showUploadForm;
  }

  /**
   * Mensaje dinámico del botón que alterna la vista (subir/consultar nóminas).
   */
  get mensajeBoton(): string {
    return this.showUploadForm ? 'Consultar nóminas' : 'Subir nómina';
  }

  /**
   * Icono dinámico del botón que alterna la vista (subir/consultar nóminas).
   */
  get iconoBoton(): string {
    return this.showUploadForm
      ? 'bi bi-file-earmark-text'
      : 'bi bi-file-earmark-arrow-up';
  }

  /**
   * Captura el cambio en el input de archivo y actualiza el formulario.
   * @param event Evento del input file.
   */
  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) {
      this.uploadForm.patchValue({ file: null });
      return;
    }
    this.uploadForm.patchValue({ file: input.files[0] });
  }

  /**
   * Envía el formulario de subida de nómina al backend.
   * Tras subir, recarga la lista de nóminas del trabajador.
   */
  submitUpload() {
    if (this.uploadForm.invalid) {
      return;
    }
    const { trabajadorId, periodo, file } = this.uploadForm.value;

    this.nominaService.subirNomina(trabajadorId, periodo, file).subscribe({
      next: () => {
        this.nominaService.listarNominasDeTrabajador(this.currentTrabajador).subscribe({
          next: (resp) => {
            this.nominasTrabajador = resp.data ?? [];
            this.snackbarok('Nómina guardada');
          },
          error: err => {this.snackbarnok('Hubo un error al recargar las nóminas');}
        });
        this.showUploadForm = false;
      },
      error: (err) => {
        this.modalNomina.close();
        this.snackbarnok(err.error.data);
      }
    });
  }

  /**
   * Referencia al modal que muestra los fichajes del trabajador.
   */
  @ViewChild('modalFichajes') modalFichajes!: ModalComponent;

  /**
   * Lista de fichajes filtrados para mostrar en el modal.
   */
  fichajesFiltrados: any[] = [];

  /**
   * Abre el modal de fichajes para un trabajador específico.
   * Recupera, filtra, ordena y formatea los 10 fichajes más recientes.
   * @param t Trabajador seleccionado.
   */
  openModalFichajes(t: TrabajadorDto) {
    this.nombre = t.nombre + ' ' + t.apellidos;
    this.empresaService.fichajesPorEmpresa().subscribe({
      next: resp => {
        const data = resp.data ?? [];

        const recientes = data
          .filter(f => f.trabajador?.id === t.id)
          .sort((a, b) =>
            new Date(b.fechaInicio!).getTime() - new Date(a.fechaInicio!).getTime()
          )
          .slice(0, 10);

        const formatoFecha = (iso: string) => {
          const d = new Date(iso);
          const fecha = d.toLocaleDateString('es-ES', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
          });
          const hora = d.toLocaleTimeString('es-ES', {
            hour: '2-digit',
            minute: '2-digit'
          });
          return `${fecha} ${hora}`;
        };

        const formatoDuracion = (inicioIso: string, finIso?: string) => {
          const ini = new Date(inicioIso).getTime();
          const fin = finIso ? new Date(finIso).getTime() : Date.now();
          let diff = fin - ini;
          const hrs = Math.floor(diff / 3_600_000);
          diff %= 3_600_000;
          const mins = Math.floor(diff / 60_000);
          return `${hrs} h ${mins.toString().padStart(2, '0')} m`;
        };

        this.fichajesFiltrados = recientes.map(f => ({
          ...f,
          fechaInicioFmt: formatoFecha(f.fechaInicio!),
          fechaFinFmt: f.fechaFin ? formatoFecha(f.fechaFin) : '—',
          tiempoTrabajado: formatoDuracion(f.fechaInicio!, f.fechaFin)
        }));
        this.modalFichajes.open();
      },
      error: err => {
        this.snackbarnok('Hubo un error al recuperar los fichajes');
      }
    });
  }

  /**
   * Muestra un mensaje de éxito con el snackbar.
   * @param mensaje Texto del mensaje.
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
   * Muestra un mensaje de error con el snackbar.
   * @param mensaje Texto del mensaje.
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
   * Referencia al modal para crear un nuevo trabajador.
   */
  @ViewChild('modalNuevoTrabajador') modalNuevoTrabajador!: ModalComponent;

  /**
   * Formulario reactivo para crear un nuevo trabajador.
   */
  nuevoForm = new FormGroup({});

  /**
   * Modelo de datos para el nuevo trabajador.
   */
  nuevoModel: any = {};

  /**
   * Campos del formulario para alta de nuevo trabajador.
   */
  nuevoFields: FormlyFieldConfig[] = [
    {
      key: 'nombre',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Nombre',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'apellidos',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Apellidos',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'dni',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        maxLength: 9,
        label: 'DNI',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'naf',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'NAF',
        maxLength: 12,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'genero',
      type: 'select',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Género',
        required: true,
        hideRequiredMarker: true,
        options: [],
      },
      hooks:{
        onInit: field => {
          this.generoService.listarGeneros().subscribe({
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
      key: 'fechaNacimiento',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha Nacimiento',
        type:'date',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'fechaAntiguedad',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha Antigüedad',
        type:'date',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'categoria',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Categoría',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'iban',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'IBAN',
        maxLength: 24,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'telefono',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Teléfono',
        maxLength: 9,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'email',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Email',
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'password',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Contraseña',
        type:'password',
        required: true,
        hideRequiredMarker: true
      }
    },

  ];

  /**
   * Abre el modal para crear un nuevo trabajador y resetea el formulario.
   */
  abrirModalNuevoTrabajador() {
    this.nuevoForm.reset();
    this.modalNuevoTrabajador.open();
  }

  /**
   * Envía los datos del nuevo trabajador al backend y actualiza la lista.
   */
  guardarNuevoTrabajador() {
    console.log(this.nuevoModel)
    this.empresaService.altaTrabajador(this.nuevoModel).subscribe({
      next: response => {
        this.snackbarok("Trabajador creado correctamente");
        this.cargarTrabajadores();
        this.modalNuevoTrabajador.close();
      },
      error: err => {
        this.snackbarnok("Hubo un error al crear el trabajador")
      }
    })
  }

  /**
   * Agrega un listener para permitir el scroll horizontal con la rueda del ratón.
   * Apunta al área de scroll de tarjetas.
   */
  private addScrollListeners() {
    const el = this.scrollArea.nativeElement;

    el.addEventListener('wheel', (e) => {
      if (e.deltaY !== 0) {
        e.preventDefault();
        el.scrollLeft += e.deltaY;
      }
    }, { passive: false });
  }

  /**
   * Formateador de fecha reutilizable en la vista.
   */
  protected readonly formatDate = formatDate;
}
