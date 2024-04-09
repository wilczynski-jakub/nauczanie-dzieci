import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Transformacja {

    public static void main(String[] args)
	{	
		String nazwaPliku = args[0];
		
		double[][] macierz = new double[2][2];
		int indeks = 1;	
		
		for (int i=0; i<2; i++)
			for (int j=0; j<2; j++)
			{
				macierz[i][j] = Double.parseDouble(args[indeks]);
				indeks++;
			}
		
		transformacja(nazwaPliku, macierz);
	} //public static void main(String args[])
	
	// Początkowo to był main, używałem do testów.
	public static void test(String args[]){
		
		Random random = new Random();
		double[][] macierz = new double[2][2];
		
		for (int i=0; i<10; i++)
		{
			for (int x=0; x<2; x++)
				for (int y=0; y<2; y++)
					macierz[x][y] = (double)(random.nextInt(200) - 100)/10;
			
			//transformacja("Obrazek.png",macierz,i);
		}
	} //public static void test(String args[])

	public static void transformacja(String nazwaPliku, double[][] macierz)//, int ktoryPlik)
	{
		try{
		// Odczytaj obrazek z pliku oryginalnego
			File plikOryginalny = new File(nazwaPliku);
			BufferedImage obrazek = ImageIO.read(plikOryginalny);
			
			// Obrazek początkowy
			int szerokoscPoczatkowa = obrazek.getWidth();
			int wysokoscPoczatkowa = obrazek.getHeight();

			// Obrazek transformowany
			int szerokoscTransformed = (int)(szerokoscPoczatkowa * Math.abs(macierz[0][0]) + wysokoscPoczatkowa * Math.abs(macierz[0][1]));
			int wysokoscTransformed  = (int)(szerokoscPoczatkowa * Math.abs(macierz[1][0]) + wysokoscPoczatkowa * Math.abs(macierz[1][1]));
			
			BufferedImage nowyObrazek = new BufferedImage(szerokoscTransformed,wysokoscTransformed,BufferedImage.TYPE_INT_ARGB);
			
			int yDolGora, x00NaSrodku, y00NaSrodku;
			double xT, yT;

			for (int x=0; x<szerokoscPoczatkowa; x++)
			{
				for (int y=0; y<wysokoscPoczatkowa; y++)
				{
					yDolGora = wysokoscPoczatkowa-1 - y;
					x00NaSrodku = x - (int)szerokoscPoczatkowa/2;
					y00NaSrodku = yDolGora - (int)wysokoscPoczatkowa/2;
					
					xT = (x00NaSrodku * macierz[0][0]) + (y00NaSrodku * macierz[0][1]);
					yT = (x00NaSrodku * macierz[1][0]) + (y00NaSrodku * macierz[1][1]);
					
					xT = xT + szerokoscTransformed/2;
					yT = yT + wysokoscTransformed/2;
					yT = wysokoscTransformed-1 - yT;


					if ((int)xT<szerokoscTransformed && (int)yT<wysokoscTransformed && xT>=0 && yT>=0){
						//System.out.println(x+" , "+yDolGora + "\t->\t" + (int)xT+" , "+(int)yT);
						nowyObrazek.setRGB((int)xT,(int)yT,obrazek.getRGB(x,y));
					}
				}
			}

			
			File nowyPlik = new File("nowy" + nazwaPliku);
			ImageIO.write(nowyObrazek, "png", nowyPlik);
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
		
    }//public static void transformacja(String nazwaPliku, double[][] macierz

	
} //public class Transformacja
