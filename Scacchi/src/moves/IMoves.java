package moves;


import java.util.ArrayList;

import model.Model;
import model.StAttach;
import model.Default;
import model.StHistory;

public class IMoves extends  moves.IChess implements Moves{
	public IMoves(Model model){
		this.model= model;
	}
/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since make a cntlChess control in select step	 	 
 */
@Override
	public void grantedAreaSelect() {
		if (model.lastHistory(-1).wait && ! model.lastHistory(-1).close ){
			byte x=model.lastHistory(-1).x;
			byte y=model.lastHistory(-1).y;
			grantedArea(  model.lastHistory(-1).obj,x,y);
		}
		}
/**
 *@author Alessandro Fuochi (UNIVR) ID083311
 * @since make a cntlChess control in move step	 
 */
@Override
	public void grantedAreaMove() {
		if (!model.lastHistory(-1).wait && model.lastHistory(-1).close ){
			byte x=model.lastHistory(-1).x;
			byte y=model.lastHistory(-1).y;
			grantedArea(   model.lastHistory(-1).obj,x,y);
		}
	}
/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  return at 'before' of chess move 	 
 */
@Override public String rollbackMove(){
	String ms="";
		if(model.sizeHistory() > 1){
			boolean color;
			StHistory r2=model.lastHistory(-2);
			StHistory r1=model.lastHistory(-1);
			color = model.colorCh(r2.obj);
		 	r2.obj=r1.obj;
			r1.obj=r1.eatObj;
			model.set(r1.x,r1.y,r1.obj);
		    model.set(r2.x,r2.y,r2.obj);
		    model.setColor(color);
		    model.setSelectState();
		    ms="RollBack  Destinazione  pezzo " +Default.xCor[r2.x]+"."+Default.yCor[r2.y]+"  Mossa successiva al " + model.getSColor() ;
		}
		return ms;
	}
/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  test for scacco 	 
 */
@Override
public ArrayList<StAttach> testScacco(){
	ArrayList<StAttach> alist=new ArrayList<StAttach>();
	byte chess = model.lastHistory(-1).obj;
		byte kingOnAttach=Default.whiteKing;
		byte otherKing= Default.blackKing;
		if (model.colorCh(chess) == model.colorCh(Default.whiteKing)) {
			 kingOnAttach=Default.blackKing;
			 otherKing= Default.whiteKing;
		}
		alist=	testScacco	(kingOnAttach,otherKing,false);
		if ( alist.size() == 0)  alist=	testScacco	(otherKing,kingOnAttach,true);
		return alist;
}

/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  move a chess
 */
@Override
public boolean  moveChessboard(byte xFrom,byte yFrom ,byte xTo,byte yTo){
	boolean othColor;
	boolean canMove=true;
	boolean myColor=model.colorAt(xFrom,yFrom);
	if (model.at(xTo,yTo)[0]> -1){
	 othColor=model.colorAt(xTo,yTo);
	 if (myColor == othColor)
	 return false;
	}
	model.set(xTo,yTo,model.at(xFrom,yFrom)[0]);
	model.set(xFrom,yFrom,(byte)-1);
	return canMove;
}	
/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  test for scacco 	(one step)
 */
private ArrayList<StAttach> testScacco	(byte kingOnAttach,byte otherKing,boolean auto){
		ArrayList<StAttach> defence= new ArrayList<StAttach>();
		ArrayList<StAttach> alist = new ArrayList<StAttach>();
		alist =overrideAttach(kingOnAttach,auto);
		if(	alist.size() > 0  ) {
			defence= defenceAttach(otherKing,auto);
			printDefenceKing( defence );
		}
		for (byte i=0;i< defence.size();i++ )
			alist.add(((StAttach)defence.get(i)));
		return alist;
}

/**
 * @since  test per scacco,scacco matto,stallo	 
 */
	private ArrayList<StAttach> overrideAttach(byte king,boolean auto){
	ArrayList<StAttach> ind=new ArrayList<StAttach>(); 
	byte [] chAt;
	byte [] kingAt=whereIsCh(king);
	byte [] range=model.rangeAt(king,true);
	ArrayList<around> Around =aroundTheKing( king);
	while (range[0] <= range[1]){
    	if (range[0] != Default.blackKing && range[0] !=  Default.whiteKing){
    			chAt=whereIsCh(range[0]);
    		if (chAt[0] > -1 && chAt[1] > -1)    	{
    			grantedArea(range[0],  chAt[0],chAt[1]);
    			setAroundTheKing( Around, king);
	    		if (model.cntlAt(kingAt[0],kingAt[1])==Default.posKill) {
	     			StAttach s= new StAttach();
    				s.sm		=	true;
    				s.end		= 	true;
    				s.type		= 	false;
    				s.chAttach	= 	range[0];
    				s.king		= 	model.colorCh(king);
    				s.levelAttach= model.cntlAt(kingAt[0],kingAt[1]);
    				s.auto=auto;
    				ind.add(s);
    			}
    		}
    	}
    	range[0]++; 
     	}
		if( isExitAroundTheKing( Around ) == Default.posKill && numberOfChess(model.colorCh(king))< 2  ){ 
			model.setStall(true);
			StAttach s= new StAttach();
			s.sm= false;
			s.end= true;
			s.type= false;
			s.chAttach= range[0];
			s.king= model.colorCh(king);
			s.levelAttach= model.cntlAt(kingAt[0],kingAt[1]);
			s.auto=auto;
			ind.add(s);
		}
		printAroundTheKing(Around );
		return ind;
	}
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since  found one or more defense of king	 
	 */
	private ArrayList<StAttach> defenceAttach(byte king,boolean auto){
		model.restoreAt(true);
		byte otherKing=Default.blackKing;
		if (king ==Default.blackKing ) otherKing=Default.whiteKing;
		ArrayList<StAttach> aqlist=new ArrayList<StAttach>();
		ArrayList<StAttach> defence=new ArrayList<StAttach>();
		byte [] kingAt=whereIsCh(king);
		byte [] chAt;
		byte [] range=model.rangeAt(king, true);
		while (range[0]<= range[1]){
		    	 	chAt=whereIsCh(range[0]);
		    		if (chAt[0] > -1 && chAt[1] > -1)    	{
		    			model.resetCntl();
		    			grantedArea(range[0], chAt[0],chAt[1]);
		    			ArrayList<byte[]> lm=model.listMove();
		    			byte index=0;
		    			while (index < lm.size()){
		    				byte [] d=lm.get(index);
		    				moveChessboard(chAt[0],chAt[1],d[0],d[1]);
		    				aqlist =overrideAttach(otherKing,auto);
		   				if (aqlist.size() ==  0) {
		    					StAttach s= new StAttach();
		        				s.type=true;
		        				s.sm=true;
		        				s.end= false;
		        				s.chAttach= range[0];
		        				s.king= model.colorCh(otherKing);
		        				s.levelAttach= model.cntlAt(kingAt[0],kingAt[1]);
		        				s.auto=auto;
		        	    		defence.add(s);
		    				}
		    				model.restoreAt(true);
		    				aqlist.clear();
		    				index++;
		    			}
		    		}
		    		range[0]++; 
		     }
		model.restoreAt(true);
		return defence;
}
/**
 * @since  imposta la lista di attacco al re	 
 */
private void setAroundTheKing(ArrayList<around> Around,byte king){
	byte value=0;
	for (byte n=0;n< Around.size();n++){
	    value=model.cntlAt(Around.get(n).x, Around.get(n).y);
		if (value != Default.posFree ){
			around e=Around.get(n);
			e.chess=value;
			Around.set(n, e);
		}
	}
}

/**
 * @since  lista delle vie di fuga per il re		 
 */
private byte  isExitAroundTheKing(ArrayList<around> Around ){
    byte ck=Default.posKill;
	for (byte n=0;n< Around.size();n++){
	if ( Around.get(n).chess ==Default.posFree ){
		ck=n;
		break;
	}
	}
	return ck;
}
/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  print so list type <Around>	 
 */
private void  printAroundTheKing(ArrayList<around> Around ){
	if (model.isPrintSysOut())
	for (byte n=0;n< Around.size();n++){
		System.out.println("*  x ["+ Around.get(n).x +"] y ["+ Around.get(n).y +"] value "+ Around.get(n).chess +"" );
	}
}

/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  print so list type <Around>	 
 */
private void  printDefenceKing(ArrayList<StAttach> defence ){
	 if (model.isPrintSysOut()){
		 for (byte i=0;i< defence.size();i++){
			 System.out.println("\n\n\n Scacco at " + ((StAttach) defence.get(i)).king 
		 			+" resolve ("+ i +") with "+((StAttach) defence.get(i)).chAttach + " ");
		 }
	 }
}
	
/**
 * @param king
 * @return lista posizioni attorno al re
 */
private ArrayList<around> aroundTheKing(byte king){
	ArrayList<around>  Around= new ArrayList<around>();
	byte [] kingAt=whereIsCh(king);
	byte[][] v= {{-1,-1} ,{0,-1}, {+1,-1} ,{+1,0},{-1,0} ,{-1,+1},{0,+1} ,{+1,+1}};
	for (byte n=0; n< v.length;n++){
		if(model.xyOk((byte)(kingAt[0] + v[n][0]),(byte)(kingAt[1] + v[n][1]))){
			around e = new around();
			e.x=(byte )(kingAt[0] + v[n][0]);
			e.y=(byte)(kingAt[1] + v[n][1]);
			e.chess=Default.posFree;
			Around.add(e);
		}
		}
	return Around;
}
/**
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since model around data	 
 */
private class around{
	byte x;
	byte y;
	byte chess;
}


}