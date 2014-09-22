package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.io.Serializable;

public class AtributosNeg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributos de Orden de Compra!# 
	 * 				y 
	 * 		Orden de Servicio!# 
	 */
	/*******Orden de Compra*********/
	public static String OC_ID="oc_id";
	public static String OC_FECHAIMP="oc_fechaimp";
	public static String OC_PREIMPRESOINI="oc_preimpresoini";
	public static String OC_SOLICITANTE="oc_solicitante";
	public static String OC_FECHA="oc_fecha";
	public static String OC_TOTAL="oc_total";
	public static String OC_ALMDIR="oc_almdir";
	public static String OC_ESTADO="oc_estado";
	public static String OC_PRECIOIGV="oc_precioigv";
	/*******Condiciones del proveedor*********/
	public static String COTPROV_GARANTIA="cotprov_garantia";
	public static String COTPROV_TIEMPO_ENTREGA="cotprov_tiempo_entrega";//Tiempo de entrega o Tiempo de ejecución
	public static String COTPROV_FORMA_PAGO="cotprov_forma_pago";
	
	/*******Cotizacion de Articulo*********/
	public static String COTART_MARCA="forma_pago";
	public static String COTART_DETALLE_OC="detalle_oc";//Para OC y OS
	public static String COTART_TIPO_MONEDA="tipo_moneda";
	public static String COTART_TIPO_PRECIO="tipo_precio";
	public static String COTART_PRECIO_UNIT="precio_unit";//Precio Unit (OC) o Precio Base (OS)
	public static String COTART_IMPUESTO="impuesto";
	public static String COTART_PRECIO_TOTAL="precio_total";
	
	
	//!-->Obtenidas de Logistica
	/******* Articulo *********/
	public static String ART_CODIGO="art_codigo";
	public static String ART_ESPECIFICA="art_especifica";//Especifica ingresada por el usuario ExpresionRegular
	public static String ART_DESCRIPCION="art_partida";
	
	/******* Proveedor *********/
	public static String PROVEEDOR_RUC="art_codigo";
	public static String PROVEEDOR_RAZSOC="art_partida";
	public static String PROVEEDOR_DIRECCION="art_partida";
	public static String PROVEEDOR_TELEFONO ="art_partida";
}

