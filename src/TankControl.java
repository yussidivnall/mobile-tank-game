import javax.microedition.m3g.*;
//
//	This class is used to control the tank's actions
// I would in the future like to have it extending TControl 
//(Which at the moment just controls the AI)
// This is part of the "older" code and can do with a lot of touching up
//
//
public class TankControl{
	float Speed =0;
	
	
	boolean turning = false;
	boolean direction; // if turning, then which direction, true - left, false - right
	boolean Accelerating = false;
	boolean moving = false;
	boolean forwards = false;
	
	float Acceleration =0;
	float rotationX;float rotationY;float rotationZ;
	private Group myTankGroup;
	int MAX_SPEED = 10;
	float ROTATION_ANGLE = 11.25f;
	float ACCELERATION_RATE=0.1f;
	float FRICTION=0.05f;
	float RANGE = 10;
	public TankControl(javax.microedition.m3g.Group tankRef){
		//System.out.println("TankControlConstuction");
		myTankGroup = tankRef;
		rotationY=0;
		moving = false;
		Speed = 0f;
		myTankGroup.setPickingEnable(false);
	}
        
	public boolean isMoving(){return moving;}
   public Group getTankGroup(){return myTankGroup;}
        
   public void crash(int type){
		if(type==TPhysics.CRASH_HEADON){ // Head On Crash
			Speed=-(Speed);
			if(Speed > -Acceleration && Speed < Acceleration)Speed=0;
			System.out.println("Crashed ");
		}   
   }
	public void FullStop(){
			moving=false;
			Accelerating=false;
			Acceleration=0;
			Speed=0;	
	}
	public void Fire(){}
	
	
	public void setPosition(TPoint p){
	//Never called!		
		myTankGroup.setTranslation(p.X,p.Y,p.Z);	
	}
	
	//Maybe to replace these two with setTankPosition
	public void setTankX(float x){
		myTankGroup.setTranslation(x,getTankPosition()[1],getTankPosition()[2]);
	}
	public void setTankZ(float z){
		myTankGroup.setTranslation(getTankPosition()[0],getTankPosition()[1],z);	
	}	

	//@To be deprecated
	public float[] getTankPosition(){
		float Translation[] = new float[3];
		myTankGroup.getTranslation(Translation);
		return Translation;
	}
	// And replaced by this!
	public TPoint getPosition(){
		float pos[] = new float[3];
		myTankGroup.getTranslation(pos);
		return new TPoint(pos[0],pos[1],pos[2]);	
	}	
	
	//@To Be Deprecated!
	public float[] getTankOrientation(){
		float ret[] = new float[3];
		ret[0] = rotationX/180;
		ret[1]= rotationY/180;
		ret[2]=rotationZ/180;
		return ret;
	}
	// And replaced by this!
	public TPoint getOrientation(){
		float orient[] = new float[4];
		myTankGroup.getOrientation(orient);
		float angle_x = (orient[0]*orient[1])/180;
		float angle_y = (orient[0]*orient[2])/180;
		float angle_z = (orient[0]*orient[3])/180;		
		return new TPoint(angle_x,angle_y,angle_z);	
	}	
	
	public void tankAltitute(float distance){
		myTankGroup.translate(0,-distance,0);
	}
	public void tankGroundAlignment(float normalX,float normalY,float normalZ){
		float angleX=normalX/(float)Math.PI*180;
		float angleZ=normalZ/(float)Math.PI*180;
		System.out.println("AngleX: " + angleZ); 
		System.out.println("AngleZ: " + angleZ); 
		myTankGroup.setOrientation(angleX,1,0,0);
		myTankGroup.setOrientation(angleZ,0,0,1);
//		myTankGroup.postRotate(angleX,1,0,0);
//		myTankGroup.postRotate(angleZ,0,0,1);
	}
	

	//This isn't great !	
	public float[] getTurrotPosition(){
		//This should probably come from a descriptor file for the model used;	
		float Translation[] = new float[3];
		myTankGroup.getTranslation(Translation);
		Translation[1] +=0.01;//Raise on Y (Should come from some descriptor file for the model)
		System.out.println("Turrort at X : "+Translation[0]+" Y : " + Translation[1] + " Z : " + Translation[2]);
		return Translation;
	}
	public float[] getTurrotOrientation(){
	
		float ret[] = new float[3];
		//Needs to calculate the vector ( the translation on X and Z in relation to rotation Y)
		//Sin=opp/hyp =~ sin*hyp = opp, make hyp 10
		float  turnAngle = (rotationY*3.14159f)/180;
		float TranslationX = (float)Math.sin(turnAngle) * (float)RANGE;
		float TranslationZ =(float)Math.cos(turnAngle) * (float)RANGE;
		ret[0]=TranslationX;
		ret[1]=0;
		ret[2]=TranslationZ;
		System.out.println("Computed angles : X : "+ ret[0] + " Y " + ret[1] +" Z : "+ ret[2]);
		return ret;
	}
	
	public void rotateTank(float rotation){
		rotationY+=rotation;
		try{
			myTankGroup.setOrientation(rotationY,0,1,0);
			if(rotationY>=360){
				rotationY-=360;
			}else if(rotationY<=0){
				rotationY+=360;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("RotationY: "+rotationY);
	}
	
	
	public void advanceTank(float distance){
		//Soh Cah Toa
		//Sin = opp / hyp
		//Sin(rotatioZ) * hyp(distance) == opp(TaranlationX)
		
		//Cah
		//Cos = adj/hyp
		//adj == cos(RotationZ) * hyp
		
		//Tan = Opp / Adj
		//Tan(rotationZ) * Opp(TanslationX)  = Adj(TanslationZ)
		
		
		try{
			float  turnAngle = (rotationY*3.14159f)/180;
			float TranslationX = (float)Math.sin(turnAngle) * (float)distance;
			float TranslationZ =(float)Math.cos(turnAngle) * (float)distance;
			//System.out.println("TanslationX: "+TranslationX+", TanslationZ:" + TranslationZ);
			myTankGroup.translate(-TranslationX,0,-TranslationZ);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}