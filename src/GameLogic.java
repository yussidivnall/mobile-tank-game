import javax.microedition.m3g.*;
import javax.microedition.m3g.Group;
public class GameLogic{
	TankControl myTankControl;
	TCamera GameCamera;
	Group Ground;
	
	float DISTANCE_FROM_GROUND=0.05f;
	float NORMALS_ACCURACY=0.5f;
	Level myLevel;
        
	public GameLogic(TankControl tankRef,Level level,TCamera cam){
		
		myLevel = level;
		System.out.println("GameLogic Construction");
		Ground=myLevel.groundGroup;
		myTankControl = tankRef;
		GameCamera = cam;
	};
		public void GroundInteraction(){
			float position[] = myTankControl.getTankPosition();
			float orientation[] = {0,-1,0}; // look down
			float tankOrientation[] = myTankControl.getTankOrientation();
			orientation[0]=tankOrientation[0];
			orientation[2]=tankOrientation[2];
			
			RayIntersection rayIntersection=new RayIntersection();
			if(myLevel.myWorld.pick(-1,position[0],position[1],position[2],orientation[0],orientation[1],orientation[2],rayIntersection)){//Check if to Lower
				if( rayIntersection.getDistance()>=DISTANCE_FROM_GROUND){
					System.out.println("down Distance : "+rayIntersection.getDistance());
					myTankControl.tankAltitute(rayIntersection.getDistance());	
				}
				//if(rayIntersection.getNormalX()!=0|rayIntersection.getNormalZ()!=0){
				if((rayIntersection.getNormalX()<tankOrientation[0]+NORMALS_ACCURACY &rayIntersection.getNormalX()>tankOrientation[0]-NORMALS_ACCURACY) |(rayIntersection.getNormalZ()<tankOrientation[2]+NORMALS_ACCURACY &rayIntersection.getNormalZ()>tankOrientation[2]-NORMALS_ACCURACY)){
					//myTankControl.tankGroundAlignment(rayIntersection.getNormalX(),0,rayIntersection.getNormalZ());
				}
			}else if(myLevel.myWorld.pick(-1,position[0],position[1],position[2],orientation[0],-orientation[1],orientation[2],rayIntersection) ){//Check if to higher
				if(rayIntersection.getDistance()>=DISTANCE_FROM_GROUND){
					System.out.println("Up Distance : "+rayIntersection.getDistance());
					myTankControl.tankAltitute(-(rayIntersection.getDistance()));	
				}
				//if(rayIntersection.getNormalX()!=0|rayIntersection.getNormalZ()!=0){
				if((rayIntersection.getNormalX()<tankOrientation[0]+NORMALS_ACCURACY &rayIntersection.getNormalX()>tankOrientation[0]-NORMALS_ACCURACY) |(rayIntersection.getNormalZ()<tankOrientation[2]+NORMALS_ACCURACY &rayIntersection.getNormalZ()>tankOrientation[2]-NORMALS_ACCURACY)){
					//myTankControl.tankGroundAlignment(rayIntersection.getNormalX(),0,rayIntersection.getNormalZ());
				}
			}
			
			rayIntersection=null;
			
		}
		/*
		*	Checks that level boundaries aren't exceeded
		*/	
		
	public void GroundBorders(){
			float tankX = myTankControl.getTankPosition()[0];
			float tankZ = myTankControl.getTankPosition()[2];
			if(tankX>myLevel.MaxX ||tankX<myLevel.MinX || 
				tankZ>myLevel.MaxZ ||tankZ<myLevel.MinZ){
				if(tankX>myLevel.MaxX){
					myTankControl.setTankX(myLevel.MaxX);
				}	
				if(tankX<myLevel.MinX){
					myTankControl.setTankX(myLevel.MinX);							
				}
				if(tankZ>myLevel.MaxZ){
					myTankControl.setTankZ(myLevel.MaxZ);
				}
				if(tankZ<myLevel.MinZ){
					myTankControl.setTankZ(myLevel.MinZ);									
				}
				myTankControl.FullStop();
			}
	};
	public void tankFire(){
		myTankControl.Fire();
		float position[] = myTankControl.getTurrotPosition();
		float orientation[] = myTankControl.getTurrotOrientation();
		RayIntersection rayIntersection=new RayIntersection();
		if(myLevel.myWorld.pick(-1,position[0],position[1],position[2],orientation[0],orientation[1],orientation[2],rayIntersection)){
			//somthing was hit
         System.out.println("Picked something");
			Node objectIntersected = rayIntersection.getIntersected();
                        
                        //Do the explosion sprite
                        Group explosionGroup = new Group();
                        Sprite3D explosion = (Sprite3D)myLevel.explosionSprite.duplicate();
								//explosion.setCrop(0,0,100,100);                                                
                        
                        
                        explosionGroup.addChild(explosion);
                        myLevel.myWorld.addChild(explosionGroup);
                        float trans[] = new float[3];
                        myTankControl.getTankGroup().getTranslation(trans);
                        explosionGroup.setTranslation(trans[0], trans[1]+2, trans[2]);
                        float orient[] = new float[4];
                        myTankControl.getTankGroup().getOrientation(orient);
                        explosionGroup.preRotate(orient[0], orient[1], orient[2], orient[3]);
                        explosion.translate(0, 0, rayIntersection.getDistance()*10-2); // No idea why you need*10-1
								
								TGameExplosionThread animThread = new TGameExplosionThread(explosion,explosionGroup,myLevel.myWorld);
								animThread.start();	                        
                        //if it wasn't the ground that was hit, then the game engine needs to know
                        if(!objectIntersected.getParent().equals(myLevel.groundGroup)){           
                                    //objectIntersected.postRotate(45,0,1,0);
                                    myLevel.SomethingShot(objectIntersected);
                        }					
			System.out.println("Picked : " + objectIntersected);
			System.out.println("Distance : "+rayIntersection.getDistance());
			
		}else{
			System.out.println("Nothing Picked");
		}
		
		float tankPosition[] = myTankControl.getTankPosition();
		
		//TGameUtilities.Ray(
		rayIntersection = null;
		
	}
	
        public void cameraControl(){
            if(myTankControl.Accelerating){
                GameCamera.accelerate(myTankControl.Speed);
            }else{
                GameCamera.decelerate(myTankControl.Speed);
            }
        }
        
        
	public void advanceLogic(float time){
		tankLogic(time);
                
		if (myTankControl.moving){// needs to update the positioning
			GroundBorders();			
			GroundInteraction();
         cameraControl();
		}
	}
	

	
        
        
	public void tankLogic(float time){
		if (myTankControl.Speed > myTankControl.MAX_SPEED){ //MAX SPEED
			myTankControl.Speed=myTankControl.MAX_SPEED;
			//System.out.println("MAX_SPEED");
		}
		else if(myTankControl.Speed < -(myTankControl.MAX_SPEED) ){//MIN SPEED (Reversing at max Speed)
			myTankControl.Speed=-(myTankControl.MAX_SPEED);
		}
		else if(myTankControl.Accelerating){ //ACCELERATING
			if(myTankControl.forwards){
				//System.out.println("ACCELERATION_RATE :"+myTankControl.ACCELERATION_RATE);
				myTankControl.Speed-=myTankControl.ACCELERATION_RATE;
			}else{
				//System.out.println("ACCELERATION_RATE :"+myTankControl.ACCELERATION_RATE);
				myTankControl.Speed+=myTankControl.ACCELERATION_RATE;
			}
		}
		else if (!myTankControl.Accelerating && myTankControl.moving){//Decelerating
			if (myTankControl.Speed < myTankControl.FRICTION && myTankControl.Speed > -myTankControl.FRICTION){//stopping speed
				myTankControl.moving=false;
			}
			else { // not stopping speed yet, slow down
				if (myTankControl.Speed > myTankControl.FRICTION){ //Positive speed,
					myTankControl.Speed-=myTankControl.FRICTION;
				}
				else { // going backwards
					myTankControl.Speed+=myTankControl.FRICTION;
				}
			}
		};
		
		if (myTankControl.moving) { // moving
			//System.out.println("moving.......................");
			//System.out.println("MyTankControl.speed:"+myTankControl.Speed);
			myTankControl.advanceTank(myTankControl.Speed);
			//GroundInteraction();
		}else {
			//System.out.println(".......................Stoped"); 
		}
		
		if (myTankControl.turning){ // turning
			if(myTankControl.direction==true){
				myTankControl.rotateTank(myTankControl.ROTATION_ANGLE);
				//System.out.println("Direction "+myTankControl.direction);
			}else {
				myTankControl.rotateTank(-(myTankControl.ROTATION_ANGLE));
			}
			
		}
	}
}

