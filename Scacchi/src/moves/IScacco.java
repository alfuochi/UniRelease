package moves;

import java.util.ArrayList;

import model.Default;
import model.Model;
import model.StAttach;
import model.StMove;

public  class IScacco extends IChess implements Scacco {
	/**
	 * @since  test per scacco 	 
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
	 * @since  test per scacco 	
	 */
	private ArrayList<StAttach> testScacco	(byte kingOnAttach,byte otherKing,boolean auto){
			ArrayList<StAttach> defence= new ArrayList<StAttach>();
			ArrayList<StAttach> alist = new ArrayList<StAttach>();
			alist =overrideAttach(kingOnAttach,auto);
			if(	alist.size() > 0  ) {
				defence= defenceAttach(otherKing,auto);
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
		ArrayList<StMove> Around =aroundTheKing( king);
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
			return ind;
		}
		/**
		 * @since  cerca una o piu' mosse di uscita per il re 
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
	private void setAroundTheKing(ArrayList<StMove> Around,byte king){
		byte value=0;
		for (byte n=0;n< Around.size();n++){
		    value=model.cntlAt(Around.get(n).x, Around.get(n).y);
			if (value != Default.posFree ){
				StMove e=Around.get(n);
				e.obj=value;
				Around.set(n, e);
			}
		}
	}

	/**
	 * @since  lista delle vie di fuga per il re		 
	 */
	private byte  isExitAroundTheKing(ArrayList<StMove> Around ){
	    byte ck=Default.posKill;
		for (byte n=0;n< Around.size();n++){
		if ( Around.get(n).obj ==Default.posFree ){
			ck=n;
			break;
		}
		}
		return ck;
	}

	/**
	 * @param king
	 * @return lista posizioni attorno al re
	 */
	private ArrayList<StMove> aroundTheKing(byte king){
		ArrayList<StMove>  Around= new ArrayList<StMove>();
		byte [] kingAt=whereIsCh(king);
		byte[][] v= {{-1,-1} ,{0,-1}, {+1,-1} ,{+1,0},{-1,0} ,{-1,+1},{0,+1} ,{+1,+1}};
		for (byte n=0; n< v.length;n++){
			if(model.xyOk((byte)(kingAt[0] + v[n][0]),(byte)(kingAt[1] + v[n][1]))){
				StMove e = new StMove();
				e.x=(byte )(kingAt[0] + v[n][0]);
				e.y=(byte)(kingAt[1] + v[n][1]);
				e.obj=Default.posFree;
				Around.add(e);
			}
			}
		return Around;
	}
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since  mossa di un pezzo
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
}
