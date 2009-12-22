import javax.microedition.m3g.*;
import java.io.*;
import java.util.Hashtable;

public class Level{
	final static int _RULE_STOP_THAT_TRUCK=1;


	int rule_set;
	String Level_Name;
	
	
	
	World myWorld;	
	String GAME_OBJECTS ="/GameObjects.m3g"; // Contains in the order:Light(0),Camera(1),Tank(2)
	Camera camera;	
	Group playersGroup;
	Object3D playersObject;
	
	Group groundGroup;
	Hashtable trucks;	
	Background myBackground;
	Hashtable sources;
	Level(World w){
		trucks = new Hashtable();	
		sources=new Hashtable();
		myWorld = w;
		try{
			init_world();
		}catch(Exception e){e.printStackTrace();}
	}
	public void init_world() throws IOException{
		playersGroup = new Group();
		      //Load once
            Object3D gameObjs[] = Loader.load(GAME_OBJECTS);
            Group tempGameObjGrp = (Group)gameObjs[0];
            Light light = (Light)tempGameObjGrp.getChild(0);
            camera =(Camera)tempGameObjGrp.getChild(1);
            playersObject = tempGameObjGrp.getChild(2);// Should be in a seperate file
            tempGameObjGrp.removeChild(light);
            tempGameObjGrp.removeChild(camera);
            tempGameObjGrp.removeChild((Node)playersObject);
            playersGroup.addChild((Node)playersObject);
            playersGroup.addChild(camera);
            
            myWorld = (World)tempGameObjGrp; // This is instead of setting the world up manually (sort of a cheat)
            myWorld.addChild((Node)light);
            myWorld.setActiveCamera(camera);
            myWorld.addChild((Node)playersGroup);       
	}
	//Do all the parsing from here, maybe needs to be in a different class for the sake of cleanliness
	public void load(String level){
		LevelParser parser = new LevelParser(level,this);
	}

	public void setGround(Group g){
		groundGroup=g;
	}
	public void setBackground(Background bg){
		myBackground=bg;
	}	
	
	public void addTruckModel(int num,Group mod){
			trucks.put(new Integer(num),mod);	
	}
	public void addSource(int num,TBuilding build){
		sources.put(new Integer(num),build);
	}
}