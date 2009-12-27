import javax.microedition.lcdui.*;
import java.io.*;
public class MenuCanvas extends Canvas{
	TGame2 myParent;
	Image af_logo;	


	int SHOW_LOGO=1;
	int START_GAME=2;
	int state=0;
		
	String level="level.txt";
	MenuCanvas(TGame2 c){
		myParent=c;
		try{		
			af_logo=resizeImage(Image.createImage("/AFLogo.gif"),getWidth(),getHeight());
			state=SHOW_LOGO;
		}catch(IOException ioe){ioe.printStackTrace();}	
	}

	public void paint(Graphics g){
			if(state==SHOW_LOGO)g.drawImage(af_logo,0,0,0);
			
	}
	
	public void keyPressed(int keyCode){
		myParent.startGame(level);	
	}

//Stolen from:http://developers.sun.com/mobility/reference/techart/design_guidelines/image_resizing.html
/**
  * This methog resizes an image by resampling its pixels
  * @param src The image to be resized
  * @return The resized image
  */

  private Image resizeImage(Image src, int screenWidth,int screenHeight) {
      int srcWidth = src.getWidth();
      int srcHeight = src.getHeight();
      Image tmp = Image.createImage(screenWidth, srcHeight);
      Graphics g = tmp.getGraphics();
      int ratio = (srcWidth << 16) / screenWidth;
      int pos = ratio/2;

      //Horizontal Resize        

      for (int x = 0; x < screenWidth; x++) {
          g.setClip(x, 0, 1, srcHeight);
          g.drawImage(src, x - (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);
          pos += ratio;
      }

      Image resizedImage = Image.createImage(screenWidth, screenHeight);
      g = resizedImage.getGraphics();
      ratio = (srcHeight << 16) / screenHeight;
      pos = ratio/2;        

      //Vertical resize

      for (int y = 0; y < screenHeight; y++) {
          g.setClip(0, y, screenWidth, 1);
          g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP);
          pos += ratio;
      }
      return resizedImage;

  }//resize image   






}