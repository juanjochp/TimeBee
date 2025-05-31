import { Component } from '@angular/core';
import {FieldWrapper, FormlyModule} from '@ngx-formly/core';
import {NgIf} from "@angular/common";

/**
 * Wrapper horizontal para campos Formly.
 * <p>
 * Este componente define cómo se renderizan los campos de formulario
 * usando un diseño horizontal (etiqueta a la izquierda, campo a la derecha),
 * compatible con Bootstrap.
 * También gestiona la visualización de etiquetas, marcadores de requerido
 * y mensajes de validación de errores.
 */
@Component({
    selector: 'formly-horizontal-wrapper',
    template: `
        <div class="row mb-3">
            <label [attr.for]="id" class="col-sm-5 col-form-label text-white" *ngIf="props.label">
                {{ props.label }}
                <ng-container *ngIf="props.required && props['hideRequiredMarker'] !== true">*</ng-container>
            </label>
            <div class="col-sm-7">
                <ng-template #fieldComponent></ng-template>
                <div *ngIf="showError" class="col-sm-3 invalid-feedback d-block">
                    <formly-validation-message [field]="field"></formly-validation-message>
                </div>
            </div>
        </div>
    `,
    imports: [
        FormlyModule,
        NgIf
    ]
})
export class FormlyHorizontalWrapper extends FieldWrapper {}
