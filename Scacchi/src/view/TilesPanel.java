package view;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import osUtil.OsUtil;
import moves.*;
import model.Default;
import model.Model;
import controller.*;


public class TilesPanel extends JPanel implements View {
	private static final long serialVersionUID = 1L;
	private Model model;
	private Controller controller;
	private Moves moves;
	private OsUtil osUtil;
	
	private JPanel[] pNorth;
	private JPanel[] pSouth;
	private JPanel[] pWest;
	private JPanel[] pEast;
	private JPanel[] pCenter;
	private JButton[][] centerButtons ;
	private JButton[][] eastButtons ;
	private JButton[][] westButtons ;
	private JLabel[] northLabel ;
	
	private JButton restart,demo,savePlay; 
    private JComboBox<String> game;
	private JTextArea tarea,addInfo;
	private JFrame frame;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Info / Exit");
	private JMenuItem[] mItem;
	private String[] itemTitle ={"Manual","Exit"};
	private boolean[] awtSupported ={false,true};
	
	public TilesPanel(Model model,JFrame frame){
		this.frame=frame;
		this.model=model;
		model.testFileImage();
		moves=new moves.IMoves(this);
		controller= new controller.OnClickIm(this);
		osUtil=model.getOsUtil();
		createPanel();
		ldStart();
	}

	/**
	 * @since ricarica gioco
	 */
	@Override
	public void ldStart( ){
	   try {
		   model.resetAll();
		   moves.loadChessboard();
		   clearAllCenterBorder();
		   syncChessboard();
		   reloadEat();
		   reloadCord();
		   writeAddInfo( "Memoria usata jvm  " + osUtil.memory()+" KB (circa) ",Color.GREEN  );
		   model.printM( " New game ........ ");
		   writeMessage("Apre il " + model.sColorNow(),Color.WHITE);
		   reloadDemoList();
		   frame.setVisible(true);
		   frame.validate();
	   }catch (Exception ex){
			 ex.printStackTrace();
			 System.exit(-9);
	   }
	}
    /**
     * @since visualizzazione mossa  
     */
	@Override
	public void moveChD(byte xFrom,byte yFrom,byte xTo,byte yTo,byte eat,byte moved){
	   String desc=" - not exist - ";
	   if(moved > -1)  desc=Default.roulesTypes[moved];
	   if ( eat != -1) { 
		  ldIconCe (eat);
	   }
	   setIcon (centerButtons[yTo][xTo],model.getConvProm(model.at(xTo, yTo)[0]));
	   model.printM( " [moveCh] >>> "+ moved+" eat " +eat);
	   clrIcon(centerButtons[yFrom][xFrom]);
	   String ms="  Destinazione  pezzo " +Default.xCor[xTo]+"."+Default.yCor[yTo]+"  " + desc ;
	   ms+= "\n Mossa successiva al " + model.sColorNow() +"   Regola 50 (w/b) [" +model.getRoules50()[1] + " , " +model.getRoules50()[0]+"]";
	   writeMessage(ms,Color.WHITE);
	   clearAllCenterBorder();
	} 
   /**
    * @since richiesta promozione pedone
    */
	@Override
	public byte reqProm(byte chess){    
	   Icon errorIcon = UIManager.getIcon("OptionPane.warningIcon");  
       String[] possibilities=new String[Default.promRange];
       for(int n=0;n<possibilities.length;n++)
       possibilities[n]=Default.itLabels[n];
       return  (byte) JOptionPane.showOptionDialog(null,  " Richiesta promozione ",  "Scacchi", JOptionPane.PLAIN_MESSAGE, 1,  errorIcon, possibilities, 0);
	} 
     /**
      * @since stampa tipo scacco / matto / stallo / patta regola 50
      */
	@Override
	public void printScacco(boolean auto,boolean sm,boolean end,boolean king){    
		int sn=-1;  
		String re= " bianco ";
		if (king) re="nero";
		if (auto) re+="\n\nUltima mossa ha generato auto scacco verrà annullata ";
		if (sm && !end)  {
			   tarea.setText(" SCACCO ");
			   tarea.setBackground(Color.yellow);
			   JOptionPane.showMessageDialog(null, " Scacco al re " + re, "Scacchi", JOptionPane.INFORMATION_MESSAGE);
		   }
		   if ( !sm && end)  {
			   sn=1; 
			   tarea.setText(" STALLO ");
			   tarea.setBackground(Color.ORANGE);
			   sn = JOptionPane.showConfirmDialog(null, "STALLO !!\n\n Nuova partita (SI) o esci (NO) ?", "Scacchi", JOptionPane.YES_NO_OPTION);
		  }
		   if ( sm && end)  {
			   sn=1;
			   tarea.setText(" SCACCO MATTO ");
			   tarea.setBackground(Color.red);
			  if (auto)
				   JOptionPane.showMessageDialog(null, " Scacco al re " + re, "Scacchi", JOptionPane.INFORMATION_MESSAGE);
			  else{
				  Icon errorIcon = UIManager.getIcon("OptionPane.warningIcon");  
			       String[] possibilities= {"Nuova Partita","Nuova Partita e Salva partita","Esci"};
			        sn = (Integer) JOptionPane.showOptionDialog(null,  " Scacco matto ",  "Scacchi", JOptionPane.PLAIN_MESSAGE, 1,  errorIcon, possibilities, 0);
			  }
			  }
		   if (model.isPattaRoules50()) {
				  Icon errorIcon = UIManager.getIcon("OptionPane.warningIcon");  
			       String[] possibilities= {"Nuova Partita","Nuova Partita e Salva partita","Esci"};
			        sn = (Integer) JOptionPane.showOptionDialog(null,  " Patta (Regola 50) ",  "Scacchi", JOptionPane.PLAIN_MESSAGE, 1,  errorIcon, possibilities, 0);
		
		   } 
		   if (sn == 0)  ldStart();
		   if (sn == 1){
			   String name=JOptionPane.showInputDialog("Nome partita ");
			   model.savePlay( name);
			   ldStart();
		   }
		   if (sn==2 && !auto)   System.exit(0);
	  }   
    /**
     * @ since stampa selezione pezzi da array di controllo 
     */
	@Override
	public void markBorderArea(){
		 try {
			   for (byte y=0;y<  Default.lY;y++)
	 				for (byte x=0;x< Default.lX ;x++) {
	 					if ( model.cntlAt(x,y)== Default.posGranted)
	 						centerButtons[y][x].setBorder( BorderFactory.createBevelBorder(1,Default.grantedchess[0],Default.grantedchess[1]));
	 					if ( model.cntlAt(x,y)== Default.posKill)
	 						centerButtons[y][x].setBorder( BorderFactory.createBevelBorder(1,Default.killchess[0],Default.killchess[1]));
	 				}
		 }catch (Exception ex){
			ex.printStackTrace();
			System.exit(-10);
		 }}   
  /**
   * @since azzera colori bordi su scacchiera
   */
   @Override
   public void clearAllCenterBorder(){
		  try {
			   for (int y=0;y< Default.lY;y++)
				for (int x=0;x<Default.lX ;x++)
					setBorder(centerButtons[x][y],Color.WHITE,Color.WHITE);
		  }catch (Exception ex){
				 ex.printStackTrace();
				   System.exit(-1);
		}}
   /**
    * @since stampa array scacchiera su scacchiera
    */
   	@Override
   	public void syncChessboard(){
	   for (byte y=0;y<  Default.lY;y++)
		   for (byte x=0;x< Default.lX ;x++){
			   boolean t=false;
			   if (((y/2*2) == y)){
				   t=false;
				   if ( (x/2 *2) < x )  t=true;
			   } else{
				   t=true;
				   if ( (x/2 *2) < x )  t=false; 
			   }
			   if (t)  centerButtons[y][x].setBackground(Color.BLACK);
		       else    centerButtons[y][x].setBackground(Color.WHITE);
			   
			   if (model.at(x, y)[0] == -1 ) {
				   centerButtons[y][x].setIcon(null);
			   } else {
				   setIcon(centerButtons[y][x],model.getConvProm(model.at(x, y)[0]));
			   }
		}
   	}
    /**
     * @since stampa messaggi su casella mosse e cambia colore bordo
     */
   @Override 
   public void writeMessage( String m,Color c){
	   tarea.setText(m);
       tarea.setBackground(c);
   }
   /**
    * @since stampa messaggi su casella info addizionali e cambia colore bordo
    */
   @Override
   public void writeAddInfo( String m,Color c){
	   addInfo.setText(m);
       addInfo.setBackground(c);
   }
  
   @Override
   public void ldIcon(byte chess,byte x,byte y){
	   try {
		   setIcon(centerButtons[y][x], chess);
	   } catch (Exception ex) {
			   ex.printStackTrace();
			   System.exit(-9);
	   }}
   
   @Override
   public void ldIconCe(byte chess){
	   try {
		   if (model.colorCh(chess) )  setIcon(eastButtons[chess][0],chess);
		   else   setIcon(westButtons[chess-16][0],chess);
		   } catch (Exception ex) {
			   ex.printStackTrace();
			   System.exit(-9);
	}}
 
   @Override
   public void clrIconCe(byte chess){
	   try {
		    if (model.colorCh(chess) ) 	setIcon(eastButtons[0][chess],chess);
		   else 			   setIcon(westButtons[0][chess],chess);
		   } catch (Exception ex) {
			   ex.printStackTrace();
			   System.exit(-1);
   }}
   
   @Override
	public Model getModel() {
		return model;
	}
   
   @Override
 	public Controller getController() {
 		return controller;
 	}
   
   @Override
 	public Controller getConfiguration() {
 		return getConfiguration();
 	}
   
   @Override
	public void setController(Controller controller) {
		this.controller=controller;
	}
   
   @Override
   public void selCenterBorder(byte x,byte y){
  	 setBorder(centerButtons[y][x], Default.busychess[0], Default.busychess[1]);
   }
   
   /**
    * @since  crea thread per sequenze demo 
    */
   class lDemo extends Thread { 
      public void run() {
   	   demo.setEnabled(false);
   	   restart.setEnabled(false);
   	   savePlay.setEnabled(false);
   	   ldStart();
   	   controller.runDemo(game.getSelectedIndex());
   	//   ldStart();
   	   savePlay.setEnabled(true);
   	   restart.setEnabled(true);
   	   demo.setEnabled(true);
      }
  }
   /**
    * @since crea pannello completo
    */
      private void createPanel(){
   		 	frame.setSize(Default.frame_w,Default.frame_h);
   			frame.setLayout(new BorderLayout());
   		 	frame.setTitle(Default.title);
   			frame.setIconImage(osUtil.ldImage(osUtil.defaultAppDir(true)+"starticon"+Default.iconType));
   			menuBar.add(menu);
   			mItem= new JMenuItem[itemTitle.length];
   			
   			for(int n=0;n<itemTitle.length; n++){
   				if (Desktop.isDesktopSupported() || awtSupported[n]) {
   				mItem[n]=new JMenuItem(itemTitle[n]);
   				menu.add(mItem[n]);
   				}
   			}
   			
   			menuBar.setBackground(Color.green);
   			frame.setJMenuBar(menuBar);
   			
   			mItem[0].addActionListener(event -> {
   					   ldPdf(Default.manualName+Default.pdfType);
   			});
   			
   			mItem[1].addActionListener(event -> {
   		    	   System.exit(0);
   			});
   			
   			pNorth= new JPanel[4]; 
   		    makePanel(pNorth,0,false,0,0,Default.frame_w, Default.frame_h/24,Color.WHITE);
   		    makePanel(pNorth,1,false,0,0,Default.frame_w*2/32, Default.frame_h/64,Color.WHITE);
   		    makePanel(pNorth,2,true,1,8,Default.frame_w*28/32, Default.frame_h/64,Color.WHITE);
   		    makePanel(pNorth,3,false,0,0,Default.frame_w*2/32, Default.frame_h/64,Color.WHITE);
   			
   		    northLabel=new JLabel[10];
   		    
   		    pNorth[0].add(pNorth[1], 	BorderLayout.EAST);
   		    pNorth[0].add(pNorth[2], 	BorderLayout.CENTER);
   		    pNorth[0].add(pNorth[3], 	BorderLayout.WEST);				
   		 
   		    pCenter= new JPanel[1]; 
   		    centerButtons=new JButton[Default.lY][Default.lX];
   		    makePanel(pCenter,0,true,Default.lX,Default.lY,Default.frame_w*28/32, Default.frame_h*5/6,Color.BLUE);
    		addButtons(pCenter[0],centerButtons);
   		    addButtonCenterListener();
   		   
   		    pSouth=new JPanel[1];
   		    makePanel(pSouth,0,true,2,3,Default.frame_w, Default.frame_h*3/24,Color.GREEN);
		    
   			tarea= new  JTextArea(Default.frame_w*1/3,Default.frame_h/(32));
   			tarea.setPreferredSize(tarea.getSize());
   			tarea.setBackground(Color.GRAY);
   	
   			addInfo= new  JTextArea(Default.frame_w*1/3,Default.frame_h/(32));
   			addInfo.setPreferredSize(tarea.getSize());
   			addInfo.setBackground(Color.GRAY);
   		    
   			demo= new JButton("Demo");
   			demo.setSize(Default.frame_w*(1/3),Default.frame_h/(32));
   			demo.setPreferredSize(demo.getSize());
   			
   			game= new JComboBox<String>();
   			game.setSize(Default.frame_w*(1/3),Default.frame_h/(32));
   			game.setPreferredSize(game.getSize());
   			
   			savePlay=new JButton("SALVA PARTITA");
   			savePlay.setSize(Default.frame_w*(1/3),Default.frame_h/(32));
   			savePlay.setPreferredSize(savePlay.getSize());
   			
   			restart= new JButton("NUOVA PARTITA");
   			restart.setSize(Default.frame_w*1/3,Default.frame_h/(32));
   			restart.setPreferredSize(restart.getSize());
   			
   		    pSouth[0].add(tarea);
			pSouth[0].add(game);
			pSouth[0].add(demo);
			pSouth[0].add(addInfo);
			pSouth[0].add(savePlay);
			pSouth[0].add(restart);
   		   
			pEast=new JPanel[1];
			eastButtons=new JButton[16][1];
			makePanel(pEast,0,true,eastButtons.length,eastButtons[eastButtons.length-1].length,Default.frame_w*2/32, Default.frame_h*5/6,Color.WHITE);
			addButtons(pEast[0],eastButtons);
	   		  
			pWest=new JPanel[1];
			westButtons=new JButton[16][1];
			makePanel(pWest,0,true,westButtons.length,westButtons[westButtons.length-1].length,Default.frame_w*2/32, Default.frame_h*5/6,Color.WHITE);
			addButtons(pWest[0],westButtons);
	   		
   			frame.add(pNorth[0], 	BorderLayout.NORTH);
   			frame.add(pCenter[0], 	BorderLayout.CENTER);
   			frame.add(pSouth[0], 	BorderLayout.SOUTH);
   			frame.add(pEast[0],		BorderLayout.EAST);
   			frame.add(pWest[0], 	BorderLayout.WEST);
   			    
   			demo.addActionListener(event -> {
   				new lDemo().start();
   	    	});
   	   
   			restart.addActionListener(event -> {
   		    	   ldStart();
   			});
   			
   			savePlay.addActionListener(event -> {
   				try {
   				String name=JOptionPane.showInputDialog("Nome partita ");
   				if (name != null)
   				model.savePlay( name);
   				reloadDemoList();
   				}catch (Exception e){
   					
   				}
   				});
   			
      }
    /**
     * @since caricamento pannello array pannelli 
     */
      private void makePanel(JPanel[] jp,int index,boolean GreadLayout,int gridX,int gridY,int width,int height,Color backgroundColor ){
        jp[index]=new JPanel(); 
    	jp[index].setSize(width, height);
    	jp[index].setPreferredSize(jp[index].getSize());
    	if (GreadLayout)
    		jp[index].setLayout(new GridLayout(gridX,gridY));
    	else
    		jp[index].setLayout(new BorderLayout());
  		jp[index].setBackground(backgroundColor);
	}
   
    
    /**
    * @since crea bottoni in matrice e li aggiunge a pannello  
    */
      private void addButtons(JPanel jp,JButton [][] jba){
   	      int yl=jba.length;
   	      int xl=jba[yl-1].length;
    	  for (byte y=0;y< yl;y++)
   		   for (byte x=0;x< xl ;x++)
   			  jp.add(jba[y][x]= new JButton());
      }
      /**
       * @since  crea  listeners per bottoni scacchiera
       */
      private void addButtonCenterListener(){
    	  byte yl=(byte)centerButtons.length;
   	      byte xl=(byte)centerButtons[yl-1].length;
   	      for (byte y=0;y< yl;y++)
   		   for (byte x=0;x< xl ;x++) centerButtonListener(x,y,model.at(x,y)[0]);
      }
      
      private void  centerButtonListener( byte x,byte y,byte value){  
    	  centerButtons[y][x].addActionListener(event -> {
			   if ( AWTEvent.MOUSE_EVENT_MASK > 0 ){
				   if (controller != null  ) controller.onClick(x, y, value);
			   }});
      }
      /**
       *@since imposta singolo bottone 
       */
      private void setButton(JButton jb,String text,int width,int height,Color backgroundColor){
    	   jb.setIcon(null);
    	   jb.setBackground(backgroundColor);
    	   jb.setText(text);
    	   jb.setSize(width, height);
    	   jb.setPreferredSize(jb.getSize());
       }
       /**
        *@since carica icona del pezzo con indirizzo chess (per i pedoni tiene conto della promozione se avvenuta)
        *@see model.Default  
        */
       private void setIcon(JButton jb,byte chess){
      	   if (moves.isChValue(chess ))
      	    try {
      		   String name=osUtil.defaultAppDir(true)+Default.path[model.getConvProm(chess)]+Default.iconType;
      		   Image img = osUtil.ldImage(name);
      		   jb.setIcon(new ImageIcon(img));
      	   } catch (Exception ex) {
      		   ex.printStackTrace();
      		   System.exit(-1);
      	   }
       }
     
       private void clrIcon(JButton jb){
      	   try {
      		   jb.setIcon(null);
      		   jb.revalidate();
      		   jb.repaint();
      	   } catch (Exception ex) {
      		   ex.printStackTrace();
      		   System.exit(-1);
      	   }
       }
        
   
         private void setBorder(JButton jb,Color colorH,Color colorL){
      	   try{
      		   jb.setBorder( BorderFactory.createBevelBorder(1,colorH,colorL));
      	   } catch (Exception ex) {
      		   ex.printStackTrace();
      		   System.exit(-1);
      	   }
         }  
    
         private void reloadCord(){
      	   pNorth[2].removeAll();
      	   for (int n=0;n< 8;n++) {
      		   JLabel b =new JLabel("  "+Default.xCor[n]);
      		   northLabel[n]= b;
      		   pNorth[2].add(northLabel[n]);
      	   }
         }
         
         private void reloadEat(){
           int in=0;
      	   for (int i=0 ;i< 16;i++) {
      	   if (i >  1) in=(i/2); else in=0; 
      	   setButton(eastButtons[i][0],Default.yCor[in] +"" ,Default.frame_w*1/16, Default.frame_h*5/(6*16),Color.BLACK);
      	   setButton(westButtons[i][0],Default.yCor[in] +"" ,Default.frame_w*1/16, Default.frame_h*5/(6*16),Color.WHITE);
      	   }
         }
    
         /**
          *@since ricarica lista combo dei test demo
          */
         private void reloadDemoList(){
        	 ArrayList<String[]>list=model.loadDemo();
        	 int bsi=game.getSelectedIndex();
        	 game.removeAllItems();
        	 for (int n=0;n< list.size();n++)
			     try{
        		 if (list.get(n)[0].toString().trim().length() > 0)
        		 game.addItem(list.get(n)[0].toString().trim());
			     }catch(Exception e){
			    	 
			     }
        		 if (bsi == -1) bsi=0;
        	 game.setSelectedIndex(bsi);
         }
         
         
    /**
    * @since carica un file pdf su browser default se installato 
    */
   private void ldPdf(String name){
	   try {
		   File pdfFile = new File(osUtil.defaultAppDir(true)+name);
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
			} else {
				System.out.println("File " + pdfFile + " is not exists!");
			}
			System.out.println("Done");
	   } catch (Exception ex) {
		   JOptionPane.showMessageDialog(null, " Per manuale impostare un browser pdf di default ", "Scacchi", JOptionPane.INFORMATION_MESSAGE);
	   }
   }
   
   
}
