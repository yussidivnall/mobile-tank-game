import javax.microedition.m3g.*;
public class TGameUtilities {

	public TGameUtilities(){
		
		
	}

	public static Group Arrow(float x,float y,float z,float dx,float dy,float dz){
		Group arrowGroup = new Group();
		try{
			Object3D[] ArrowObj = Loader.load("/Arrow.m3g");
			Group Obj1 = (Group)ArrowObj[0];
			arrowGroup.addChild(Obj1);
			arrowGroup.translate(x,y,z);
		}catch (Exception e){
			e.printStackTrace();
		}
		return arrowGroup;
	};
	
	public static Group Ray(float x,float y, float z,float dx,float dy,float dz){
		float lineData[] = {
			x,y,z,
			x,y,z,
			dx,dy,dz,
			dx,dy,dz
		};
		return null;
	}
}