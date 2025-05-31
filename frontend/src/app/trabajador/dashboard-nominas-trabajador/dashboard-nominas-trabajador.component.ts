/**
 * Componente DashboardNominasTrabajadorComponent
 *
 * Este componente muestra la lista de nóminas del trabajador actual,
 * permitiéndole visualizar y descargar las nóminas almacenadas.
 * Usa servicios HTTP para obtener los datos y generar las descargas.
 */
import {Component, OnInit} from '@angular/core';
import {IdRequestDto, NominaMetadataDto, NominasService} from "../../../openapi";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NgForOf, NgIf} from "@angular/common";
import {FormlyModule} from "@ngx-formly/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {formatDate} from "../../core/library/library";

@Component({
  selector: 'app-dashboard-nominas-trabajador',
  standalone: true,
  imports: [
    FormlyModule,
    NgForOf,
    NgIf

  ],
  templateUrl: './dashboard-nominas-trabajador.component.html',
  styleUrl: './dashboard-nominas-trabajador.component.scss'
})
export class DashboardNominasTrabajadorComponent implements OnInit {

  /**
   * Constructor que inyecta los servicios necesarios:
   * - NominasService: servicio para operaciones de nóminas.
   * - MatSnackBar: para mostrar mensajes.
   * - HttpClient: para llamadas HTTP directas (descarga de PDFs).
   */
  constructor(private nominasService: NominasService, private snackBar: MatSnackBar, private http: HttpClient) {
  }

  /**
   * Lista de nóminas recuperadas para el trabajador.
   */
  nominas: NominaMetadataDto[] = [];

  /**
   * Hook ngOnInit que recupera las nóminas del trabajador
   * y las ordena por periodo, de más reciente a más antigua.
   */
  ngOnInit(): void {
    this.nominasService.listarNominasDeTrabajadorPorEmail().subscribe({
      next: data => {
        this.nominas = data.data!;
        this.nominas = data.data!
          .sort((a, b) => {
            // Si alguno no tiene periodo, lo mandamos al final
            if (!a.periodo) return 1;
            if (!b.periodo) return -1;
            // orden descendente (más reciente primero)
            return b.periodo.localeCompare(a.periodo);
          })
      },
      error: err => {}
    })
  }

  /**
   * Descarga el PDF de una nómina específica.
   *
   * @param nomina La nómina seleccionada a descargar.
   */
  descargarNomina(nomina: NominaMetadataDto) {

    const payload: IdRequestDto = { id: nomina.id! };

    const url = `${this.nominasService.configuration.basePath}/nominas/descargar`;

    this.http.post(url, payload, {
      responseType: 'blob',
      observe: 'response'
    }).subscribe({
      next: (resp: HttpResponse<Blob>) => {
        const contentDisp = resp.headers.get('content-disposition') || '';
        const match = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/.exec(contentDisp);
        const filename = match ? match[1].replace(/['"]/g, '') : `nomina-${nomina.periodo}.pdf`;

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
   * Muestra un mensaje snackbar de éxito.
   *
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
   *
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
   * Referencia protegida a la utilidad de formato de fecha.
   */
  protected readonly formatDate = formatDate;
}
