package com.spring.common.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	static Logger logger = Logger.getLogger(FileUploadUtil.class);
	public static void makeDir(String docRoot) {
		File fileDir = new File(docRoot);
		if(fileDir.exists()) {
			return;
		}
		fileDir.mkdirs();
	}
	
	public static String fileUpload(MultipartFile file, HttpServletRequest request, String fileName) throws IOException{
		logger.info("fileUpload 호출 성공");
		
		String real_name = null;
		String org_name = file.getOriginalFilename();
		logger.info("org_name="+org_name);
		
		if(org_name  != null && (!org_name.equals(""))) {
			real_name = fileName + "_" + System.currentTimeMillis() + "_" + org_name;
			String docRoot = request.getSession().getServletContext().getRealPath("uploadStorage/"+fileName);
			makeDir(docRoot);
			
			File fileAdd = new File(docRoot + "/" + real_name);
			logger.info("업로드 할 파일(fileAdd) : " + fileAdd);
			file.transferTo(fileAdd);
		}
		return real_name;
	}
	
	public static void fileDelete(String fileName, HttpServletRequest request) throws IOException{
		logger.info("fileDelete 호출 성공");
		
		boolean result = false;
		String dirName = fileName.substring(0, fileName.indexOf("_"));
		String docRoot = request.getSession().getServletContext().getRealPath("/uploadStorage/" + dirName);
		
		File fileDelete = new File(docRoot+"/" + fileName);
		logger.info("삭제할 파일(fileDelete) : " + fileDelete);
		if(fileDelete.exists() && fileDelete.isFile()) {
			result = fileDelete.delete();
		}
		logger.info("파일 삭제 여부(true/false): " + result);
	}
}
