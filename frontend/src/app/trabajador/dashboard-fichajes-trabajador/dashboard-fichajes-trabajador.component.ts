/**
 * Componente DashboardFichajesTrabajadorComponent
 *
 * Este componente gestiona la vista del dashboard de fichajes
 * para el trabajador individual.
 * Permite consultar fichajes pasados, filtrar resultados,
 * e iniciar/finalizar nuevos fichajes.
 */
import {Component, OnInit} from '@angular/core';
import {FichajesDto, FichajesService, TrabajadoresService} from "../../../openapi";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FormlyModule} from "@ngx-formly/core";
import {NgxPaginationModule} from "ngx-pagination";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {NgForOf} from "@angular/common";

/**
 * Interfaz extendida para enriquecer los datos del fichaje con formatos
 * legibles para la interfaz.
 */
interface FichajeView extends FichajesDto {
  inicioFmt: string;
  finFmt: string;
  tiempoTrabajado: string;
}

@Component({
  selector: 'app-dashboard-fichajes-trabajador',
  standalone: true,
  templateUrl: './dashboard-fichajes-trabajador.component.html',
  imports: [
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    FormlyModule,
    NgForOf,
  ],
  styleUrl: './dashboard-fichajes-trabajador.component.scss'
})

export class DashboardFichajesTrabajadorComponent implements OnInit {

  /**
   * Lista de fichajes mostrados en la vista filtrada.
   */
  fichajes: FichajeView[] = [];

  /**
   * Lista completa de todos los fichajes recuperados.
   */
  allFichajes: FichajeView[] = [];

  /**
   * Formulario reactivo para el buscador.
   */
  filterForm = new FormGroup<{ query: FormControl<string | null> }>({
    query: new FormControl<string | null>(null),
  });

  /**
   * Constructor del componente.
   * Inyecta los servicios necesarios.
   */
  constructor(private trabajadoresService: TrabajadoresService,
              private fichajesService: FichajesService, private snackBar: MatSnackBar ) { }


  /**
   * Hook ngOnInit
   * Se ejecuta al iniciar el componente.
   * Carga los fichajes y configura el escuchador del buscador.
   */
  ngOnInit(): void {
    this.cargarFichajes();
    this.filterForm.get('query')!
      .valueChanges.pipe(debounceTime(200), distinctUntilChanged())
      .subscribe(() => this.onSearch());
  }

  /**
   * Recupera los fichajes del trabajador desde el servicio.
   */
  private cargarFichajes() {
    this.trabajadoresService.fichajesPorEmailTrabajador().subscribe({
      next: resp => {
        const datos = resp.data ?? [];
        datos.sort((a, b) =>
          new Date(b.fechaInicio!).getTime() - new Date(a.fechaInicio!).getTime()
        );
        this.fichajes = datos.map(f => this.toView(f));
        this.allFichajes = [...this.fichajes];
      },
      error: () => this.snackbarError('No se pudieron recuperar los fichajes'),
    });
  }

  /**
   * Transforma un fichaje original en un objeto extendido con
   * formatos legibles para la UI.
   */
  private toView(f: FichajesDto):FichajeView {
    const inicio = new Date(f.fechaInicio!);
    const fin = f.fechaFin ? new Date(f.fechaFin) : new Date();
    const pad = (n: number) => n.toString().padStart(2, '0');
    const fmt = (d: Date) =>
      `${pad(d.getDate())}/${pad(d.getMonth()+1)}/${d.getFullYear()} ${pad(d.getHours())}:${pad(d.getMinutes())}`;
    let diff = fin.getTime() - inicio.getTime();
    const hrs = Math.floor(diff / 36e5);
    diff %= 36e5;
    const mins = Math.floor(diff / 6e4);
    return {
      ...f,
      inicioFmt: fmt(inicio),
      finFmt: f.fechaFin ? fmt(fin) : '—',
      tiempoTrabajado: `${hrs}h ${pad(mins)}m`,
    };
  }

  /**
   * Llama al servicio para iniciar un nuevo fichaje.
   */
  iniciarFichaje() {
    this.fichajesService.iniciarFichaje().subscribe({
      next: () => { this.snackbarOk('Entrada registrada'); this.cargarFichajes(); },
      error: (err) => {
        this.snackbarError(err.error.data);
      },
    });
  }

  /**
   * Llama al servicio para finalizar el fichaje abierto.
   */
  finalizarFichaje() {
    this.fichajesService.finalizarFichaje().subscribe({
      next: () => { this.snackbarOk('Salida registrada'); this.cargarFichajes(); },
      error: (err) => {
        this.snackbarError(err.error.data);
      },
    });
  }

  /**
   * Filtra los fichajes mostrados según la búsqueda del usuario.
   */
  onSearch() {
    const q = this.filterForm.value.query?.toLowerCase().trim() || '';
    if (!q) {
      this.fichajes = [...this.allFichajes];
    } else {
      this.fichajes = this.allFichajes.filter(f =>
        [f.inicioFmt, f.finFmt, f.tiempoTrabajado]
          .some(field => field.toLowerCase().includes(q))
      );
    }
  }

  /**
   * Muestra un snackbar de éxito con mensaje personalizado.
   */
  private snackbarOk(msg: string) {
    this.snackBar.open(msg, '', { panelClass: 'snackbar-success', horizontalPosition: 'right', verticalPosition: 'bottom', duration: 3000 });
  }

  /**
   * Muestra un snackbar de error con mensaje personalizado.
   */
  private snackbarError(msg: string) {
    this.snackBar.open(msg, '', { panelClass: 'snackbar-error', horizontalPosition: 'right', verticalPosition: 'bottom', duration: 3000 });
  }
}
