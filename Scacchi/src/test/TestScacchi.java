package test;

import static org.junit.Assert.*;
import javax.swing.JFrame;
import org.junit.Test;
import view.TilesPanel;
public class TestScacchi {
	int r=1;
	@Test
	public void test() {
		try{
				JFrame frame = new JFrame();
				new TilesPanel(new model.TilesModel(), frame);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				r=0;
		}
		catch(Exception e)
		{
			r=2;
		}
		assertEquals(0,r);
		//fail("Not yet implemented");
	}
}
