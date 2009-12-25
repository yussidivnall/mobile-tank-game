public class TControl{
	//float speed = 0.5f;

	float rotationY=0;
	TTarget myTarget;	
	
	
	TControl(TTarget t){
			myTarget=t;
			myTarget.group.setTranslation(myTarget.source.X,myTarget.source.Y,myTarget.source.Z);
			setStartingPoint();			
			
						
	}	
	public void setStartingPoint(){

			/*if(myTarget.source.X==myTarget.destination.X){
				if(myTarget.source.Z > myTarget.destination.Z){
					rotationY=180;
				}else{
					rotationY=0;
				}

			}else if (myTarget.source.Z==myTarget.destination.Z){
				if(myTarget.source.X > myTarget.destination.X){
					rotationY=270;
				}else{
					rotationY=90;
				}

			}else{
				float opp = myTarget.destination.X - myTarget.source.X;
				float adj = myTarget.destination.Z - myTarget.source.Z;
				float ratio = (float)opp/adj;
				rotationY=TMath.atan(ratio);
			}
			*/
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