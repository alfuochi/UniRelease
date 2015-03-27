package test;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import model.Model;

import org.junit.Test;

import view.TilesPanel;
public class TestScacchiStart {
    Model model;
	int r=1;
	@Test
	public void test() {
		try{
				JFrame frame = new JFrame();
				model =new model.TilesModel();
				new TilesPanel(model, frame);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				printM p =new printM(model);
				p.print("JUnit Test " + this.getClass().getName());
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
