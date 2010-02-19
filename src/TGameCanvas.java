import javax.microedition.m3g.*;
import javax.microedition.lcdui.*;
import java.util.TimerTask;
import java.util.Timer;
public class TGameCanvas extends Canvas {
	TGame2 myTestlet;

	
		public TGameCanvas(TGame2 Testlet){
			myTestlet = Testlet;
		}
	
	
		// Canvas abstract override
		public void paint(Graphics g){
			myTestlet.paint(g);
		}

		public void keyPressed(int keyCode){
			//System.out.println("Canvas.KeyPressed");
			//System.out.println("======================KeyPresed=================");			
			switch (getGameAction(keyCode)){
				case UP:
					myTestlet.SpeedUp(true);
				break;
				
				case DOWN:
					myTestlet.SpeedUp(false);
				break;
				
				case LEFT:	
					myTestlet.Turn(true);
				break;
				case RIGHT:	
					myTestlet.Turn(false);
				break;
				case FIRE:
					myTestlet.Fire();
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
					myTestlet.SlowDown();
				break;
				case DOWN:
					myTestlet.SlowDown();
				break;
								
				case LEFT:	
					myTestlet.Strighten();
				break;
				case RIGHT:	
					myTestlet.Strighten();
				break;
			}
			
		}
		public void terminate(){}		
}
	



