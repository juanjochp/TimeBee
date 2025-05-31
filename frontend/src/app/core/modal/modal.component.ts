import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgIf} from '@angular/common';
import { trigger, style, animate, transition } from '@angular/animations';

/**
 * Componente modal reutilizable.
 * <p>
 * Este componente muestra una ventana emergente (modal)
 * con animaciones suaves de entrada y salida.
 * Puede abrirse y cerrarse mediante métodos públicos
 * y notifica al exterior cuando es cerrado.
 */
@Component({
    selector: 'app-modal',
    standalone: true,
    templateUrl: './modal.component.html',
    imports: [
        NgIf
    ],
    styleUrl: './modal.component.scss',
    animations: [
        /**
         * Animación para la capa de fondo (overlay).
         * Hace un fundido suave al entrar y salir.
         */
        trigger('fadeOverlay', [
            transition(':enter', [
                style({ opacity: 0 }),
                animate('200ms ease-out', style({ opacity: 1 })),
            ]),
            transition(':leave', [
                animate('200ms ease-in', style({ opacity: 0 })),
            ]),
        ]),
        /**
         * Animación para el contenido del modal.
         * Escala suavemente al aparecer y desaparecer.
         */
        trigger('scaleContent', [
            transition(':enter', [
                style({ transform: 'scale(0.8)', opacity: 0 }),
                animate('200ms ease-out', style({ transform: 'scale(1)', opacity: 1 })),
            ]),
            transition(':leave', [
                animate('200ms ease-in', style({ transform: 'scale(0.8)', opacity: 0 })),
            ]),
        ]),
    ],
})
export class ModalComponent {
    /**
     * Título opcional del modal.
     * Puede mostrarse en la cabecera del contenido.
     */
    @Input() title?: string;

    /**
     * Evento que se emite cuando el modal es cerrado.
     * Permite al componente padre reaccionar al cierre.
     */
    @Output() closed = new EventEmitter<void>();

    /**
     * Indica si el modal está visible.
     * Controlado internamente por los métodos open() y close().
     */
    visible = false;

    /**
     * Método para cerrar el modal.
     * Oculta el contenido y emite el evento 'closed'.
     */
    close() {
        this.visible = false;
        this.closed.emit();
    }

    /**
     * Método para abrir el modal.
     * Simplemente establece la visibilidad en true.
     */
    open() {
        this.visible = true;
    }
}


