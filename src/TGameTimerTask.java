import java.util.TimerTask;
import java.util.Timer;
import javax.microedition.m3g.Group;
public class TGameTimerTask extends TimerTask{

	GameLogic TGameLogic;
	TGameCanvas GameCanvas;

        long Time;
        long start;
        long elapsed;
        
        
	public TGameTimerTask(GameLogic GameLogicRef,TGameCanvas TGameCanvasRef,long time){
	//Game logic to advance in time
	//Canavs to repaint every cycle
	//time to advance each cycle
		TGameLogic = GameLogicRef;
		GameCanvas = TGameCanvasRef;
                
		Time = time;
                
                
	};
	public void run(){
		//int t = 1; //iincrement in time;
		//System.out.println("Running............");
		TGameLogic.advanceLogic(Time);
		GameCanvas.repaint();
	};
}