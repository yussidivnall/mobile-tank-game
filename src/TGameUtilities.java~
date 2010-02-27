import javax.microedition.m3g.*;
//Misc utilities
public class TGameUtilities {

	public TGameUtilities(){
		
		
	}
	public void DisplayGroupsChildren(Group group)
	{
		int NumberOfChildren = group.getChildCount();
		for (int i = 0;i < NumberOfChildren;i++){
			String ObjName = ""+group.getChild(i);
			System.out.println("Child number: "+i+" is named: "+ObjName);
		}
	}	
	
	public void printSysData(){
		System.out.println("SystemData....");
		System.out.println(System.getProperty("javax.microedition.m3g.version"));
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