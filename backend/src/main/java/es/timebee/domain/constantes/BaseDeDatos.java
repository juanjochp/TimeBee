package es.timebee.domain.constantes;

/**
 * {@code BaseDeDatos} contiene las constantes de nombres de tablas y columnas
 * usadas en la base de datos de TimeBee.
 * <p>
 * Este archivo centraliza todos los nombres para evitar errores por escritura manual
 * y asegurar consistencia a lo largo del proyecto. Es como el “diccionario oficial”
 * que consultamos cada vez que trabajamos con las entidades persistentes.
 */
public final class BaseDeDatos {

    /**
     * {@code Tablas} agrupa los nombres de las tablas principales
     * de la base de datos.
     * <p>
     * Aquí encontrarás las entidades clave del sistema: Empresa, Trabajador,
     * Fichaje, Permiso y Nómina.
     */
    public static final class Tablas {
        public static final String EMPRESA = "EMPRESA";
        public static final String TRABAJADOR = "TRABAJADOR";
        public static final String FICHAJE = "FICHAJE";
        public static final String PERMISO = "PERMISO";
        public static final String NOMINA = "NOMINA";
    }

    /**
     * {@code Columnas} agrupa los nombres de las columnas utilizadas
     * en las distintas tablas.
     * <p>
     * Este bloque es esencial para mantener coherencia en los mapeos JPA
     * y consultas SQL. Contiene desde campos generales como {@code ID}, {@code NOMBRE},
     * hasta detalles específicos como {@code FECHA_SUBIDA}, {@code ARCHIVO_PDF} o {@code ESTADO}.
     */
    public static final class Columnas {
        public static final String ID = "ID";
        public static final String NOMBRE = "NOMBRE";
        public static final String FORMA_JURIDICA = "FORMA_JURIDICA";
        public static final String CIF = "CIF";
        public static final String DIRECCION = "DIRECCION";
        public static final String TELEFONO = "TELEFONO";
        public static final String EMAIL = "EMAIL";
        public static final String APELLIDOS = "APELLIDOS";
        public static final String DNI = "DNI";
        public static final String NAF = "NAF";
        public static final String GENERO = "GENERO";
        public static final String FECHA_NACIMIENTO = "FECHA_NACIMIENTO";
        public static final String FECHA_ANTIGUEDAD = "FECHA_ANTIGUEDAD";
        public static final String CATEGORIA = "CATEGORIA";
        public static final String IBAN = "IBAN";
        public static final String FECHA_INICIO = "FECHA_INICIO";
        public static final String FECHA_FIN = "FECHA_FIN";
        public static final String TIPOPERMISO = "TIPO_PERMISO";
        public static final String FECHA = "FECHA";
        public static final String HORA = "HORA";
        public static final String NOMBRE_ARCHIVO = "NOMBRE_ARCHIVO";
        public static final String ARCHIVO_PDF = "ARCHIVO_PDF";
        public static final String CONTENT_TYPE = "CONTENT_TYPE";
        public static final String TAMANO = "TAMANO";
        public static final String FECHA_SUBIDA = "FECHA_SUBIDA";
        public static final String PERIODO = "PERIODO";
        public static final String EMPRESA_ID = "EMPRESA_ID";
        public static final String TRABAJADOR_ID = "TRABAJADOR_ID";
        public static final String ESTADO = "ESTADO";
    }
}
