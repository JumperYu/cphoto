package com.cp.utils;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 导入本地图片到缓冲区
	 * 
	 * @param imgPath
	 *            本地的图片地址
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage loadImageLocal(String imgPath)
			throws IOException {
		return ImageIO.read(new File(imgPath));
	}

	/**
	 * 导入网络图片到缓冲区
	 * 
	 * @param imgUrl
	 *            网络图片url
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage loadImageUrl(String imgUrl) throws IOException {
		URL url = new URL(imgUrl);
		return ImageIO.read(url);
	}

	public static void transform(File fi, OutputStream fo, int smallX, int samllH,
			String contentType) throws IOException {
		BufferedImage bsrc = null;
		bsrc = ImageIO.read(fi);
		// System.out.println(bsrc.getWidth() + "," + bsrc.getHeight());
		// 生成图像变换对象
		AffineTransform transform = new AffineTransform();
		double sx = (double) smallX / bsrc.getWidth();
		double sy = (double) samllH / bsrc.getHeight();
		transform.setToScale(sx, sy);
		// 生成图像转换操作对象
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		// 生成缩小图像的缓冲对象
		BufferedImage bsmall = new BufferedImage(smallX, samllH,
				BufferedImage.TYPE_3BYTE_BGR);
		// 生成小图像
		ato.filter(bsrc, bsmall);
		// 输出小图像
		ImageIO.write(bsmall, contentType, fo);
	}

	/**
	 * 16进制转Color对象
	 * 
	 * @param str
	 * @return
	 */
	public static Color String2Color(String str) {
		int i = Integer.parseInt(str.substring(1), 16);
		return new Color(i);
	}

	/**
	 * Color对象转16进制
	 * 
	 * @param color
	 * @return
	 */
	public static String Color2String(Color color) {
		String R = Integer.toHexString(color.getRed());
		R = R.length() < 2 ? ('0' + R) : R;
		String B = Integer.toHexString(color.getBlue());
		B = B.length() < 2 ? ('0' + B) : B;
		String G = Integer.toHexString(color.getGreen());
		G = G.length() < 2 ? ('0' + G) : G;
		return '#' + R + B + G;
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2 text="test",返回 2 text="中国ABC",返回 4
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

}
