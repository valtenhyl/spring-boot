package com.valten.service.impl;

import com.valten.comm.Const;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.valten.service.WatermarkService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ImgWatermarkServiceImpl implements WatermarkService {

    @Override
    public String watermarkAdd(File imageFile, String imageFileName, String uploadPath, String realUploadPath) {

        String logoFileName = "watermark_" + imageFileName;
        OutputStream os = null;

        try {
            Image image = ImageIO.read(imageFile);

            int width = image.getWidth(null);
            int height = image.getHeight(null);

            // 创建图片缓存对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建绘绘图工具对象
            Graphics2D g = bufferedImage.createGraphics();
            // 使用绘图工具将原图绘制到缓存图片对象
            g.drawImage(image, 0, 0, width, height, null);

            // 水印图片地址
            String logoPath = realUploadPath + "/" + Const.LOGO_FILE_NAME;
            // 读取水印图片
            File logo = new File(logoPath);
            Image imageLogo = ImageIO.read(logo);

            // 水印图片的宽度和高度
            int markWidth = imageLogo.getWidth(null);
            int markHeight = imageLogo.getHeight(null);

            // 设置水印透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Const.ALPHA));
            // 旋转图片
            g.rotate(Math.toRadians(-10), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

            int x = Const.X;
            int y = Const.Y;

            int xInterval = Const.X_INTERVAL;
            int yInterval = Const.Y_INTERVAL;

            double count = 1.5;
            while (x < width * count) {  // 循环添加水印
                y = -height / 2;
                while (y < height * count) {
                    g.drawImage(imageLogo, x, y, null);  // 添加水印
                    y += markHeight + yInterval;
                }
                x += markWidth + xInterval;
            }

            g.dispose();

            os = new FileOutputStream(realUploadPath + "/" + logoFileName);
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(bufferedImage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return uploadPath + "/" + logoFileName;
    }

}
