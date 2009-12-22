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
public class TGame2 extends MIDlet  implements CommandListener {

        long start,elapsed,time =0;
    
	int FRAMES_PER_SECOND=20;
	int Speed;
	private Display myDisplay = null;
	Graphics3D myGraphics3D = Graphics3D.getInstance();
	World myWorld = null;
	TLoader gameLoader;
	
	//Tank's Ray Intersection (Collision Detection)
	RayIntersection rayInterSection;
	Object3D RayObject;
        TGameCanvas myCanvas = null;
	

	
	TimerTask myTimerTask = null;
	Timer myTimer = new Timer();
	GameLogic myGameLogic;
	public TankControl myTankControl;
	public Level myLevel;
	
	
	public TGame2(){
		super();	
		System.out.println("Constructor");

	
		myDisplay = Display.getDisplay(this);
		myCanvas = new TGameCanvas(this);
		try {
                    
         myWorld = new World();
			myLevel = new Level(myWorld);         
			myLevel.load("level.txt");         
         
			gameLoader = new TLoader(myWorld); // Get rid of this and replace with myLevel!
			myTankControl = new TankControl(gameLoader.playersGroup);
         TCamera GameCamera = new TCamera(gameLoader.camera,myTankControl,FRAMES_PER_SECOND);
                        
                        myGameLogic = new GameLogic(myTankControl,gameLoader,GameCamera);
			//myGameLogic = new GameLogic(myTankControl,gameLoader.groundGroup,gameLoader.myWorld,GameCamera);
			myTimerTask = new TGameTimerTask(myGameLogic,myCanvas,1000/FRAMES_PER_SECOND);
			rayInterSection = new RayIntersection();
                        
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Constructor End");
	}

	public void startApp(){
		System.out.println("startApp");
		myDisplay.setCurrent(myCanvas);
		printSysData();
		try {
			myTimer.schedule(myTimerTask,0,1000/FRAMES_PER_SECOND);
		}catch(Exception e) {
			e.printStackTrace();
		}
                System.out.println("startApp end");
	}
	public void printSysData(){
		System.out.println("SystemData....");
		System.out.println(System.getProperty("javax.microedition.m3g.version"));
	}
	/* CommandListener abstract methods override
	*/
	public void commandAction(Command c,Displayable d){};
	
	public void SpeedUp(boolean forwards){
		System.out.println("Speeeeeeeeeeeeeeeeeeeeeeeeeeeeeed");
		myTankControl.moving=true;
		myTankControl.Accelerating = true;
		myTankControl.forwards=forwards;
		System.out.println("Moving?:"+myTankControl.moving);
	}

	public void SlowDown(){
		System.out.println("Slooooooooooooooooooooooooooooooooowwwwwwwwwwwwww");
		myTankControl.Accelerating = false; //deselarating
		System.out.println("Moving?:"+myTankControl.moving);
	}	
	
	public void Turn(boolean direction){
		myTankControl.direction=direction;
		myTankControl.turning=true;
	}
	public void Strighten(){
		myTankControl.turning=false;
	}
	
	public void Fire(){
		System.out.println("Fire!");
		myGameLogic.tankFire();
	}
	/*
	/*Midlet abstract methods override
	*/
	public void destroyApp(boolean unconditional)throws MIDletStateChangeException{
		myCanvas.terminate();
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
		g.fillRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
		int Validity = gameLoader.myWorld.animate((int)time);
		myGraphics3D.bindTarget(g);
		myGraphics3D.setViewport(0,0,myCanvas.getWidth(),myCanvas.getHeight());
		myGraphics3D.render(gameLoader.myWorld);
		myGraphics3D.releaseTarget();
                elapsed = System.currentTimeMillis()-start;
                time += (int)elapsed;
	};

	
	
	
	public void DisplayGroupsChildren(Group group)
	{
		int NumberOfChildren = group.getChildCount();
		for (int i = 0;i < NumberOfChildren;i++){
			String ObjName = ""+group.getChild(i);
			System.out.println("Child number: "+i+" is named: "+ObjName);
		}
	}
}

