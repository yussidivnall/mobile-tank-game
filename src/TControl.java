public class TControl{
	//float speed = 0.5f;
	//Control the targets (trucks) would in the futre be used to extend TankControl too
	// And save a few lines of code
	float rotationY=0;
	TTarget myTarget;	
	
	
	TControl(TTarget t){
			myTarget=t;
			myTarget.group.setTranslation(myTarget.source.X,myTarget.source.Y,myTarget.source.Z);
			setStartingPoint();			
			
						
	}	
	public void setStartingPoint(){
				float opp = myTarget.destination.X - myTarget.source.X;
				float adj = myTarget.destination.Z - myTarget.source.Z;
				rotationY = TMath.angle(opp,adj);
				myTarget.group.preRotate(rotationY,0,1,0);			
			
	}	
	
	public void advance(float distance){
			float  turnAngle = (rotationY*3.14159f)/180;
			float TranslationX = (float)Math.sin(turnAngle) * (float)distance;
			float TranslationZ =(float)Math.cos(turnAngle) * (float)distance;
			myTarget.group.translate(TranslationX,0,TranslationZ);
			//rotationY+=0.5f;	
	}
}