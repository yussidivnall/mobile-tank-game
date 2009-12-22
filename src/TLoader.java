/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uri
 */
import javax.microedition.m3g.*;
import java.io.*;
import java.util.*;
import javax.microedition.lcdui.*;

public class TLoader {
    World myWorld;
    Group playersGroup;
    Object3D playersObject;
    
    Group groundGroup;
    Object3D groundObject;
    
    Sprite3D explosionSprite;
    
    Camera camera;
    
    
    Hashtable levelObjects;
    
    public TLoader(World w){//This should use some config file really
        try{
            String BACKGROUND = "/sunset.png";
            String GROUND     = "/Farm2.m3g";
            String GAME_OBJECTS ="/GameObjects.m3g"; // Player's model really should be somewhere else
            String EXPLOSION ="/explosion1.gif";
            
            
            //World
            myWorld = w;
            playersGroup = new Group();
            groundGroup = new Group();
            
            
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
            
            //Ground
            
            
            Object grdObjs[] = Loader.load(GROUND);
            Group tempGroundGroup = (Group)grdObjs[0];
            groundObject = (Object3D)tempGroundGroup.getChild(0);
            tempGroundGroup.removeChild((Node)groundObject);
            groundGroup.addChild((Node)groundObject);
            myWorld.addChild(groundGroup);
            
             
            //Background
            Background bg = new Background();
            bg.setImage(new Image2D(Image2D.RGB,Image.createImage(BACKGROUND)));
            
            
            myWorld.setBackground(bg);
            //Explosion
            Image explosionImg = Image.createImage(EXPLOSION);
            Image2D explosionImg2D = new Image2D(Image2D.RGBA,explosionImg);
	
	   		CompositingMode cm = new CompositingMode();
				cm.setBlending(CompositingMode.ALPHA);      
            
            Appearance app = new Appearance();
            //app.setLayer(9);
            app.setCompositingMode(cm);
            
            explosionSprite = new Sprite3D(true,explosionImg2D,app);
            explosionSprite.setTranslation(0, 0, 0);
            //playersGroup.addChild(explosionSprite);
            levelObjects=new Hashtable();
            //Needs to be for each game object
            Group grp = new Group();
            Object3D objs[] = Loader.load("/PetrolStation4.m3g");
            Group tmpGrp = (Group)objs[0];
            Object3D obj = (Object3D)tmpGrp.getChild(0);
            tmpGrp.removeChild((Node)obj);
            grp.addChild((Node)obj);
            TGameObject tgobj = new TGameObject(0,0,0,100);
            levelObjects.put(grp, tgobj);
            myWorld.addChild(grp);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Group getGroundGroup(){
        return groundGroup;
    }
    
    
    
    
    
    public void setWorld(World w){
        myWorld = w;
    }

    public void levelLoader(String levelDefFile){
        try{
        
            InputStream is;
            is = this.getClass().getResourceAsStream(levelDefFile);
            
            InputStreamReader isr = new InputStreamReader(is);
            //BufferedReader br = new BufferedReader(isr);
            
            is.close();
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
