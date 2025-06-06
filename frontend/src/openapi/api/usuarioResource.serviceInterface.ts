/**
 * OpenAPI definition
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { HttpHeaders }                                       from '@angular/common/http';

import { Observable }                                        from 'rxjs';

import { ApiRespuestaBoolean } from '../model/models';
import { EmpresaDto } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface UsuarioResourceServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Sing in de una empresa
     * Sing in de una empresa
     * @param empresaDto 
     */
    altaEmpresa(empresaDto: EmpresaDto, extraHttpRequestParams?: any): Observable<ApiRespuestaBoolean>;

}
