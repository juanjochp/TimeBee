/**
 * OpenAPI definition
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { GeneroDto } from './generoDto';


/**
 * Respuestas estandarizadas de la api
 */
export interface ApiRespuestaListGeneroDto { 
    data?: Array<GeneroDto>;
    estado?: ApiRespuestaListGeneroDto.EstadoEnum;
}
export namespace ApiRespuestaListGeneroDto {
    export type EstadoEnum = 'exito' | 'error';
    export const EstadoEnum = {
        Exito: 'exito' as EstadoEnum,
        Error: 'error' as EstadoEnum
    };
}


