package controller;

import java.awt.Color;
import java.util.ArrayList;

import osUtil.OsUtil;
import view.View;
import model.StMove;
import model.StAttach;
import model.StHistory;
import model.Model;
import model.Default;
import moves.*;


public class OnClickIm implements Controller{
	private final View view;
	private Moves moves;
	private Model model;
	private OsUtil osUtil;
	public OnClickIm(View view){
		this.view = view;
		this.moves=new moves.IMoves(view.getModel());
		this.model= view.getModel();
		this.osUtil= model.getOsUtil();
		model.testFileImage();
		osUtil.testJVM();
	}
	
	@Override
	public Moves getMoves(){
		return moves;
	}
	
	@Override
	public void onClick(byte x,byte y,byte value) {
		cntlProcess(x,y,value,(byte) 0);
	}
	
	@Override
	public void runDemo (int gameIndex ) { 
	   StMove[] sm ;
	   model.runDemo();
	   sm=convertDemo(gameIndex);
	   byte overWriteObj=(byte)0;
	   for( int ic=1;ic< sm.length;ic++){
			if(!sm[ic].overWrite){
				cntlProcess (sm[ic].x,sm[ic].y , sm[ic].obj ,overWriteObj);
				overWriteObj=Default.posFree;
			} else  {
				overWriteObj	= sm[ic].obj;
		}	
				osUtil.wait(Default.runDemoDelay);
		}
	}
	/**
	 * @ since determina se la fase attuale e' selezione o movimento
	 * @ since esegue validazioni fasi 
	 * @ since esegue richiese azioni e stampe
	 * @ since cancella record invalidi da storico azioni  
	 */
	private void cntlProcess (byte x,byte y ,byte value,byte overValue ) { 
		StHistory a=new StHistory();
		a.x=x;
		a.y=y;
		a.wait=false;
		a.close=false;
		a.end=false;
		a.eat=false;
		a.print=false;
		a.obj= model.at(x, y)[0];
		a.cobj=model.at(x, y)[1];
		if (moves.chExist(x, y)) a.color=model.colorAt(x, y);
		a.selectOk=false;
		a.valueProm=Default.posFree;
		model.addHistory(a);
		
		if ( moves.chExist(x, y) && ((model.sizeHistory() ==1 && model.colorAt(x, y) == model.getColor()) 	|| 
			( model.sizeHistory() > 2 && model.lastHistory(-2).close && model.lastHistory(-2).selectOk 
			&& ! model.lastHistory(-2).wait && model.isSelectState())) ) {
			selectCh(model.lastHistory(-1).x,model.lastHistory(-1).y,model.lastHistory(-1).obj);
			if (model.lastHistory(-1).selectOk ) model.setMoveState();
		}
		
		if (model.sizeHistory() >1 && (( model.lastHistory(-2).wait && ! model.lastHistory(-2).close 
			&& model.lastHistory(-2).selectOk &&  model.lastHistory(-2).color == model.getColor() && model.isMoveState()) 
			|| !(model.lastHistory(-2).close ))) {
					moveCh(model.lastHistory(-2).x,model.lastHistory(-2).y,model.lastHistory(-1).x,model.lastHistory(-1).y,
					model.lastHistory(-2).obj,model.lastHistory(-1).obj,overValue);
					if ((model.sizeHistory() > 0  && model.lastHistory(-1).selectOk) ) model.setSelectState();
		}
		
		if ( model.sizeHistory() > 0  &&  ! model.lastHistory(-1).selectOk ) {  
				model.removeHistory(model.sizeHistory()-1);
				model.setSelectState();
		}
			view.writeAddInfo(osUtil.memoryDesc() ,Color.GREEN  );
	}
	
		/**
		 * @since converte sequenza in comandi per esecuzione demo 
		 * @param index
		 * @return
		 */
		private StMove[] convertDemo(int index ){
			ArrayList<String[]> list= model.loadDemo();
			String[] mv=list.get(index);
			if (index >=list.size()) osUtil.printOutError(" Selezione test demo " ,-1,false);
			StMove[] s= new StMove[mv.length];
			for (int io=1;io< mv.length;io++){
				s[io]= new StMove();
				if (mv[io].toUpperCase().getBytes()[0] >= 'A' && mv[io].toUpperCase().getBytes()[0] <= 'H' ){
				s[io].y= model.stdYC(mv[io].substring(1,2).trim());
				s[io].x= model.stdXC(mv[io].substring(0,1).trim());
				s[io].obj=model.posFromXY(s[io].x,s[io].y);
				s[io].overWrite=false;
				}else{
					s[io].y= s[io-1].y;	
					s[io].x= s[io-1].x;	
				 	s[io].obj=(byte)(int) Integer.parseInt((String)mv[io]);
				    s[io].overWrite=true;
				}
			}
			return s;
		}
		/**
		 * @since verifica se il pezzo selezionato e' selezionabile e se ha mosse valide
		 */
	 	private void selectCh(byte x,byte y ,byte value){  	  
		  StHistory   	a =model.lastHistory(-1);
		  if(  model.getColor() == model.colorAt(x, y) ){
			  try {
				  a.wait=true;  
				  a.close=false; 
				  a.print=true;
				  a.eat=false;
				  a.obj= model.at(x, y)[0];
				  a.cobj= model.at(x, y)[1];
				  a.color=model.colorAt(x, y);
				  model.setHistory(model.sizeHistory()-1, a);
				  model.resetCntl();
				  view.writeMessage(" Selezionato " +Default.xCor[x]+"."+Default.yCor[y]+" - "+ model.getSColor(value) +"  " + Default.roulesTypes[value],Color.WHITE);
				  view.clearAllCenterBorder();
				  view.selCenterBorder(x, y);
				  moves.grantedAreaSelect();
				  view.markBorderArea();
				  if(  model.listMove().size() > 0)  {	
					  a =model.lastHistory(-1);
					  a.selectOk=true;
					  model.setHistory(model.sizeHistory()-1, a);
				  }} catch(Exception e){
					  e.printStackTrace();
					  System.exit(-1);
				  }
		  }
		  if  (!model.lastHistory(-1).selectOk) {
				  model.resetCntl();
		  }
	 	}
	 	
	 	/**
		 * @since verifica se la mossa e valida 
		 * @since chiede eventuale promozione pedone 
		 * @since chiede controlli scacco/matto ,regola50, e stallo
		 * @since chiede eventuali visualizzazioni eventi 
		 */
	 	private void moveCh(byte xFrom,byte yFrom ,byte xTo,byte yTo ,byte valueFrom,byte valueTo,byte overValue){  	  
		  StHistory   	a =model.lastHistory(-1);
		  if ( moves.cntlGrantedOrKilled(xTo, yTo)){
			  a.wait=false;  
			  a.close=true;
			  a.print=true;
			  a.color=model.colorAt(xFrom, yFrom);
			  a.obj=model.at(xFrom, yFrom)[0];
			  a.cobj=model.at(xFrom, yFrom)[1];
			  a.eatObj=valueTo;
			  a.eat=moves.isChValue(valueTo);
			  a.selectOk= true;
			  a.promReq=model.isPawnAgain(a.obj)&& ( a.y == 0 || a.y == 7 );
			  model.setHistory(model.sizeHistory()-1, a);
			  model.set(xTo,yTo,valueFrom);
			  model.set(xFrom,yFrom,Default.posFree);
			  model.changeColor();
			  rules50(a.obj,a.eat);
			  view.moveChD(xFrom,yFrom,xTo,yTo,valueTo,a.obj);
			  model.resetCntl();
			  osUtil.wait(Default.runDisplayDelay);
			  osUtil.beep();
			  cntlCheckStall( a);
			  if (a.promReq ) {
				  if(! model.isDemo())
					  model.setProm03(a.obj, view.reqProm(a.obj));
				  else
					  model.setProm(a.obj, overValue);
				  view.setIconChess(a.x,a.y,model.at(a.x, a.y)[1]);
				  StHistory   	b =model.lastHistory(-1);
				  b.valueProm=model.at(a.x, a.y)[1];
				  model.setHistory(model.sizeHistory()-1, b);
				  cntlCheckStall( a);
			  }
		  	}
	 	}   
 		  
	  /**
       * @since test scacco / matto / stallo 
       */
	  private void cntlCheckStall(StHistory   	a){
		  if (model.isPrintSysOut())  
			  System.out.println("   H["+model.sizeHistory()+"] (Dopo) X "+a.x+ "  Y " +a.y+"  CODE " +a.obj+ " WAIT "+a.wait+" Close "+a.close+"\n");
		  model.backUpAt();
		  ArrayList<StAttach> listEvent= moves.testScacco();
		  if (listEvent.size() > 0)  
			  view.printScacco(((StAttach) listEvent.get(listEvent.size()-1)).auto,((StAttach) listEvent.get(listEvent.size()-1)).sm,((StAttach) listEvent.get(listEvent.size()-1)).end, ((StAttach) listEvent.get(listEvent.size()-1)).king);
		  model.restoreAt(false);
		  if (listEvent.size() > 0 && listEvent.get(listEvent.size()-1).auto) 
			  {
				view.writeMessage(moves.rollbackMove(),Color.cyan);
				view.syncChessboard();
			  }
	  }
	 
	  /**
	   * @since regola 50
	   */
	  private void rules50(byte obj,boolean eat){
		  boolean color= model.colorCh(obj);
		  if (eat || model.isPawnAgain(obj)){ 
			  model.resetRoules50(color);
			  model.resetRoules50(!color);
    		} else model.setRoules50(color);	 
	  }
    

}
