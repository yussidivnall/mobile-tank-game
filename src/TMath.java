public class TMath{

	public static float angle(float rise,float run){
			System.out.println("rise:"+rise+" run:"+run);			
			float ret = 0;
			if(rise == 0){
				if(run > 0) {return 0;}else {return 180;} // checked !			
			}
			if(run == 0){
				if(rise > 0){return 90;}else{return 270;} // checked !			
			}
			if(run > 0)return atan(rise/run);
			if(run < 0)return atan(rise/run)+180;
			return (float)0;
			/*			
			if(run > 0 && rise > 0){ // checked !
				return 0+atan(rise/run);			
			}
			if(run < 0 && rise > 0){ // checked!
				return 180+atan(rise/run);			
			}
			if(run < 0 && rise < 0){ // checked 
				return 180+atan(rise/run);			
			if(run > 0 && rise < 0){
				return 0+atan(rise/run);			
			}
			return ret;
			*/
	}



	public static float atan(float ratio){
			//atan - needs to be done better!
			// atan(t) = t - t^3/3+t^5/5-t^7/7+t^9/9 * 180/pi > thanks MathForum - ask doctor math 
			// http://mathforum.org/library/drmath/view/51875.html
			//Broken!!!			
						
			float r = 0;
			float atan=0;			
			if (ratio > -1 && ratio < 1){
				r=ratio;
				atan=r-((r*r*r)/3)+((r*r*r*r*r)/5)-((r*r*r*r*r*r*r)/7)+((r*r*r*r*r*r*r*r*r)/9);
			}else if (ratio > 1){
				r=1/ratio;
				atan=(float)Math.PI/2 - r-((r*r*r)/3)+((r*r*r*r*r)/5)-((r*r*r*r*r*r*r)/7)+((r*r*r*r*r*r*r*r*r)/9);
			}else{ // 
				r=1/ratio;
				atan=(float)-Math.PI/2 - r-((r*r*r)/3)+((r*r*r*r*r)/5)-((r*r*r*r*r*r*r)/7)+((r*r*r*r*r*r*r*r*r)/9);
			}
			float atang=atan*180/(float)Math.PI;
			System.out.println("ratio:"+ratio+" atang ="+atang);			
			return atang;	
	}
}