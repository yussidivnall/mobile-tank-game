import javax.microedition.lcdui.Image;
import javax.microedition.m3g.*;
public final class Farm1 {
	public static World getRoot(Canvas3D aCanvas) {
		//Background:
		Background BL2 = new Background();
		BL2.setColor(0x000E3866);
		
		//Light: 
		Light BL3 = new Light();
		BL3.setMode(128);
		BL3.setColor(0x00000000);
		BL3.setIntensity(1.000000f);
		
		//Light: Lamp.002
		Light BL4 = new Light();
		BL4.setMode(129);
		BL4.setColor(0x00FFFFFF);
		BL4.setIntensity(1.000000f);
		float[] BL4_matrix = {
			-1.0f,6.60023615555e-15f,-8.74227765735e-08f,0.703569769859f,
			-8.74227765735e-08f,-7.5497901264e-08f,1.0f,92.0706558228f,
			0.0f,1.0f,7.5497901264e-08f,-1.25169754028e-05f,
			0.0f,0.0f,0.0f,1.0f
		};
		
		Transform BL4_transform = new Transform();
		BL4_transform.set(BL4_matrix);
		BL4.setTransform(BL4_transform);
		
		
		// VertexArray 
		short[] BL5_array = {
			32766,32766,0,-32766,32766,0,-32766,-32766,0,32766,-32766,0
		};
		
		VertexArray BL5 = new VertexArray(BL5_array.length/3,3,2);
		BL5.set(0,BL5_array.length/3,BL5_array);
		
		// VertexArray 
		byte[] BL6_array = {
			0,0,127,0,0,127,0,0,127,0,0,127
		};
		
		VertexArray BL6 = new VertexArray(BL6_array.length/3,3,1);
		BL6.set(0,BL6_array.length/3,BL6_array);
		
		//VertexBuffer
		VertexBuffer BL7 = new VertexBuffer();
		float BL7_Bias[] = { -0.000001f, 0.000001f, 0.000000f};
		BL7.setPositions(BL5,0.000305f,BL7_Bias);
		BL7.setNormals(BL6);
		//length of TriangleStrips
		int[] BL8_stripLength ={4};
		
		//IndexBuffer
		int[] BL8_Indices = {
		1,2,0,3};
		
		IndexBuffer BL8=new TriangleStripArray(BL8_Indices,BL8_stripLength);
		
		PolygonMode BL9 = new PolygonMode();
		BL9.setCulling(162);
		BL9.setShading(165);
		BL9.setWinding(168);
		BL9.setTwoSidedLightingEnable(false);
		BL9.setLocalCameraLightingEnable(false);
		BL9.setPerspectiveCorrectionEnable(true);
		
		//Material: Material.001
		Material BL10 = new Material();
		BL10.setColor(Material.AMBIENT,0x00000000);
		BL10.setColor(Material.SPECULAR,0x00000000);
		BL10.setColor(Material.DIFFUSE,0xFFCCCCCC);
		BL10.setColor(Material.EMISSIVE,0x00000000);
		BL10.setShininess(0.000000f);
		BL10.setVertexColorTrackingEnable(false);
		//Appearance
		Appearance BL11 = new Appearance();
		BL11.setPolygonMode(BL9);
		BL11.setMaterial(BL10);
		
		
		//Mesh:Plane
		Mesh BL12 = new Mesh(BL7,BL8,BL11);
		float[] BL12_matrix = {
			-10.0f,-1.13998654266e-13f,1.50995799686e-06f,0.0f,
			1.50995799686e-06f,-7.5497899843e-07f,10.0f,0.0f,
			0.0f,10.0f,7.5497899843e-07f,0.0f,
			0.0f,0.0f,0.0f,1.0f
		};
		
		Transform BL12_transform = new Transform();
		BL12_transform.set(BL12_matrix);
		BL12.setTransform(BL12_transform);
		
		
		//World:
		World BL13 = new World();
		BL13.addChild(BL3);
		BL13.addChild(BL4);
		BL13.addChild(BL12);
		BL13.setBackground(BL2);
		
	
	return BL13;
}}
