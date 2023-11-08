package com.memo.common;




import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component   //   spring bean    의미없는 spring bean
public class FileManagerService {    // 중복 최소화 하기 위해 => 유지 ,보수 , 수정 용이
	
	private Logger log = LoggerFactory.getLogger(FileManagerService.class);
	
	
	// 실제 업로드가 된 이미지가 저장될 경로 (서버)
	// 학원 경로
	//public static final String FILE_UPLOAD_PATH = "D:\\오승환\\5_spring_project\\memo\\workspace\\images/"; // 한번 변경 후 절대 바꿀 수 없음
	
	// 집 경로
	public static final String FILE_UPLOAD_PATH = "C:\\오승환\\6_spring_project\\memo\\workspace\\images/"; // 한번 변경 후 절대 바꿀 수 없음
	
	
	// input : userLoginId, file(이미지 파일)    output : 웹 imagePath
	public String saveFile(String loginId, MultipartFile file) {
		// 폴더 생성
		// 예 : aaaa_1230951/sun.png     // 밀리 세컨드
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		if(!directory.mkdir()) {
			// 폴더 생성 실패 시 경로 null
			return null;
		};
		
		// 파일 업로드 : byte 단위로 업로드
		try {
			byte[] bytes = file.getBytes();
			//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			//	한글 이름 이미지는 올릴 수 없으므로 나중에 영문자로 바꿔서 올리기 구현까지
			//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename());   // 디렉토리 경로 + 사용자가 올린 파일 명
			Files.write(path, bytes);
		} catch (IOException e) {
			log.error("[이미지 업로드] 업로드 실패 : ");
			return null;  					// 이미지 업로드 실패 시 null 리턴   =>   이미지는 필수가아님
		}
		
		// 파일 업로드가 성공 => 웹 이미지 url path를 리턴
		// 주소는 이렇게 될 것이다. (예언)
		// http://localhost/images/aaaa_1698922766893/sun.png
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
		
	}
	
	// input : imagePath
	// output : X
	public void deleteFile(String imagePath) { // imagePath:  /images/aaaa_1698925017926/novel.png
		
		// D:\\오승환\\5_spring_project\\memo\\workspace\\images//images/aaaa_1698925017926/novel.png
		// 주소에 겹치는 /images/ 제거
		
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if (Files.exists(path)) {  // 이미지가 존재하는가?  =>  있으면 삭제
			// 이미지 삭제   =>    실패 시 BO영향 X
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[이미지 삭제] 파일 삭제 실패. imagePath:{}", imagePath);
				return;
			}
			
			// 디렉토리 삭제
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[이미지 삭제] 폴더 삭제 실패. imagePath:{}", imagePath);
				}
			}
			
		}
		
	}
	
	
}
