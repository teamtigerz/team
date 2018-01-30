package com.spring.common.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	static Logger logger = Logger.getLogger(FileUploadUtil.class);

	/* 파일 업로드할 폴더 생성 */
	public static void makeDir(String docRoot) {
		File fileDir = new File(docRoot);
		if (fileDir.exists()) {
			return;
		}
		fileDir.mkdirs();
	}

	/* 파일 업로드 메서드 */
	public static String fileUpload(MultipartFile file, HttpServletRequest request, String fileName)
			throws IOException {
		logger.info("fileUpload 호출 성공 ");

		String real_name = null;
		// MultipartFile 클래스의 getFile() 메서드로 클라이언트가 업로드한 파일
		String org_name = file.getOriginalFilename();
		logger.info("org_name : " + org_name);

		// 파일명 변경(중복되지 않게)
		if (org_name != null && (!org_name.equals(""))) {
			real_name = fileName + "_" + System.currentTimeMillis() + "_" + org_name;
			// 저장할 파일 이름

			String docRoot = request.getSession().getServletContext().getRealPath("uploadStorage/" + fileName);
			makeDir(docRoot);

			File fileAdd = new File(docRoot + "/" + real_name); // 파일 생성후
			logger.info("업로드할 파일(fileAdd): " + fileAdd);

			file.transferTo(fileAdd);
		}
		return real_name;
	}

	/* 파일 삭제 메서드 */
	public static void fileDelete(String fileName, HttpServletRequest request) throws IOException {
		logger.info("fileDelete 호출 성공 ");
		boolean result = false;
		String dirName = fileName.substring(0, fileName.indexOf("_"));
		String docRoot = request.getSession().getServletContext().getRealPath("/uploadStorage/" + dirName);

		File fileDelete = new File(docRoot + "/" + fileName); // 파일 생성후
		logger.info("삭제할 파일(fileDelete) : " + fileDelete);
		if (fileDelete.exists() && fileDelete.isFile()) {
			result = fileDelete.delete();
		}
		logger.info("파일 삭제 여부(true/false) : " + result);
	}

	/* 파일 Thumbnail 생성 메서드 */
	public static String makeThumbnail(String fileName, HttpServletRequest request) throws Exception {
		String dirName = fileName.substring(0, fileName.indexOf("_"));
		// 이미지가 존재하는 폴더 추출
		String imgPath = request.getSession().getServletContext().getRealPath("/uploadStorage/" + dirName);
		// 추출된 폴더의 실제 경로(물리적 위치: C:\...)
		File fileAdd = new File(imgPath, fileName);
		logger.info("원본 이미지 파일(fileAdd) : " + fileAdd);
		
		BufferedImage sourceImg = ImageIO.read(fileAdd);
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 133);
		// resize(대상[BufferedImage 타입], 원본비율, 높이 또는 너비, 크기)
		
		String thumbnailName = "thumbnail_" + fileName;
		String docRoot = imgPath + "/thumbnail";
		makeDir(docRoot);
		
		File newFile = new File(docRoot, thumbnailName);
		logger.info("업로드할 파일(newFile) : " + newFile);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		logger.info("확장자(formatName) : " + formatName);
		
		ImageIO.write(destImg, formatName, newFile);
		return thumbnailName;

	}

}
