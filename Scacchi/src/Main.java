import view.*;

import java.awt.EventQueue;
import javax.swing.JFrame;


/**
 * @author Univr id083311 Alessandro Fuochi
 * @since Progetto di Programmazione II 2014/2015
 * @since Scacchi (tested Ubuntu/Windows )
 */
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				new TilesPanel(new model.TilesModel(), frame);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}