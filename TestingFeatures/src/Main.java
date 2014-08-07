import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Main {

	 private static FeatureTest previousDetector;
	    private static Mat leche;
	    private static Mat agua;
	    private static Mat lata;
	    private static List<Mat> preLoadedImages;
	    private static Mat resp;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


		leche = Highgui.imread("Leche2.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
    	agua = Highgui.imread("agua.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
    	lata = Highgui.imread("lata.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
    	showResult(leche);
    	
    	preLoadedImages = new ArrayList<Mat>();
    	
    	preLoadedImages.add(leche);
    	preLoadedImages.add(agua);
    	preLoadedImages.add(lata);
    	
    	previousDetector = new FeatureTest(preLoadedImages);
			previousDetector.ComputeImages();

			resp=Highgui.imread("nevera.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
			
			FeatureTest SD = new FeatureTest();
	    	SD.setFrame(resp);
	    	
	    	SD.ProcessFrame();
	    	if ( SD.Process( previousDetector.getDescriptors().get(0), "Leche")){
	    		System.out.println("LECHE DETECTADA");
				
		    	
				
		    	}
	    	else if( SD.Process( previousDetector.getDescriptors().get(1), "Agua")){
	    		System.out.println("AGUA DETECTADA");
				
		    	
				
		    	}
	    	else if( SD.Process( previousDetector.getDescriptors().get(2), "Lata")){
	    		System.out.println("LATA DETECTADA");
				
		    	
				
		    	}
	    	else
	    		System.out.println("NADA DETECTADO");
	    	showResult(resp);

	}
	public static void showResult(Mat img) {
	    Imgproc.resize(img, img, new Size(640, 480));
	    MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(".jpg", img, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);
	        JFrame frame = new JFrame();
	        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
	        frame.pack();
	        frame.setVisible(true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
