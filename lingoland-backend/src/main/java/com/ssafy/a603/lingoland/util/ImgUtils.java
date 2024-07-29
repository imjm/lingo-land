package com.ssafy.a603.lingoland.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImgUtils {
	private final String DEFAULT_GROUP_IMAGE_PATH = "default.jpg";

	public String saveImage(MultipartFile img, String path) {
		String savedFilename = UUID.randomUUID().toString();    //파일 이름 중복 방지
		String fileName = img.getOriginalFilename();
		if (fileName == null) {
			throw new RuntimeException("fileName cannot be null");
		}
		int extensionIdx = fileName.lastIndexOf(".");
		String extension = fileName.substring(extensionIdx + 1);

		savedFilename = savedFilename + '.' + extension;
		String savedPath = "C:/upload/" + path + '/' + savedFilename;

		File file = new File(savedPath);

		try {
			img.transferTo(file);
			return savedFilename;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteImage(String imgPath, String path) {
		Path filePath = Paths.get("C:/upload/" + path + '/' + imgPath);
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getDefaultGroupImagePath() {
		return this.DEFAULT_GROUP_IMAGE_PATH;
	}
}
