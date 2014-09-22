package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


public class Metodos {
	public static Locale local=new Locale("pe");
	private static NumberFormat formatoMoneda;
	static{
		formatoMoneda=NumberFormat.getNumberInstance(local);
		formatoMoneda.setMinimumFractionDigits(2);
		formatoMoneda.setMaximumFractionDigits(2);
	}
	/**
	 * @param numero: el numero a convertir
	 * @param nombreMoneda: el tipo de moneda. Ejemplo: NUEVO SOL
	 */
	public static String convertirNumeroLetras(BigDecimal numero, String nombreMoneda){
		Converter con =new Converter();
		con.setNombreMoneda(nombreMoneda);	
		return con.getStringOfNumber(numero);
		//System.out.println("clase 1"+con.getStringOfNumber(num)+"");
	}
 	public static String formatearDecimales(String numero,int decimales){
 		BigDecimal num=new BigDecimal(numero).setScale(decimales, RoundingMode.HALF_UP); 		
 		return num.toPlainString();
 	}
	public static String roundToStringDinero(BigDecimal d){	       
	    
		return formatoMoneda.format(d);
	 }
	public static int convertirEstadoToInt(String estado){
		//No puedo colocar case tipo String debido a que JDK 1.6 to need higher 1.6
		if(estado.equals("1")){
			return 1;
		}else if(estado.equals("2")){
			return 2;
		}else if(estado.equals("3")){
			return 3;
		}else if(estado.equals("4")){			
			return 4;
		}else{
			return -1;//ASDASF
		}		
	}
}
class Converter {
	private Integer counter = 0;
	private String value = "";
	private String nombreDeMoneda;

	public Converter() {
		nombreDeMoneda = "NUEVOS SOLES";
	}

	/*public String getStringOfNumber(Integer $num) {
		this.counter = $num;
		return doThings($num);
	}*/

	public void setNombreMoneda(String nombre) {
		nombreDeMoneda = nombre;
	}

	/** Con formato centavos/100MN **/
	public String getStringOfNumber(BigDecimal numero) {
		// this.counter = $num;
		BigDecimal parteEntera = numero.setScale(0, BigDecimal.ROUND_DOWN);
		BigDecimal parteDecimal = numero.subtract(parteEntera); // Almaceno la parte decimal
		// Redondeo y convierto a entero puedo tener problemas
		int fraccion = parteDecimal.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValueExact();
		String ceroDecimal="";
		if(!(fraccion>=10)){
			ceroDecimal="0";
		}
		CnvNumsLets nl=new CnvNumsLets();
		return nl.toLetras(parteEntera.longValueExact())+ "Y " + ceroDecimal+fraccion
		+ "/100"+ " " + nombreDeMoneda;
	}


}
/*
 *Clase para describir un numero 
 */
class CnvNumsLets
 { 
  private String Unidad[] = {"CERO","UNO","DOS","TRES","CUATRO","CINCO","SEIS","SIETE","OCHO","NUEVE","DIEZ","ONCE","DOCE","TRECE","CATORCE","QUINCE","DIECISEIS","DIECISIETE","DIECIOCHO","DIECINUEVE","VEINTE"};
  private String Decena[] = {"VENTI", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"};
  private String Centena[] ={"CIEN","DOSCIENTOS","TRESCIENTOS","CUATROCIENTOS","QUINIENTOS","SEISCIENTOS","SETECIENTOS","OCHOCIENTOS","NOVECIENTOS"," MIL","UN MILLON","MILLONES","UN BILLON","BILLONES"};
  private long Valororiginal=0;
 public CnvNumsLets()
	 {
	 	//constructoir vacio
	 }
  private String getUnidad(long Numero)
    {String aux="";
     for(int p=0;p<=20;p++)
         {if(Numero== p)
             { aux = Unidad[p] + " ";
              /* if( Numero ==1 & Valororiginal > 1000 )
                  return("Un ");*/
               return aux;
             }
         } 
      return "";
   }
  private String getDecena(long Numero)
  {String aux="";
   long pf=Numero%10;
   long pi=(Numero/10);
   int p=0;
   boolean sal=false;
  //pf: parte final del numero,pi: parte inicial del numero 
   while(p<=8 & sal==false)
       {if(pi== p+2)
           { aux = Decena[p]; 
             sal=true;
           }
          p++; 
       }
    if (pf==0)   
       return aux+" ";
    if(Numero>20 & Numero<30)   
      return(aux+getUnidad(pf)+"");
   return aux+" Y "+getUnidad(pf)+"";   
 }
private String getCentena(long Numero)	  	 
  {String aux="",aux2="";
   long pf=Numero%100;
   long pi=(Numero/100);
   int p=0;
   boolean sal=false;
   while(p<=10 & sal==false)
       {if(pi== p+1)
           { aux = Centena[p]; 
             sal=true;
           }
          p++; 
       }
    if (pf==0)   
       return aux;
    if (pf<21)   
       aux2=getUnidad(pf);
    else
       aux2=getDecena(pf);   
    if (Numero<200)   
       return(aux+"TO "+aux2+" ");
    else
       return(aux+" "+aux2+" ");
  }
  
 private String getMil(long Numero)	  	 
  { String aux="",aux2="";
    long pf=Numero%1000;
    long pi=(Numero/1000);
    long p=0;
    if(Numero==1000) return " MIL";
    if (Numero>1000 & Numero < 1999)
        aux=Centena[9]+" ";
    else
     	 aux=resolverIntervalo(pi)+Centena[9]+" ";
    if (pf!=0)   
       return(aux+resolverIntervalo(pf)+" ");
    return aux;  
   } 
 private String getMillon(long Numero)	  	 
  { String aux="",aux2="";
    long pf=Numero%1000000;
    long pi=(Numero/1000000);
    long p=0;
    if (Numero>1000000 & Numero < 1999999)
        aux=Centena[10]+" ";
    else
     	 aux=resolverIntervalo(pi)+Centena[11]+" ";
    if (pf!=0)   
       return(aux+resolverIntervalo(pf)+" ");
    return aux;  
   } 
 private String getBillon(long Numero)	  	 
  { String aux="",aux2="";
    long pf=Numero%1000000000;
    long pi=(Numero/1000000000);
    long p=0;
    if (Numero>1000000000 & Numero <1999999999)
        aux=Centena[12]+" ";
    else
     	 aux=resolverIntervalo(pi)+Centena[13]+" ";
    if (pf!=0)   
       return(aux+resolverIntervalo(pf)+" ");
    return aux;  
   }   
 private String resolverIntervalo(long Numero)
  {  if(Numero>=0 & Numero<=20) 
        return getUnidad(Numero);
     if(Numero>=21 & Numero<=99) 
        return getDecena(Numero);
     if(Numero>=100 & Numero<=999) 
        return getCentena(Numero);
     if(Numero>=1000 & Numero<=999999) 
        return getMil(Numero);
     if(Numero>=1000000 & Numero<=999999999) 
        return getMillon(Numero);            
     if(Numero>=1000000000 & Numero<=2000000000) 
        return getBillon(Numero);
     return "<<El numero esta fuera del rango>>";   
  }  
 public String toLetras(long Numero)  
   { Valororiginal=Numero;
      if(Numero>=0) {
    	  String numeroLetras=resolverIntervalo(Numero);
    	  //int tam=numeroLetras.length();
          return(numeroLetras);
      }
    	 
      else{
    	  String numeroLetras=resolverIntervalo(Numero*-1);
    	 // int tam=numeroLetras.length();
    	  return(" Menos "+numeroLetras);   
      }
    	  
   }

	public boolean pausa(long p) {
		try {
			Thread.sleep(p);
		} catch (Exception e) {
		}
		return true;
	}
	
	public static String obtenerRutaRaiz(HttpServletRequest request){
		String rutaRaiz;
		rutaRaiz=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		return rutaRaiz;
	}
 }

