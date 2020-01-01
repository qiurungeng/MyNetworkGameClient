
package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Winston Smith
 *	ͼƬ������
 */

public class ImageUtil {

	public static BufferedImage getImg(String path) {
		// ����ͼƬ ��װ��ȡͼƬ����
		try {
			// �����ȡͼƬ
			BufferedImage img = ImageIO.read(ImageUtil.class.getResource(path));
			return img;
		} catch (IOException e) {
			// ������Ҳ�����ԭ��
			e.printStackTrace();
		}
		return null;
	}
}
