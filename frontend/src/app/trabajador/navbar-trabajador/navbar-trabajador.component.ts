/**
 * Componente NavbarTrabajadorComponent
 *
 * Este componente muestra la barra de navegación del trabajador,
 * permitiéndole consultar su información personal, editarla
 * y cerrar sesión.
 */
import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {GenerosService, TrabajadorDto, TrabajadoresService} from "../../../openapi";
import {ModalComponent} from "../../core/modal/modal.component";
import {FormGroup} from "@angular/forms";
import {FormlyFieldConfig, FormlyModule} from "@ngx-formly/core";
import {NgOptimizedImage} from "@angular/common";
import {humanizeEnum} from "../../core/library/library";

@Component({
  selector: 'app-navbar-trabajador',
  standalone: true,
  templateUrl: './navbar-trabajador.component.html',
  imports: [
    ModalComponent,
    FormlyModule,
    NgOptimizedImage
  ],
  styleUrl: './navbar-trabajador.component.scss'
})
export class NavbarTrabajadorComponent implements OnInit {

  /**
   * Constructor del componente.
   *
   * @param router Router para redirecciones.
   * @param trabajadoresService Servicio para gestionar datos del trabajador.
   * @param genero Servicio para obtener los géneros.
   * @param snackBar Servicio para mostrar mensajes snackbar.
   */
  constructor(private router:Router, private trabajadoresService: TrabajadoresService, private genero:GenerosService ,private snackBar: MatSnackBar) {

  }

  /**
   * Objeto que almacena la información del trabajador.
   */
  trabajador: TrabajadorDto={nombre:'', apellidos:''};

  /**
   * Hook de inicialización.
   * Obtiene los datos del trabajador actual al cargar el componente.
   */
  ngOnInit(): void {
    this.trabajadoresService.obtenerTrabajador().subscribe({
      next: response => {
        this.trabajador = response.data!;
      },
      error: error => {
        this.snackbarnok("Hubo un error al recuperar los datos del trabajador")
      }
    })
  }

  /**
   * Cierra la sesión actual.
   * Elimina el token y redirige al inicio.
   */
  cerrarSession() {
    localStorage.removeItem("token");
    this.router.navigate(['']);
  }

  /**
   * Referencia al modal de edición de datos del trabajador.
   */
  @ViewChild('modalEditarTrabajador') modalEditarTrabajador!: ModalComponent;

  /**
   * Formulario de edición de trabajador.
   */
  editarTrabajadorForm = new FormGroup({});

  /**
   * Modelo de datos para el formulario de edición.
   */
  editarTrabajadorModel: any = {};

  /**
   * Configuración de los campos del formulario de edición.
   */
  editarTrabajadorFields: FormlyFieldConfig[] = [
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
          this.genero.listarGeneros().subscribe({
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
      key: 'fechaNacimiento',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Fecha de Nacimiento',
        type:'date',
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
        disabled: true
      }
    },
    {
      key: 'passwordAntigua',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Contraseña',
        type:'password',
        // required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'passwordNueva',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Contraseña nueva',
        type:'password',
        // required: true,
        hideRequiredMarker: true
      }
    }
  ];

  /**
   * Abre el modal de edición con los datos actuales del trabajador.
   */
  openConfigTrabajador() {
      this.editarTrabajadorModel = this.trabajador;
      this.editarTrabajadorForm.patchValue(this.editarTrabajadorModel);
      this.modalEditarTrabajador.open();
  }

  /**
   * Muestra un mensaje snackbar de éxito.
   * @param mensaje Texto a mostrar.
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
   * @param mensaje Texto a mostrar.
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
   * Envía los datos actualizados del trabajador al backend.
   * Si cambia el email, obliga a cerrar sesión.
   */
  actualizarDatosTrabajador() {
    this.trabajadoresService.actualizarTrabajador(this.editarTrabajadorModel).subscribe({
      next: response => {
        if (response.data == false) {
          localStorage.removeItem("token");
          this.router.navigate(['']);
        }

        this.modalEditarTrabajador.close();
        this.snackbarok('¡Datos actualizados!');
      },
      error: error => {
        this.modalEditarTrabajador.close();
        this.snackbarnok('Hubo un error al actualizar los datos');
      }

    })

  }
}
