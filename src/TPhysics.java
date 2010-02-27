import javax.microedition.m3g.*;
//Handles (Should handle) all the phyisics of the game
//Doesn't a lot is still under TGameLogic & TankControl

public class TPhysics{
	public static final int CRASH_HEADON=1;

	Level myLevel;
	
	Group testA;
	Group testB;
	TPhysics(Level l){
			myLevel=l;				

			testA=(Group)myLevel.dotGroup.duplicate();
			testA.setPickingEnable(false);
			testB=(Group)myLevel.dotGroup.duplicate();
			testB.setPickingEnable(false);
			//myLevel.myWorld.addChild(testA);myLevel.myWorld.addChild(testB);
	}
	public void advance(long time){
		//wheelsCollision();	
		collisionDetection();	
	}
	public void collisionDetection(){
		float pt[] = new float[3]; // player's translation
		myLevel.playerDescriptor.myGroup.getTranslation(pt);
		float playerX = pt[0];
		float playerZ = pt[2];
		
		float ot[] = new float[4];// player's orientation
		myLevel.playerDescriptor.myGroup.getOrientation(ot);
		float playerAngle = ((ot[0] * ot[2])*3.14159f)/180; // total Y rotation (In rads)	
		float testDistance=5.00f; // Magnitude
		float testHeight = myLevel.playerDescriptor.getLength()/2; // how much above the ground to place rayintersection
		
		//Test headon collision
		float testX = (float)Math.sin(playerAngle)*testDistance + playerX;		
		float testZ = (float)Math.cos(playerAngle)*testDistance + playerZ;		
		
		RayIntersection headOn = new RayIntersection();
		if(myLevel.myWorld.pick(-1,playerX,testHeight,playerZ,(float)Math.sin(playerAngle),0,(float)Math.cos(playerAngle),headOn)){		
			if(headOn.getDistance() < testDistance){
				System.out.println("Headon Crash");
				myLevel.myTankControl.crash(TPhysics.CRASH_HEADON);			

			}
		}		
		//System.out.println("Player at X:"+playerX+" Z:"+playerZ+" at angle:"+playerAngle);		
	}	
	

	
	public void wheelsCollision(){
		//	A - B
		// C - D corners
		float down[]={0,-1,0};
		float p_A[] = new float[3];float p_B[] = new float[3];float p_C[] = new float[3];float p_D[] = new float[3];

		myLevel.playerDescriptor.A.getTranslation(p_A);
		myLevel.playerDescriptor.B.getTranslation(p_B);
		myLevel.playerDescriptor.C.getTranslation(p_C);
		myLevel.playerDescriptor.D.getTranslation(p_D);

		RayIntersection ri_A = new RayIntersection();RayIntersection ri_B = new RayIntersection();
		RayIntersection ri_C = new RayIntersection();RayIntersection ri_D = new RayIntersection();
		
		if(myLevel.myWorld.pick(-1,p_A[0],p_A[1],p_A[2],down[0],down[1],down[2],ri_A)){
			Node n = ri_A.getIntersected();
			if(n.getParent()==myLevel.groundGroup){
				//System.out.println("A Intersected Ground:"+n);
			}else{
				System.out.println("A Intersected:"+n);			
			}
			//if(ri_A.getIntersected			
			//System.out.println("A picked");		
		}
		if(myLevel.myWorld.pick(-1,p_B[0],p_B[1],p_B[2],down[0],down[1],down[2],ri_B)){
			//System.out.println("A picked");		
		}
		if(myLevel.myWorld.pick(-1,p_C[0],p_C[1],p_C[2],down[0],down[1],down[2],ri_C)){
			//System.out.println("A picked");		
		}
		if(myLevel.myWorld.pick(-1,p_D[0],p_D[1],p_D[2],down[0],down[1],down[2],ri_D)){
			//System.out.println("A picked");		
		}		
	}


}