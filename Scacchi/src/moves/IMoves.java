package moves;

import model.Model;
import model.Default;
import model.StHistory;

public class IMoves extends  moves.IScacco implements Moves{
	public IMoves(Model model){
		this.model= model;
	}
/**
 * @since  crea array di controllo per fase select 	 
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
 * @author Alessandro Fuochi (UNIVR) ID083311
 * @since  ritorno allo stato precedente l'ultima mossa 	 
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
 * @since  mossa di un pezzo
 */
@Override
public boolean  moveChessboard(byte xFrom,byte yFrom ,byte xTo,byte yTo){
	return moveChessboardCh( xFrom, yFrom,  xTo,  yTo);
}	
}