package model;
/**
 *@author Alessandro
 *@since  struttura storico  
 *@since  int x;				coordinata x matrice icone pulsanti
*@since  int obj;         		pezzo presente a fine mossa 
*@since  int cobj;         		conversione da pedone 
*@since  int eatObj;     		se non -1 pezzo mangiato
*@since  int ceatObj;     		conversione da pedone
*@since  boolean wait;   		mossa in attesa destinazione
*@since  boolean close;  		mossa terminata
*@since  boolean print;   		stampa icona e movimento pezzo
*@since  boolean sm; 			sm false end false normale ; sm true end false stallo ; sm false end true scacco ; sm true end true scacco matto
*@since  boolean end;   		sm false end false normale ; sm true end false stallo ; sm false end true scacco ; sm true end true scacco matto
*@since  boolean color;       	colore bianco false nero true
*@since  boolean eat;         	consenso mangiare pezzo
*@since  boolean selectOk; 		check for delete
*@since  boolean promReq;       request prom pawn  
*@since  byte    valueProm;     valore assegnato per promozione    
 */

public class StHistory {
public byte x;					
public byte y;					
public byte obj;         		
public byte cobj;  
public byte eatObj;     			
public byte ceatObj;  
public boolean wait;   			
public boolean close;  			
public boolean print;   		
public boolean sm; 			
public boolean end;   			
public boolean color;       	
public boolean eat;         	
public boolean selectOk; 
public boolean promReq;
public byte    valueProm;
}
