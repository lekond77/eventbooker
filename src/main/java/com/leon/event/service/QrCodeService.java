package com.leon.event.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

	public String generateQRCode(String data, String id) throws Exception {

		String fileType = "png";

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250, hints);
		BufferedImage image = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		for (int x = 0; x < 250; x++) {
			for (int y = 0; y < 250; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
			}
		}

		String qrCodeFileName = UUID.randomUUID().toString() + ".png";


		String uploadDir = getUploadDir(id);

		File outputFile = new File(uploadDir, qrCodeFileName);

		ImageIO.write(image, fileType, outputFile);

		return qrCodeFileName;
	}

	private String getUploadDir(String subPath) throws IOException {

		String baseDir = Paths.get(properties.getLocation(), subPath).toString();

		Path path = Paths.get(baseDir);

		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}

		return path.toAbsolutePath().toString();

	}

}
