 

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uri
 */
 //Controls camera movement
 
import javax.microedition.m3g.*;
public class TCamera{
    Camera myCamera;
    int MAX_DISTANCE = 10;
    int MIN_DISTANCE = 2;
    float distance;
    boolean activated = false;
    int FPS;
      
    TCamera(Camera c){
        myCamera = c;
        distance = 0;
        stop();
    };
    public void stop(){
        myCamera.setOrientation(-10, 1, 0, 0);
        myCamera.setTranslation(0, 0,5);
    }
    
    
    public void accelerate(float speed){
        //if(speed > -2){
            myCamera.preRotate(1f, 1, 0f, 0f);
				myCamera.setTranslation(0,0,speed+3);            
            //myCamera.translate(0, 0, speed*3);
            distance--; 
        //}    
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
