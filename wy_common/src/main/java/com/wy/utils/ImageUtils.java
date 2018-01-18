package com.wy.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 * 
 * @author lingy
 *
 */
public final class ImageUtils {

	/** 图片格式：JPG */
	private static final String PICTRUE_FORMATE_JPG = "jpg";

	private ImageUtils() {
	}

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @throws Exception
	 */
	public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) throws Exception {
		File file = new File(targetImg);
		Image image = ImageIO.read(file);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);

		Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
		int width_1 = waterImage.getWidth(null);
		int height_1 = waterImage.getHeight(null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

		int widthDiff = width - width_1;
		int heightDiff = height - height_1;
		if (x < 0) {
			x = widthDiff / 2;
		} else if (x > widthDiff) {
			x = widthDiff;
		}
		if (y < 0) {
			y = heightDiff / 2;
		} else if (y > heightDiff) {
			y = heightDiff;
		}
		g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
		g.dispose();

		String prefix = getPrefixName(targetImg);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write(bufferedImage, prefix, file);
	}

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @throws Exception
	 */
	public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize,
			Color color, int x, int y, float alpha) throws Exception {
		File file = new File(targetImg);

		Image image = ImageIO.read(file);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

		int width_1 = fontSize * getLength(pressText);
		int height_1 = fontSize;
		int widthDiff = width - width_1;
		int heightDiff = height - height_1;
		if (x < 0) {
			x = widthDiff / 2;
		} else if (x > widthDiff) {
			x = widthDiff;
		}
		if (y < 0) {
			y = heightDiff / 2;
		} else if (y > heightDiff) {
			y = heightDiff;
		}

		g.drawString(pressText, x, y + height_1);
		g.dispose();

		String prefix = getPrefixName(targetImg);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write(bufferedImage, prefix, file);
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}

	/**
	 * 图片缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 * @throws Exception
	 */
	public static void resize(String filePath, int height, int width, boolean bb) throws Exception {
		double ratio = 0; // 缩放比例
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
			if (bi.getHeight() > bi.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / bi.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / bi.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(bi, null);
		}
		if (bb) {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null))
				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			else
				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			g.dispose();
			itemp = image;
		}

		String prefix = getPrefixName(filePath);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write((BufferedImage) itemp, prefix, f);
	}

	/**
	 * 图片缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param savePath
	 *            缩放后的图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 * @throws Exception
	 */
	public static void resize(String filePath, String savePath, int height, int width, boolean bb) throws Exception {
		double ratio = 0; // 缩放比例
		File f = new File(filePath);
		File saveFile = new File(savePath);
		BufferedImage bi = ImageIO.read(f);
		Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
			if (bi.getHeight() > bi.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / bi.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / bi.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(bi, null);
		}
		if (bb) {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null))
				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			else
				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			g.dispose();
			itemp = image;
		}

		String prefix = getPrefixName(filePath);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write((BufferedImage) itemp, prefix, saveFile);
	}

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param saveImg
	 *            保存添加水印后的图片路径，如：C://myPictrue//1.1.jpg
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @param alpha
	 *            缩放比例，根据目标图片的大小对水印图片进行缩放. 1:不缩放. >0&&<1: 缩小.
	 * @throws Exception
	 */
	public final static void pressImage(String targetImg, String waterImg, String saveImg, int x, int y, float alpha,
			float zoom) throws Exception {
		File file = new File(targetImg);
		File saveFile = new File(saveImg);
		Image image = ImageIO.read(file);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);

		File waterFile = new File(waterImg);
		Image waterImage = ImageIO.read(waterFile); // 水印文件
		int width_1 = waterImage.getWidth(null);
		int height_1 = waterImage.getHeight(null);

		BufferedImage waterBufferedImage = null;
		// 对水印图片进行缩放
		if (zoom > 0 && zoom < 1 && (width < width_1 || height < height_1)) {
			int w_n = (int) (width * zoom);// 缩放后的水印图片的宽度
			int h_n = (int) ((width * zoom) / width_1 * height_1);// 缩放后的水印图片的高度

			// 缩放后的宽度和高度小于原水印图片的宽度和高度时，则进行缩小
			if (w_n < width_1 || h_n < height_1) {
				double ratio = 0; // 缩放比例
				if (h_n > w_n) {
					ratio = (new Integer(height_1)).doubleValue() / h_n;
				} else {
					ratio = (new Integer(width_1)).doubleValue() / w_n;
				}

				waterBufferedImage = ImageIO.read(waterFile);
				Image itemp = waterBufferedImage.getScaledInstance(w_n, h_n, BufferedImage.SCALE_SMOOTH);
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(waterBufferedImage, null);
				width_1 = w_n;
				height_1 = h_n;
			}
		}

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

		int widthDiff = width - width_1;
		int heightDiff = height - height_1;
		if (x < 0) {
			x = widthDiff / 2;
		} else if (x > widthDiff) {
			x = widthDiff;
		}
		if (y < 0) {
			y = heightDiff / 2;
		} else if (y > heightDiff) {
			y = heightDiff;
		}
		if (waterBufferedImage != null) {
			g.drawImage(waterBufferedImage, x, y, width_1, height_1, null); // 水印文件结束
		} else {
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
		}
		g.dispose();

		String prefix = getPrefixName(targetImg);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write(bufferedImage, prefix, saveFile);
	}

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @param zoom
	 *            缩放比例，根据目标图片的大小对水印图片进行缩放. 1:不缩放. >0&&<1: 缩小.
	 * @throws Exception
	 */
	public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha,
			float zoom) throws Exception {
		File file = new File(targetImg);
		Image image = ImageIO.read(file);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);

		File waterFile = new File(waterImg);
		Image waterImage = ImageIO.read(waterFile); // 水印文件
		int width_1 = waterImage.getWidth(null);
		int height_1 = waterImage.getHeight(null);

		BufferedImage waterBufferedImage = null;
		// 对水印图片进行缩放
		if (zoom > 0 && zoom < 1 && (width < width_1 || height < height_1)) {
			int w_n = (int) (width * zoom);// 缩放后的水印图片的宽度
			int h_n = (int) ((width * zoom) / width_1 * height_1);// 缩放后的水印图片的高度

			// 缩放后的宽度和高度小于原水印图片的宽度和高度时，则进行缩小
			if (w_n < width_1 || h_n < height_1) {
				double ratio = 0; // 缩放比例
				if (h_n > w_n) {
					ratio = (new Integer(height_1)).doubleValue() / h_n;
				} else {
					ratio = (new Integer(width_1)).doubleValue() / w_n;
				}

				waterBufferedImage = ImageIO.read(waterFile);
				Image itemp = waterBufferedImage.getScaledInstance(w_n, h_n, BufferedImage.SCALE_SMOOTH);
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(waterBufferedImage, null);
				width_1 = w_n;
				height_1 = h_n;
			}
		}

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

		int widthDiff = width - width_1;
		int heightDiff = height - height_1;
		if (x < 0) {
			x = widthDiff / 2;
		} else if (x > widthDiff) {
			x = widthDiff;
		}
		if (y < 0) {
			y = heightDiff / 2;
		} else if (y > heightDiff) {
			y = heightDiff;
		}
		if (waterBufferedImage != null) {
			g.drawImage(waterBufferedImage, x, y, width_1, height_1, null); // 水印文件结束
		} else {
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
		}
		g.dispose();

		String prefix = getPrefixName(targetImg);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write(bufferedImage, prefix, file);
	}

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param saveImg
	 *            保存添加水印后的图片路径，如：C://myPictrue//1.1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @throws Exception
	 */
	public static void pressText(String targetImg, String saveImg, String pressText, String fontName, int fontStyle,
			int fontSize, Color color, int x, int y, float alpha) throws Exception {
		File file = new File(targetImg);
		File saveFile = new File(saveImg);

		Image image = ImageIO.read(file);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

		int width_1 = fontSize * getLength(pressText);
		int height_1 = fontSize;
		int widthDiff = width - width_1;
		int heightDiff = height - height_1;
		if (x < 0) {
			x = widthDiff / 2;
		} else if (x > widthDiff) {
			x = widthDiff;
		}
		if (y < 0) {
			y = heightDiff / 2;
		} else if (y > heightDiff) {
			y = heightDiff;
		}

		g.drawString(pressText, x, y + height_1);
		g.dispose();
		
		String prefix = getPrefixName(saveImg);
		if(prefix == null || prefix.isEmpty()){
			prefix = PICTRUE_FORMATE_JPG;
		}
		ImageIO.write(bufferedImage, prefix, saveFile);
	}
	
	/**
	 * 从文件名中获取后缀名
	 * @param filePath 文件名称或路径，包含后缀名
	 * @return 后缀名
	 */
	public static String getPrefixName(String filePath){
		if(filePath == null || filePath.isEmpty())	return "";
		
		int index = filePath.lastIndexOf(".");
		if(index == -1)	return "";
		String prefix = filePath.substring(index + 1);
		
		return prefix;
	}

	/**
	 * 给现有的文件名称加上指定的名称.
	 * eg:/opt/1.png. 需要加入min名称. 最终结果为: /opt/1.min.png
	 * @param path 文件路径，含后缀
	 * @param name 需要在后缀前加入的名称
	 * @return
	 */
	public static String addNameToPath(String path, String name){
		if(path == null || path.isEmpty())	return "";
		int index = path.lastIndexOf(".");
		if(index < 1)	return "";
		
		String newPath = path.substring(0, index);
		newPath += "." + name + ".";
		newPath += path.substring(index + 1);
		
		return newPath;
	}
	
	public static void main(String[] args) throws Exception {
		// 添加文字水印
		// pressText("D:/1.jpg", "test", "宋体", Font.BOLD, 24, Color.red, 50, 50,
		// 0.3f);
		// 图片缩放
		resize("D:/1.jpg", 100, 600, false);
		System.out.println("处理完毕！");
	}

}
