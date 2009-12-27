import java.util.TimerTask;
import java.util.Timer;
import javax.microedition.m3g.Group;
public class TGameTimerTask extends TimerTask{

	GameLogic TGameLogic;
	TGameCanvas GameCanvas;
	Level myLevel;

	long Time;
   long start;
   long elapsed;
        
        
	public TGameTimerTask(Level level,GameLogic GameLogicRef,TGameCanvas TGameCanvasRef,long time){
	//Game logic to advance in time
	//Canavs to repaint every cycle
	//time to advance each cycle
		TGameLogic = GameLogicRef;
		GameCanvas = TGameCanvasRef;
      myLevel=level;
		Time = time;
	};
	public void run(){
		//int t = 1; //iincrement in time;
		//System.out.println("Running............");
		TGameLogic.advanceLogic(Time);
		myLevel.advance(Time);		
		GameCanvas.repaint();
	};
}