package com.example.Memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // 일반적인 스프링 빈
public class FileManagerServices {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//실제 이미지가 저장될 경로를 변수선언.
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\User\\Desktop\\sts\\workspace\\images/";   //마지막에 / 꼭 넣어줘야된다함~  넣어줘야 경로를 찾는다함.
	
	//input : 멀티파트 파일, userLoginId, 
	//output : 이미지 path  
	public String saveFile(String userLoginId, MultipartFile file) {
		
		//userid와 파일을 파라미터로 던져준다.
		//파일 디렉토리 예 ) kihun_20221024/exmple.png           예)  아이디_올린시간/파일명. 파일명은 앵간하면 영어로.
		
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";           // kihun_20221024 까지 쓴거임.
		String filePath = FILE_UPLOAD_PATH + directoryName; // C:\\Users\\User\\Desktop\\sts\\workspace\\images/kihun_20221024/"
		
		
		//위에서 경로를 다 설정했으면 이제 경로에다가 파일을 만들 준비.
		File directory = new File(filePath);     
		if (directory.mkdir() == false) { // mkdir => 생성하는 메소드
			return null;       // directory 생성 실패시 null => 업로드 할게 없을때 null 로 나옴 . 또는 경로를 못찾을때.
			
		}
		
		//파일 업로드 byte 단위로 파일 업로드한다.
		try {
			byte[] bytes = file.getBytes(); // 걍 외우기.
			Path path = Paths.get(filePath + file.getOriginalFilename());      //사용자가 진짜 업로드한 파일이름 // 나중에 이렇게 하면 한글저장되니 영어로 바꿔줘야한다함. 한글로하면 매핑이 안된다함.
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		//성공 했다면 이미지 url path 를 리턴한다. (WebMvcConfig 에서 매핑한 이미지 path)
		// http://localhost:8080/images/kihun_20221024/exmple.png
		return "/images/" + directoryName + file.getOriginalFilename();
		
	}
	
	public void deleteFile(String imagePath) {
		//imagePath : /images/kihun_20221024/exmple.png
		
		//imagePath 에서 /images/ 구문을 제거
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if (Files.exists(path)) {
			try {
				Files.delete(path); //이미지를 삭제. (파일)
			} catch (IOException e) {
				log.error("[이미지 삭제] 이미지 삭제 실패 imagePath : {}" , imagePath);
			} 
			
		}
		
		
		//폴더 제거
		path = path.getParent();
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.error("[디렉토리 삭제] 디렉토리 삭제 실패 imagePath : {}" , imagePath);
			}
		}
		
	}
	
}
