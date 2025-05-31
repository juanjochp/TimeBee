import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Interceptor de autenticación.
 * <p>
 * Este interceptor se encarga de interceptar todas las peticiones HTTP salientes
 * y, si existe un token almacenado en localStorage, lo adjunta automáticamente
 * en los encabezados como 'Authorization: Bearer &lt;token&gt;'.
 *
 * Así, asegura que las peticiones al backend que requieren autenticación
 * lleven las credenciales correctas sin necesidad de configurarlas manualmente
 * en cada llamada.
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  /**
   * Método que intercepta las peticiones HTTP.
   * <p>
   * Si encuentra un token en localStorage, clona la petición original
   * y le agrega el header Authorization con el token.
   * Si no hay token, deja pasar la petición tal cual.
   *
   * @param req petición HTTP original
   * @param next manejador de la cadena de interceptores
   * @return Observable con el evento HTTP (modificado o no)
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token');

    if (token) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(authReq);
    }

    return next.handle(req);
  }
}
