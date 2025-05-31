/**
 * Convierte un valor de enum (en formato cadena) a un formato más humano.
 * <p>
 * Ejemplo:
 * 'FORMA_JURIDICA' → 'Forma Juridica'
 *
 * @param value cadena original del enum
 * @return cadena formateada con mayúsculas iniciales y espacios
 */
export function humanizeEnum(value: string): string {
  return value
    .toLowerCase()
    .split(/[_\s]+/)
    .map(w => w.charAt(0).toUpperCase() + w.slice(1))
    .join(' ');
}

/**
 * Formatea una fecha en formato 'YYYY-MM-DD' a formato 'DD/MM/YYYY'.
 * <p>
 * Ejemplo:
 * '2024-06-15' → '15/06/2024'
 *
 * @param date cadena de fecha en formato ISO
 * @return cadena de fecha en formato europeo
 */
export function formatDate(date: string): string {
  const [year, month, day] = date.split('-');
  return `${day}/${month}/${year}`;
}

/**
 * Formatea un valor numérico de horas (decimales) a formato 'Xh Ym'.
 * <p>
 * Ejemplo:
 * 1.5 → '1h 30m'
 *
 * @param value número en formato decimal (horas)
 * @return cadena formateada en horas y minutos, o cadena vacía si no es válido
 */
export function formatHours(value: number | null): string {
  if (value == null || isNaN(value)) { return ''; }
  const h = Math.floor(value);
  const m = Math.round((value - h) * 60);
  return `${h}h ${m}m`;
}