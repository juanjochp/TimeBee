/**
 * Componente de la barra de navegación para la vista de empresa.
 *
 * Este componente muestra la información de la empresa,
 * permite cerrar sesión, y gestionar la edición de los datos de la empresa.
 */
import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {EmpresaDto, EmpresasService, FormasJurdicasService} from "../../../openapi";
import {ModalComponent} from "../../core/modal/modal.component";
import {FormGroup} from "@angular/forms";
import {FormlyFieldConfig, FormlyModule} from "@ngx-formly/core";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NgOptimizedImage} from "@angular/common";
import {humanizeEnum} from "../../core/library/library";

/**
 * Define el selector, plantilla y estilos del componente navbar de empresa.
 */
@Component({
  selector: 'app-navbar-empresa',
  standalone: true,
  templateUrl: './navbar-empresa.component.html',
  imports: [
    ModalComponent,
    FormlyModule,
    NgOptimizedImage
  ],
  styleUrl: './navbar-empresa.component.scss'
})
export class NavbarEmpresaComponent implements OnInit {

  /**
   * Constructor para inyectar servicios necesarios.
   * @param router Enrutador Angular.
   * @param empresasService Servicio para obtener datos de empresa.
   * @param formasJuridicas Servicio para listar formas jurídicas.
   * @param snackBar Servicio de notificaciones tipo snackbar.
   */
  constructor(private router:Router, private empresasService: EmpresasService,
              private formasJuridicas: FormasJurdicasService,private snackBar: MatSnackBar) {

  }

  /**
   * Datos de la empresa actual.
   */
  empresa: EmpresaDto = {nombre:''};

  /**
   * Al inicializar, recupera los datos de la empresa por email.
   */
  ngOnInit(): void {
    this.empresasService.empresaPorEmail().subscribe({
      next: response => {
        this.empresa = response.data!;
      },
      error: error => {
        this.snackbarnok("Hubo un error al recuperar los datos de la empresa")
      }
    })
  }

  /**
   * Cierra la sesión eliminando el token y redirige a la página principal.
   */
  cerrarSession() {
    localStorage.removeItem("token");
    this.router.navigate(['']);
  }

  /**
   * Referencia al modal para editar datos de la empresa.
   */
  @ViewChild('modalEditarEmpresa') modalEditarEmpresa!: ModalComponent;

  /**
   * Formulario reactivo para editar datos de la empresa.
   */
  editarEmpresaForm = new FormGroup({});

  /**
   * Modelo de datos para el formulario de edición.
   */
  editarEmpresaModel: any = {};

  /**
   * Campos configurados para el formulario Formly.
   */
  editarEmpresaFields: FormlyFieldConfig[] = [
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
      key: 'formaJuridica',
      type: 'select',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Forma Jurídica',
        required: true,
        hideRequiredMarker: true,
        options: []
      },
      hooks:{
        onInit: field => {
          this.formasJuridicas.listarFormasJuridicas().subscribe({
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
      key: 'cif',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'CIF',
        maxLength:9,
        type:'text',
        required: true,
        hideRequiredMarker: true
      }
    },
    {
      key: 'direccion',
      type: 'input',
      wrappers: ['form-field-horizontal'],
      props: {
        label: 'Dirección',
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
   * Abre el modal de edición de empresa cargando los datos actuales.
   */
  openConfig() {
    this.editarEmpresaModel = this.empresa;
    this.editarEmpresaForm.patchValue(this.editarEmpresaModel);
    this.modalEditarEmpresa.open();

  }

  /**
   * Muestra un mensaje snackbar de éxito.
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
   * Muestra un mensaje snackbar de error.
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
   * Envía los datos actualizados de la empresa al backend.
   * Si la respuesta indica false, se cierra sesión.
   */
  actualizarDatosEmpresa() {
    this.empresasService.actualizarEmpresa(this.editarEmpresaModel).subscribe({
      next: response => {
        if (response.data == false) {
          localStorage.removeItem("token");
          this.router.navigate(['']);
        }

        this.modalEditarEmpresa.close();
        this.snackbarok('¡Datos actualizados!');
      },
      error: error => {
        this.modalEditarEmpresa.close();
        this.snackbarnok('Hubo un error al actualizar los datos');
      }

    })
  }

}
