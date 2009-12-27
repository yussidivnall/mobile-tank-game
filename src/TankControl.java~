import javax.microedition.m3g.*;
public class TankControl{
	float Speed =0;
	
	boolean turning = false;
	boolean direction; // if turning, then which direction, true - left, false - right
	boolean Accelerating = false;
	boolean moving = false;
	boolean forwards = false;
	
	float Acceleration =0;
	float rotationX;float rotationY;float rotationZ;
	private Group localTankGroup;
	int MAX_SPEED = 10;
	float ROTATION_ANGLE = 11.25f;
	float ACCELERATION_RATE=0.1f;
	float FRICTION=0.05f;
	float RANGE = 10;
	public TankControl(javax.microedition.m3g.Group tankRef){
		System.out.println("TankControlConstuction");
		localTankGroup = tankRef;
		rotationY=0;
		moving = false;
		Speed = 0f;
		localTankGroup.setPickingEnable(false);
	}
        
        public boolean isMoving(){
            return moving;
        }
        public Group getTankGroup(){
            return localTankGroup;
        }
        
        
	public void FullStop(){
			moving=false;
			Accelerating=false;
			Acceleration=0;
			Speed=0;	
	}
	public void Fire(){
			localTankGroup.setPickingEnable(false);
	}
	
	
	public void setTankPosition(float x,float y,float z){
		localTankGroup.setTranslation(x,y,z);	
	}
	public void setTankX(float x){
		localTankGroup.setTranslation(x,getTankPosition()[1],getTankPosition()[2]);
	}
	public void setTankZ(float z){
		localTankGroup.setTranslation(getTankPosition()[0],getTankPosition()[1],z);	
	}	
	
	public float[] getTankPosition(){
		float Translation[] = new float[3];
		localTankGroup.getTranslation(Translation);
		return Translation;
	}
	
	
	public float[] getTankOrientation(){
		float ret[] = new float[3];
		/*float  turnAngle = (rotationY)/180*3.14159f;
		float TranslationX = (float)Math.sin(turnAngle) * (float)RANGE;
		float TranslationZ =(float)Math.cos(turnAngle) * (float)RANGE;
		ret[0]=TranslationX;
		ret[1]=-1;
		ret[2]=TranslationZ;*/
		
		ret[0] = rotationX/180;
		ret[1]= rotationY/180;
		ret[2]=rotationZ/180;
		
		System.out.println("Computed angles : X : "+ ret[0] + " Y " + ret[1] +" Z : "+ ret[2]);
		return ret;
	}
	
	public void tankAltitute(float distance){
		localTankGroup.translate(0,-distance,0);
	}
	public void tankGroundAlignment(float normalX,float normalY,float normalZ){
		float angleX=normalX/(float)Math.PI*180;
		float angleZ=normalZ/(float)Math.PI*180;
		System.out.println("AngleX: " + angleZ); 
		System.out.println("AngleZ: " + angleZ); 
		localTankGroup.setOrientation(angleX,1,0,0);
		localTankGroup.setOrientation(angleZ,0,0,1);
//		localTankGroup.postRotate(angleX,1,0,0);
//		localTankGroup.postRotate(angleZ,0,0,1);
	}
	
	
	public float[] getTurrotPosition(){
		float Translation[] = new float[3];
		localTankGroup.getTranslation(Translation);
		Translation[1] +=0.01;//Raise on Y
		//Translation[2]+=0.223829917; // advance on Z axis
		System.out.println("Turrort at X : "+Translation[0]+" Y : " + Translation[1] + " Z : " + Translation[2]);
		return Translation;
	}
	public float[] getTurrotOrientation(){
		float ret[] = new float[3];
		/*
		float Orientation[] = new float[4];
		
		
		localTankGroup.getOrientation(Orientation);
		System.out.println("Angle : "+Orientation[0] + " X : " +Orientation[1] +  " Y : " + Orientation[2] + " Z : " +Orientation[3] );
		ret[0] = Orientation[0] * Orientation[1]; //MULTIPLY BY ANGLE
		ret[1] = Orientation[0] * Orientation[2];
		ret[2] = Orientation[0] * Orientation[3];
		System.out.println("Computed angles : X : "+ ret[0] + " Y " + ret[1] +" Z : "+ ret[2]);
		*/
		//Needs to calculate the vactor ( the translation on X and Z in relation to rotation Y)
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
			localTankGroup.setOrientation(rotationY,0,1,0);
			if(rotationY>=360){
				rotationY-=360;
			}else if(rotationY<=0){
				rotationY+=360;
			}
			//myTankGroup.postRotate(rotation,0,1,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("RotationY: "+rotationY);
	}
	
	
	public void advanceTank(float distance){
		//System.out.println("Distance : "+distance);
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
			localTankGroup.translate(-TranslationX,0,-TranslationZ);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}