import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
	public static void main(String[] args) {
    // The provided images are apple.jpg, flower.jpg, and kitten.jpg
		int[][] imageData = imgToTwoD("./flower.jpg");
    // Or load your own image using a URL!
		//int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
		// viewImageData(imageData);
		// int[][] trimmed = trimBorders(imageData, 60);
		// twoDToImage(trimmed, "./trimmed_apple.jpg");
		// int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
		// Painting with pixels

    // Negative Version of the Image
    int[][] negative = negativeColor(imageData);
		twoDToImage(negative, "./negative_flower.jpg");

    // Strech the Iamge Horizontally
    int[][] stretch = stretchHorizontally(imageData);
		twoDToImage(stretch, "./stretch_flower.jpg");

    // Shrink the Image Vertically
    int[][] shrink = shrinkVertically(imageData);
		twoDToImage(shrink, "./shrink_flower.jpg");

    // Invert the Image
    int[][] invert = invertImage(imageData);
		twoDToImage(invert, "./invert_flower.jpg");

    // Applying a Color Filter
    int[][] filter = colorFilter(imageData, 50, 50, 50);
		twoDToImage(filter, "./filter_flower.jpg");

    // Further Challenges: Painting an Image of Random Colors
    int[][] newCanvas = new int[500][500];
    int[][] randomImage = paintRandomImage(newCanvas);
    twoDToImage(randomImage, "./random_image.jpg");

    // Drawing a Rectangle on an Image
    int[] rgba = {50, 200, 200, 255};
    int[][] rectangle = paintRectangle(randomImage, 200, 200, 100, 100, getColorIntValFromRGBA(rgba));
    twoDToImage(rectangle, "./rectangle.jpg");

    // Create Abstract Geometric Art Utilizing the paintRectangle Method
    int[][] generatedRectangles = generateRectangles(randomImage, 1000);
		twoDToImage(generatedRectangles, "./generated_rect.jpg");
	}
	// Image Processing Methods
	public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		// Example Method
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}
	public static int[][] negativeColor(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] negativeOut = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i=0; i<imageTwoD.length; i++) {
      for (int j=0; j<imageTwoD[i].length; j++) {
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] = 255 - rgba[0];
        rgba[1] = 255 - rgba[1];
        rgba[2] = 255 - rgba[2];
        negativeOut[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return negativeOut;
	}
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] stretchOut = new int[imageTwoD.length][imageTwoD[0].length*2];
		int it = 0;
    for (int i=0; i<imageTwoD.length; i++) {
      for (int j=0; j<imageTwoD[i].length; j++) {
        it = j*2;
        stretchOut[i][it] = imageTwoD[i][j];
        stretchOut[i][it+1] = imageTwoD[i][j];
      }
    }
    return stretchOut;
	}
	public static int[][] shrinkVertically(int[][] imageTwoD) {
    int[][] shrinkOut = new int[imageTwoD.length/2][imageTwoD[0].length];
    for (int j=0; j<imageTwoD[0].length; j++) {
      for (int i=0; i<imageTwoD.length-1; i+=2) {
        shrinkOut[i/2][j] = imageTwoD[i][j];
      }
    }
		return shrinkOut;
	}
	public static int[][] invertImage(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] invertOut = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i=0; i<imageTwoD.length; i++) {
      for (int j=0; j<imageTwoD[i].length; j++) {
        invertOut[i][j] = imageTwoD[imageTwoD.length-1-i][imageTwoD[i].length-1-j];
      }
    }
		return invertOut;
	}
	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
		// TODO: Fill in the code for this method
    int[][] filterOut = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i=0; i<imageTwoD.length; i++) {
      for (int j=0; j<imageTwoD[i].length; j++) {
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] += redChangeValue;
        rgba[1] += greenChangeValue;
        rgba[2] += blueChangeValue;
        for (int f=0; f<3; f++) {
          if (rgba[f] < 0) {
            rgba[f] = 0;
          } else if (rgba[f] > 255) {
            rgba[f] = 255;
          }
        }
        filterOut[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return filterOut;
	}
	// Painting Methods
	public static int[][] paintRandomImage(int[][] canvas) {
		// TODO: Fill in the code for this method
    Random rand = new Random();
    for (int i=0; i<canvas.length; i++) {
      for (int j=0; j<canvas[i].length; j++) {
        int randInt1 = rand.nextInt(256);
        int randInt2 = rand.nextInt(256);
        int randInt3 = rand.nextInt(256);
        int[] randArray = {randInt1, randInt2, randInt3, 255};
        canvas[i][j] = getColorIntValFromRGBA(randArray);
      }
    }
		return canvas;
	}
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
		// TODO: Fill in the code for this method
    for (int i=0; i<canvas.length; i++) {
      for (int j=0; j<canvas[i].length; j++) {
        if(i>=rowPosition && i<=rowPosition + width) {
          if(j>=colPosition && j<=colPosition + height) {
              canvas[i][j] = color;
          }
        }
      }
    }
		return canvas;
	}
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
		// TODO: Fill in the code for this method
		Random rand = new Random();
		for (int i = 0; i < numRectangles; i++) {
			int randomWidth = rand.nextInt(canvas[0].length);
			int randomHeight = rand.nextInt(canvas.length);
			int randomRowPos = rand.nextInt(canvas.length - randomHeight);
			int randomColPos = rand.nextInt(canvas[0].length - randomWidth);
			int[] rgba = { rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255 };
			int randomColor = getColorIntValFromRGBA(rgba);
			canvas = paintRectangle(canvas, randomWidth, randomHeight, randomRowPos, randomColPos, randomColor);
		}
		return canvas;
	}
	// Utility Methods
	public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}
