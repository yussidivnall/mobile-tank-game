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
		//Gravity, should be in TPhysics!
		//could be done a lot better!
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
		
	//Checks that level boundaries aren't exceeded
	//Should probably be in TPhysics!
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

			//for animation - can do better!!!!
			//http://www.j2megame.org/j2meapi/JSR_184_Mobile_3D_Graphics_API_1_1/index.html
			float x,y,z;
			float r[] = new float[6];
			rayIntersection.getRay(r);
			x=r[0]+r[3]*(rayIntersection.getDistance()-0.5f);			
			y=r[1]+r[4]*rayIntersection.getDistance()+2;
			z=r[2]+r[5]*(rayIntersection.getDistance()-0.5f);
			TPoint p = new TPoint((int)x,(int)y,(int)z);			
			myLevel.explodeAnim(p);
         //if it wasn't the ground that was hit, then the game engine needs to know
         if(!objectIntersected.getParent().equals(myLevel.groundGroup)){
         	myLevel.SomethingShot(objectIntersected);
      	}					
			System.out.println("Picked : " + objectIntersected);
			System.out.println("Distance : "+rayIntersection.getDistance());
			
		}else{
			System.out.println("Nothing Picked");
		}
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
		}
		else if(myTankControl.Speed < -(myTankControl.MAX_SPEED) ){//MIN SPEED (Reversing at max Speed)
			myTankControl.Speed=-(myTankControl.MAX_SPEED);
		}
		else if(myTankControl.Accelerating){ //ACCELERATING
			if(myTankControl.forwards){
				myTankControl.Speed-=myTankControl.ACCELERATION_RATE;
			}else{
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
			myTankControl.advanceTank(myTankControl.Speed);
		}
		
		if (myTankControl.turning){ // turning
			if(myTankControl.direction==true){
				myTankControl.rotateTank(myTankControl.ROTATION_ANGLE);
			}else {
				myTankControl.rotateTank(-(myTankControl.ROTATION_ANGLE));
			}	
		}
	}
}