package com.cp.img;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

/**
 * 图形相关测试
 * 
 * @author zengxm 2015年1月4日
 * 
 */
public class ImgTest {

	// 新建源图片和生成的小图片的文件对象
	private File fi;
	private File fo;

	@Before
	public void init() {
		fi = new File("d:/pics/20150105/1420455751.jpg");
		fo = new File("d:/1.jpg");
	}

	@Test
	public void testTransform() throws IOException {
		BufferedImage bsrc = null;
		bsrc = ImageIO.read(fi);
		// System.out.println(bsrc.getWidth() + "," + bsrc.getHeight());
		// 生成图像变换对象
		AffineTransform transform = new AffineTransform();
		int smallX = 120;
		int samllH = 120;
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
		ImageIO.write(bsmall, "jpeg", fo);
	}

	@Test
	public void loadImg() throws IOException {
		BufferedImage localImg = ImageIO.read(fi);
		System.out.println(localImg.getWidth());
		URL imgUrl = new URL("http://image.bradypod.com/ok.jpg");
		Image remoteImg = ImageIO.read(imgUrl);
		System.out.println(remoteImg.getWidth(null));
	}

	/*加水印*/
	@Test
	public void waterRemark() throws IOException {
		BufferedImage localImg = ImageIO.read(fi);
		int width = localImg.getWidth();
		int height = localImg.getHeight();
		// 重构一张图
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Color color = Color.red;
		Font font = new Font("微软雅黑", Font.ITALIC, 18);
		String remark = "树懒博客专用水印";
		int fx = width - (remark.getBytes().length * 12) - 10;
		int fh = height - (font.getSize() * 2) - 10;
		Graphics2D g = image.createGraphics();
		g.drawImage(localImg, 0, 0, width, height, null);
		g.setColor(color);// 颜色
		g.setFont(font);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
		g.drawString(remark, fx, fh);
		g.dispose();
		ImageIO.write(image, "jpg", fo);
	}

	@Test
	public void testLength() {
		String text = "中国ABC";
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		int len = (length % 2 == 0) ? length / 2 : length / 2 + 1;
		System.out.println(len);
	}

	@Test
	public void testString2Color() {
		String str = "#FD6E10";
		int i = Integer.parseInt(str.substring(1), 16);
		System.out.println("hex color to int: " + i);

		Color color = Color.GREEN;
		String R = Integer.toHexString(color.getRed());
		R = R.length() < 2 ? ('0' + R) : R;
		String B = Integer.toHexString(color.getBlue());
		B = B.length() < 2 ? ('0' + B) : B;
		String G = Integer.toHexString(color.getGreen());
		G = G.length() < 2 ? ('0' + G) : G;
		System.out.println("to hex string:" + "#" + R + B + G);
	}

	@Test
	public void hasAlpha() throws IOException, InterruptedException {

		Image image = ImageIO.read(fi);

		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			System.out.println(bimage.getColorModel().hasAlpha());
		} else {
			PixelGrabber pGrabber = new PixelGrabber(image, 0, 0, 1, 1, false);
			pGrabber.grabPixels();
			ColorModel colorModel = pGrabber.getColorModel();
			System.out.println(colorModel.hasAlpha());
		}
	}

}
