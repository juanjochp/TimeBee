/**
 * OpenAPI definition
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { TrabajadorDto } from './trabajadorDto';


/**
 * Respuestas estandarizadas de la api
 */
export interface ApiRespuestaTrabajadorDto { 
    data?: TrabajadorDto;
    estado?: ApiRespuestaTrabajadorDto.EstadoEnum;
}
export namespace ApiRespuestaTrabajadorDto {
    export type EstadoEnum = 'exito' | 'error';
    export const EstadoEnum = {
        Exito: 'exito' as EstadoEnum,
        Error: 'error' as EstadoEnum
    };
}


