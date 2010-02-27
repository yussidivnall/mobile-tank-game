import javax.microedition.m3g.*;
import javax.microedition.lcdui.*;
import java.util.TimerTask;
import java.util.Timer;
public class TGameCanvas extends Canvas {
		TGame2 myMidlet;

	
		public TGameCanvas(TGame2 Testlet){
			myMidlet = Testlet;
		}
	
	
		// Canvas abstract override
		public void paint(Graphics g){
			myMidlet.paint(g);
		}

		public void keyPressed(int keyCode){
			//System.out.println("Canvas.KeyPressed");
			//System.out.println("======================KeyPresed=================");			
			switch (getGameAction(keyCode)){
				case UP:
					myMidlet.SpeedUp(true);
				break;
				
				case DOWN:
					myMidlet.SpeedUp(false);
				break;
				
				case LEFT:	
					myMidlet.Turn(true);
				break;
				case RIGHT:	
					myMidlet.Turn(false);
				break;
				case FIRE:
					myMidlet.Fire();
				break;
			}
			
		}
		protected void keyRepeated(int keyCode) {
			//System.out.println("======================KeyRepeated=================");	
		}
		protected void keyReleased(int keyCode){
			//System.out.println("======================Released=================");	
			
			switch (getGameAction(keyCode)){
				case UP:
					myMidlet.SlowDown();
				break;
				case DOWN:
					myMidlet.SlowDown();
				break;
								
				case LEFT:	
					myMidlet.Strighten();
				break;
				case RIGHT:	
					myMidlet.Strighten();
				break;
			}
			
		}
		public void terminate(){}		
}
	



