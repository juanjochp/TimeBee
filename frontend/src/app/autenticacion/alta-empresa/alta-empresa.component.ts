import {Component} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {FormasJurdicasService, UsuarioResourceService} from '../../../openapi';
import {Router} from '@angular/router';
import {humanizeEnum} from "../../core/library/library";

/**
 * Componente de alta de empresa.
 * <p>
 * Este componente permite a un usuario registrar una nueva empresa en el sistema.
 * Se apoya en un formulario dinámico construido con Formly,
 * integrando datos de formas jurídicas y validaciones de campos.
 */
@Component({
  selector: 'app-alta-empresa',
  standalone: false,
  templateUrl: './alta-empresa.component.html',
  styleUrl: './alta-empresa.component.scss'
})
export class AltaEmpresaComponent{

  /**
   * Grupo de formulario principal.
   * Se utiliza para controlar el estado y las validaciones del formulario.
   */
  form = new FormGroup({});

  /**
   * Modelo de datos que se vincula al formulario.
   * Aquí se guardan los valores introducidos por el usuario.
   */
  model: any = {};

  /**
   * Configuración de campos Formly.
   * Define los campos visibles en el formulario, su tipo, etiquetas,
   * placeholders, validaciones y comportamiento.
   */
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName:'row',
      fieldGroup: [
        {
          key: 'nombre',
          type: 'input',
          className: 'col-md-6',
          props: {
            type: 'text',
            label: 'Razón social',
            placeholder: 'Razón social',
            required: true,
            hideRequiredMarker: true
          },
        },
        {
          key: 'formaJuridica',
          type: 'select',
          className: 'col-md-6',
          props: {
            label: 'Forma jurídica',
            required: true,
            hideRequiredMarker: true,
            options:[],
          },
          /**
           * Hook de inicialización.
           * Cuando el campo se inicializa, llama al servicio de forma jurídica
           * para obtener la lista de opciones disponibles y las carga dinámicamente.
           */
          hooks:{
            onInit: field => {
              this.formaJuridicaService.listarFormasJuridicas().subscribe({
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
          className: 'col-md-6',
          props: {
            type: 'text',
            label: 'CIF',
            placeholder: 'CIF',
            required: true,
            hideRequiredMarker: true
          },
        },
        {
          key: 'telefono',
          type: 'input',
          className: 'col-md-6',
          props: {
            type: 'number',
            label: 'Teléfono',
            placeholder: 'Teléfono',
            required: true,
            hideRequiredMarker: true
          },
        },
        {
          key: 'direccion',
          type: 'input',
          props: {
            type: 'text',
            label: 'Dirección',
            placeholder: 'Dirección',
            required: true,
            hideRequiredMarker: true
          },
        },

        {
          key: 'email',
          type: 'input',
          props: {
            type: 'email',
            label: 'Correo electrónico',
            placeholder: 'Introduce tu correo electrónico',
            required: true,
            hideRequiredMarker: true
          },
        },
        {
          key: 'password',
          type: 'input',
          props: {
            type: 'password',
            label: 'Contraseña',
            placeholder: 'Introduce tu contraseña',
            required: true,
            hideRequiredMarker: true
          },
        }
      ]
    }

  ];

  /**
   * Constructor del componente.
   * Inyecta los servicios necesarios para gestionar formas jurídicas,
   * usuarios y navegación de rutas.
   *
   * @param formaJuridicaService Servicio para obtener formas jurídicas
   * @param usuarioService Servicio para gestionar operaciones de usuario
   * @param router Servicio para la navegación de rutas
   */
  constructor(
    private formaJuridicaService:FormasJurdicasService,
    private usuarioService:UsuarioResourceService,
    private router: Router,
  ) {}

  /**
   * Método que se ejecuta al enviar el formulario.
   * Llama al servicio de alta de empresa y, en caso de éxito,
   * redirige al usuario a la página de inicio.
   */
  onSubmit(){
    this.usuarioService.altaEmpresa(this.model).subscribe({
      next: response => {
        this.router.navigate(['']);
      },
      error: error => {}
    })
  }

  /**
   * Método para volver atrás (redireccionar a la página principal).
   * Puede ser usado por un botón "Cancelar" en la interfaz.
   */
  volverAtras() {
    this.router.navigate(['']);
  }
}
