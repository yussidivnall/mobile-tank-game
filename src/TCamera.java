 

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uri
 */
import javax.microedition.m3g.*;
public class TCamera{
    TankControl myTankControl;
    Camera myCamera;
    int MAX_DISTANCE = 10;
    int MIN_DISTANCE = 2;
    float distance;
    boolean activated = false;
    int FPS;
    
    //float updateFPS;
    
    
    TCamera(Camera c,TankControl t,int fps){
        myTankControl = t;
        myCamera = c;
        distance = 0;
        //myCamera.setOrientation(0, 0, 0, 0);
        //myCamera.setTranslation(0, 0,5);        
        stop();
    };
    public void stop(){
        myCamera.setOrientation(-10, 1, 0, 0);
        myCamera.setTranslation(0, 0,5);
        //myCamera.set
    }
    
    
    public void accelerate(float speed){
        if(speed > -2){
            myCamera.postRotate(1, 1, 0, 0);
            myCamera.translate(0, 0, speed);
            distance--; 
        }    
    }
    public void decelerate(float speed){
        //if (distance < 0)
        System.out.println(speed);
        if(speed < -0.05 && speed > -1){
            myCamera.postRotate(-speed/4*3f, 1f, 0f, 0f);
            myCamera.translate(0, 0, -speed/4*3);
            //myCamera.translate(0, 0, -speed);
            distance++;
        }else if (speed < -1){
            //Do Nothings
        }else{
            stop();
        }
    }
    
    public void advanceCamera(float time){
        
    }
}
