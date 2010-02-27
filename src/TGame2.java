import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;
import javax.microedition.m3g.*;
import javax.microedition.lcdui.CommandListener;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.*;

public class TGame2 extends MIDlet   {

   long start,elapsed,time =0;
    
	int FRAMES_PER_SECOND=20;
	private Display myDisplay = null;
	Graphics3D myGraphics3D = Graphics3D.getInstance();
	World myWorld = null;
   TGameCanvas gameCanvas = null;
	MenuCanvas menuCanvas = null;

	
	TimerTask myTimerTask = null;
	Timer myTimer = new Timer();
	GameLogic myGameLogic;
	public TankControl myTankControl;
	public Level myLevel;
	
	public TGame2(){
		super();	
		System.out.println("Constructor");
		myDisplay = Display.getDisplay(this);
		gameCanvas = new TGameCanvas(this);
		menuCanvas = new MenuCanvas(this);
      myWorld = new World();
		myLevel = new Level(myWorld);         
		System.out.println("Constructor End");
	}

	public void startApp(){
		System.out.println("startApp");
		myDisplay.setCurrent(menuCanvas);		
		//startGame("level.txt");		
      System.out.println("startApp end");
	}
	public void startMenu(){
	}	
	
	public void startGame(String level_file){
			myLevel.load(level_file);
			myTankControl = new TankControl(myLevel.playersGroup);
			myLevel.setTankControl(myTankControl);
         TCamera GameCamera = new TCamera(myLevel.camera);
         myGameLogic = new GameLogic(myTankControl,myLevel,GameCamera);
			myTimerTask = new TGameTimerTask(myLevel,myGameLogic,gameCanvas,1000/FRAMES_PER_SECOND);
			myDisplay.setCurrent(gameCanvas);
			myTimer.schedule(myTimerTask,0,1000/FRAMES_PER_SECOND);
	}	
	

	/* CommandListener abstract methods override
	*/
	public void commandAction(Command c,Displayable d){};
	
	public void SpeedUp(boolean forwards){
		//System.out.println("Speeeeeeeeeeeeeeeeeeeeeeeeeeeeeed");
		myTankControl.moving=true;
		myTankControl.Accelerating = true;
		myTankControl.forwards=forwards;
	}

	public void SlowDown(){
		myTankControl.Accelerating = false; //deselarating
	}	
	
	public void Turn(boolean direction){
		myTankControl.direction=direction;
		myTankControl.turning=true;
	}
	public void Strighten(){
		myTankControl.turning=false;
	}
	
	public void Fire(){
		myGameLogic.tankFire();
	}
	/*
	/*Midlet abstract methods override
	*/
	public void destroyApp(boolean unconditional)throws MIDletStateChangeException{
		gameCanvas.terminate();
		myTimerTask = null;
		myTimer.cancel();
		myTimer=null;
	}
	public void pauseApp(){
	}
	
	//Midlet Paint Override
	public void paint(Graphics g){
            
      start = System.currentTimeMillis();          
		g.setColor(0x00);
		g.fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
		int Validity = myLevel.myWorld.animate((int)time);
		myGraphics3D.bindTarget(g);
		myGraphics3D.setViewport(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
		myGraphics3D.render(myLevel.myWorld);
		myGraphics3D.releaseTarget();
		g.drawString("Score:"+myLevel.score,0,gameCanvas.getHeight()-10,g.BASELINE|g.LEFT);
      elapsed = System.currentTimeMillis()-start;
      time += (int)elapsed;
	};

	
	
	

}