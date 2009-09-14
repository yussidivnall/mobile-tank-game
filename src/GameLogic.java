import javax.microedition.m3g.*;
import javax.microedition.m3g.Group;
public class GameLogic{
	TankControl myTankControl;
	World localWorld;
        TCamera GameCamera;
	Group Ground;
	float GroundPositions[];	
	float GroundLimits[]={0,0,0,0};//[MAX_X,MIN_X,MAX_Z,MIN_Z]	
	
	Object3D GroundObject;
	IndexBuffer GroundIndexBuffer;
	VertexBuffer GroundVertexBuffer;
	VertexArray GroundVertexArray;
	
	float DISTANCE_FROM_GROUND=0.05f;
	float NORMALS_ACCURACY=0.5f;
	
        TLoader gameLoader;
        
	int GroundIndices[];
	public GameLogic(TankControl tankRef,TLoader tl,TCamera cam){
		
                gameLoader = tl;
                System.out.println("GameLogic Construction");
		Ground=gameLoader.groundGroup;
		myTankControl = tankRef;
		GroundObject  = (Object3D)Ground.getChild(0);
		GameCamera = cam;
                //Ground.getPositions(GroundPositions);
		//Search for board(Ground)limits				

		
			
				
		
		//GroundIndices = new int[GroundObject.getIndexCount()];
		//GroundIndexBuffer = new IndexBuffer();
		//GroundIndexBuffer = (IndexBuffer)GroundObject.VertexBuffer;
		//GroundIndexBuffer = (IndexBuffer)GroundObject.VertexArray;
		localWorld = gameLoader.myWorld;
		
		
		Mesh m = (Mesh)GroundObject;
		GroundVertexBuffer = m.getVertexBuffer();
		System.out.println("Ground Vertex Buffer count : " + GroundVertexBuffer.getVertexCount());
		float ScaleBias[] = new float[4];
		GroundVertexArray = GroundVertexBuffer.getPositions(ScaleBias);
		
		if(GroundVertexArray == null){
			System.out.println("No Vertex Array");
		}else System.out.println("Yes Vertex Array");
		System.out.println("Scale Bias[0] : "+ScaleBias[0]);
		System.out.println("Scale Bias[1] : "+ScaleBias[1]);
		System.out.println("Scale Bias[2] : "+ScaleBias[2]);
		System.out.println("Scale Bias[3] : "+ScaleBias[3]);

		GroundPositions = new float[GroundVertexBuffer.getVertexCount()];		
		GroundLimits[0] = 200;		
		GroundLimits[1] = -200;
		GroundLimits[2] = 200;
		GroundLimits[3] = -200;		
		
		System.out.println("MAX_X:"+GroundLimits[0]+";MIN_X:"+GroundLimits[1]+";MAX_Z"+GroundLimits[2]+";MIN_Z"+GroundLimits[3]);

		byte buffer[] ;
		//GroundVertexArray.get(0,10,buffer);	
		
	};
		public void GroundInteraction(){
			float position[] = myTankControl.getTankPosition();
			float orientation[] = {0,-1,0}; // look down
			float tankOrientation[] = myTankControl.getTankOrientation();
			orientation[0]=tankOrientation[0];
			orientation[2]=tankOrientation[2];
			
			RayIntersection rayIntersection=new RayIntersection();
			if(localWorld.pick(-1,position[0],position[1],position[2],orientation[0],orientation[1],orientation[2],rayIntersection)){//Check if to Lower
				if( rayIntersection.getDistance()>=DISTANCE_FROM_GROUND){
					System.out.println("down Distance : "+rayIntersection.getDistance());
					myTankControl.tankAltitute(rayIntersection.getDistance());	
				}
				//if(rayIntersection.getNormalX()!=0|rayIntersection.getNormalZ()!=0){
				if((rayIntersection.getNormalX()<tankOrientation[0]+NORMALS_ACCURACY &rayIntersection.getNormalX()>tankOrientation[0]-NORMALS_ACCURACY) |(rayIntersection.getNormalZ()<tankOrientation[2]+NORMALS_ACCURACY &rayIntersection.getNormalZ()>tankOrientation[2]-NORMALS_ACCURACY)){
					//myTankControl.tankGroundAlignment(rayIntersection.getNormalX(),0,rayIntersection.getNormalZ());
				}
			}else if(localWorld.pick(-1,position[0],position[1],position[2],orientation[0],-orientation[1],orientation[2],rayIntersection) ){//Check if to higher
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
		*	Checks that ground borders aren't exceeded
		*/	
		
	public void GroundBorders(){
				if(myTankControl.getTankPosition()[0]>GroundLimits[0]){
					System.out.println("MAX_X PAST");				
					myTankControl.setTankPosition(GroundLimits[0],myTankControl.getTankPosition()[1],myTankControl.getTankPosition()[2]);
					//myTankControl.moving=false;
					myTankControl.FullStop();				
				}
				
				if(myTankControl.getTankPosition()[0]<GroundLimits[1]){
					System.out.println("MIN_X PAST");				
					myTankControl.setTankPosition(GroundLimits[1],myTankControl.getTankPosition()[1],myTankControl.getTankPosition()[2]);				
					//myTankControl.moving=false;				
					myTankControl.FullStop();				
				}
				if(myTankControl.getTankPosition()[2]>GroundLimits[2]){
					myTankControl.setTankPosition(myTankControl.getTankPosition()[0],myTankControl.getTankPosition()[1],GroundLimits[2]);					
					System.out.println("MAX_Z PAST");				
					//myTankControl.moving=false;
					myTankControl.FullStop();				
				}
				if(myTankControl.getTankPosition()[2]<GroundLimits[3]){					
					myTankControl.setTankPosition(myTankControl.getTankPosition()[0],myTankControl.getTankPosition()[1],GroundLimits[2]);					
					System.out.println("MIN_Z PAST");				
					//myTankControl.moving=false;
					myTankControl.FullStop();				
				}				
			
	};
	public void tankFire(){
		myTankControl.Fire();
		float position[] = myTankControl.getTurrotPosition();
		float orientation[] = myTankControl.getTurrotOrientation();
		RayIntersection rayIntersection=new RayIntersection();
		if(localWorld.pick(-1,position[0],position[1],position[2],orientation[0],orientation[1],orientation[2],rayIntersection)){
			//somthing was hit
                        System.out.println("Picked something");
			Node objectIntersected = rayIntersection.getIntersected();
                        
                        //Do the explosion sprite
                        Group explosionGroup = new Group();
                        Sprite3D explosion = (Sprite3D)gameLoader.explosionSprite.duplicate();
                        explosionGroup.addChild(explosion);
                        gameLoader.myWorld.addChild(explosionGroup);
                        float trans[] = new float[3];
                        myTankControl.getTankGroup().getTranslation(trans);
                        explosionGroup.setTranslation(trans[0], trans[1]+2, trans[2]);
                        float orient[] = new float[4];
                        myTankControl.getTankGroup().getOrientation(orient);
                        explosionGroup.preRotate(orient[0], orient[1], orient[2], orient[3]);
                        explosion.translate(0, 0, rayIntersection.getDistance()*10-1); // No idea why you need*10-1
                        //if it wasn't the ground that was hit, then the game engine needs to know
                        if(!objectIntersected.equals(GroundObject)){           
                                    //objectIntersected.postRotate(45,0,1,0);
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
				System.out.println("Direction "+myTankControl.direction);
			}else {
				myTankControl.rotateTank(-(myTankControl.ROTATION_ANGLE));
			}
			
		}
	}
}

