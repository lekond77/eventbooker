package com.leon.event.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.leon.event.config.FileStorageConfig;

@Service
public class QrCodeService {

	@Autowired
	private FileStorageConfig properties;
	public String generateQRCode(String data) throws Exception {
		

		int size = 250; 
		String fileType = "png";

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300, hints);
		BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		
		for (int x = 0; x < 300; x++) {
			for (int y = 0; y < 300; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
			}
		}
		
		String qrCodeFileName = UUID.randomUUID().toString() + ".png";
		
		String uploadDir = Paths.get(properties.getLocation()).toFile().getAbsolutePath();
		
		File outputFile = new File(uploadDir, qrCodeFileName);
        
        ImageIO.write(image, fileType, outputFile);
        
		return qrCodeFileName;
	}

}
