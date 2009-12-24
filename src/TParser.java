import java.io.*;
public class TParser{
	public static String readLine(InputStream is){
		try{
			int r=0;
			String o="";
			while((r=is.read())!=-1){			
				o+=(char)r;
				if((char)r=='\n'){return o.trim();}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static String sub(String l,char d,int n){
		String line = l;char del = d;int num=n;
		//int start = 0;
		int currentArg=0;
		String ret="";
		for(int c=0;c<line.length();c++){
			if(line.charAt(c)==del)currentArg++;
			if(currentArg==num && line.charAt(c)!=del)ret+=line.charAt(c);
			if(currentArg>num)break;
		}
		return ret.trim();
	}
}