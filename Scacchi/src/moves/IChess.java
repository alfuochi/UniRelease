package moves;


import java.util.ArrayList;

import model.Default;
import model.Model;
public class IChess  implements Chess {
	protected Model model;

@Override
public void grantedArea(byte  obj,byte x,byte y) {
		model.resetCntl();
		switch(Default.roulesTypes[(byte) model.getConvProm(obj)]){
			case "p":
				pawn(x,y);
				break;
			case "h":
				knight(x,y);
				break;
			case "b":
				bishop(x,y);
				break;
			case "r":
				rook(x,y);
				break;
			case "q":
				queen(x,y);
				break;
			case "k":
				king(x,y);
				break;
			default:
				System.out.println("([grantedArea] ERROR )      chess " + obj +" not fnd !!!!!!!!!!!");
				System.exit(-99);
				break;
		}        
}

@Override
public boolean chExist(byte x,byte y){
	return (model.at(x,y)[0] != Default.posFree);
}

@Override
public boolean isChValue(byte chess){
	return (chess >= Default.startBlack && chess <= Default.endWhite);
}

@Override
public boolean cntlGrantedOrKilled(byte x,byte y){
	return model.cntlAt(x,y) <= Default.posGranted;
}

@Override
public byte[] whereIsCh(byte ch){
	model.checkCh(ch);
	byte [] pos = new byte[2];
	pos[0]=-1;
	pos[1]=-1;
	for (byte y=0;y< Default.lY;y++)
	   for (byte x=0;x<Default.lX ;x++){
		  if( model.at(x, y)[0] == ch){
			  pos[0]=x;
			  pos[1]=y;  
			  break;
		  }
	 }
	return	pos;
}

@Override
public void loadChessboard(){  
    byte ch=0; 
	for (byte n=0 ;n< 64;n++){
	   byte [] p= model.xyFromPos(n);
	   model.set(p[0],p[1],Default.posFree);
	   if (n>=Default.startPBlack && n<=Default.endPBlack){
	  	   p= model.xyFromPos(n);
	  	  model.set(p[0],p[1],ch++);
	   }
	   if (n>=Default.startPWhite && n<=Default.endPWhite){
		    p= model.xyFromPos(n);
		   model.set(p[0],p[1],ch++);
	   }
  }
  }

@Override
public byte numberOfChess(boolean color){
	byte nc=0;
		for(byte y=0;y<Default.lY;y++)
			for(byte x=0;x<Default.lX;x++)
				if (model.at(x, y)[0]!=Default.posFree)
					if (model.colorAt(x, y)== color) nc++;
	return nc;
}


protected ArrayList<Byte>  listOfChess(boolean color){
	ArrayList<Byte> nc= new ArrayList<Byte>();
		for(byte y=0;y<Default.lY;y++)
			for(byte x=0;x<Default.lX;x++)
				if (model.at(x, y)[0]!=Default.posFree)
					if (model.colorAt(x, y)== color) nc.add(model.at(x,y)[0]);
	return nc;
}

/**
 * @since pedone
 */
private void pawn(byte x,byte y){
	base_cross_nor((byte)1,true,x,y);
	base_dia_nor ((byte)1,true,x,y);
}
/**
 * @since cavallo
 */
private void knight(byte x,byte y){
	model.cntlSet(x, y,Default.posBusy);
	byte[][][] v={{{+1,+2},{-2,+0}},{{+1,-2},{-2,-0}},{{+2,+1},{+0,-2}},{{-2,1},{+0,-2}}};
	cCntl[] cc= gCord(x,y,(byte)4);
	byte i=0;
	while(i< cc.length){
			cc[i].xd+=v[i][0][0]; 
			cc[i].yd+=v[i][0][1]; 
			knightE (cc, i,x,y);
			cc[i].xd+=v[i][1][0];
			cc[i].yd+=v[i][1][1]; 
			knightE (cc, i,x,y);
			i++;
	}
}
/**
 * @since alfiere
 */
private void bishop(byte x,byte y){
	base_dia_nor ((byte)8,false,x,y);
}

/**
 *@since torre 
 */
private void rook(byte x,byte y){
	 base_cross_nor ((byte)8,false,x,y);
}
/**
 * @since regina
 */
private void queen(byte x,byte y){
	base_cross_nor ((byte)8,false,x,y);
	 base_dia_nor ((byte)8,false,x,y);
}
/**
 * @since re
 */
private void king(byte x,byte y){
	base_cross_nor ((byte)1,false,x,y);
	base_dia_nor ((byte)1,false,x,y);
}

private void base_cross_nor (byte len ,boolean only_forword,byte x,byte y) {
	model.cntlSet(x, y,Default.posBusy);
	byte[][] v={{0,1},{0,-1},{1,0},{-1,0}};
	cCntl[] cc= gCord(x,y,(byte)v.length);
	byte i=0;
	while(i< len){
	if ( only_forword) {
		if (! model.colorAt(x, y))
			cc[0].yd-=1; 
		else
			cc[0].yd+=1;
		setGrantedPosFree(cc[0].xd,cc[0].yd);	
		} else{
			byte li=0;
			while(li < v.length){
				cc[li].xd+=v[li][0];
				cc[li].yd+=v[li][1]; 
				base_cross_norE (  cc, li,x,y);
				li++;
			}
		}
		i++;
	}
}

private void base_cross_norE (cCntl[] cc,byte index,byte x,byte y){
	if (model.xyOk(cc[index].xd,cc[index].yd) && cc[index].run  ){
		if ( posFree(cc[index].xd,cc[index].yd))  {
			model.cntlSet(cc[index].xd,cc[index].yd,Default.posGranted);
		} else  {
			if ( posHeating(cc[index].xd,cc[index].yd,x,y))
			{	
				model.cntlSet(cc[index].xd,cc[index].yd,Default.posKill);
			}
				cc[index].run=false; 
			}
		}
}

private void base_dia_nor (byte len,boolean only_eat,byte x,byte y ) {
	byte c=4;
	byte[][] vNoEat={{1,1},{1,-1},{-1,1},{-1,-1}};
	byte[][] vEat={{1,1},{-1,1},{1,-1},{-1,-1}};
	byte [][] v= vNoEat.clone();
	model.cntlSet(x,y,Default.posBusy);
	cCntl[] cc= gCord(x,y,(byte) v.length);
	byte i=0;
	byte lis=0;
	byte lie=c;
	while(i< len){
		lis=0;
		lie=c;
		if ( only_eat ){
			v=vEat.clone();
			lis=0;
			lie=2;
			if (! model.colorAt(x, y)){
				lis=2;
				lie=4;
			}
		}
		while(lis< lie){
				cc[lis].xd+=v[lis][0];
				cc[lis].yd+=v[lis][1]; 
				base_dia_norE (   cc,lis,only_eat,x,y);
				lis++;
				}
		
		i++;
	}
}

private void base_dia_norE (cCntl[] cc,byte index,boolean only_eat,byte xFrom,byte yFrom){
	if (model.xyOk(cc[index].xd,cc[index].yd) && cc[index].run  ){
	if ( posFree(cc[index].xd,cc[index].yd)  && ! only_eat)  {
		model.cntlSet(cc[index].xd,cc[index].yd,Default.posGranted);
	} else  {
		if ( posHeating(cc[index].xd,cc[index].yd,xFrom,yFrom))	{	
			model.cntlSet(cc[index].xd,cc[index].yd,Default.posKill);
		}
		cc[index].run=false; 
	}
	}
}

/**
*@since cavallo  gestione percorsi mosse 
*/
private void knightE (cCntl[] cc,byte index,byte xFrom,byte yFrom){
	if (model.xyOk(cc[index].xd,cc[index].yd)){
	    if (!chExist(cc[index].xd, cc[index].yd)) 	model.cntlSet(cc[index].xd, cc[index].yd,Default.posGranted);
		if (posHeating(cc[index].xd, cc[index].yd,xFrom,yFrom))	model.cntlSet(cc[index].xd, cc[index].yd,Default.posKill);
	}
}

private class cCntl{
	byte xd ;
	byte yd;
	boolean run;
}
/**
 * @since crea matrice controllo coordinate
 */
private cCntl[] gCord(byte x,byte y,byte len){
    cCntl[] c= new cCntl[len];
	byte i=0;
    while(i< c.length){
    	c[i]= new cCntl();
    	c[i].xd=x;
    	c[i].yd=y;
    	c[i].run= true;
    	i++;
    }
    return c;
}

private boolean posFree(byte x,byte y){
	return  (model.at(x,y)[0]== Default.posFree);
}
/**
 * @since verifica se e' possibile mangiare pezzo nella posizione
 */
private boolean posHeating(byte xTo,byte yTo,byte xFrom,byte yFrom){
    if (!chExist(xTo,yTo)) return false;
    return model.colorAt( xFrom,yFrom)!= model.colorAt(xTo,yTo);
}

/**
 * @since imposta libera la posizione se permesso e riporta risultato
 */
private boolean setGrantedPosFree(byte x,byte y){
	boolean test= false;
	if	( model.xyOk(x,y) && posFree(x,y)){ 
			model.cntlSet(x,y,Default.posGranted);
			test=true;
	}
	return test;
}

}
