package ch02;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

import javax.management.RuntimeErrorException;

public class StrategyTest {

	public static void main(String[] args) {
		
		// 문자열
		// ---> 상대방에게 문자열을 보내야 한다
		// ----> 일반 텍스트
		// -----> Base64 인코딩 방식
		// ------> 특별한 방식으로 인코딩을 적용
		
		// 전략 패턴을 쓰지 않았을 때
		// 추상화를 높이는 것이 좋은 코드가 될 수 있다
		EncodingStrategy base64 = new Base64Strategy();
		EncodingStrategy normal = new NormalStrategy();
		EncodingStrategy append = new AppendStrategy();
		EncodingStrategy URL = new URLStrategy();
		
		String message = "1234머시기저시기지ㅓ시기";
		Encoder encoder = new Encoder();
		
		// Base64로 인코딩 해주세요
		encoder.setEncodingStrategy(base64);
		System.out.println(encoder.getMessage(message));
		
		// normal로 인코딩 해주세요
		encoder.setEncodingStrategy(normal);
		System.out.println(encoder.getMessage(message));

		// append로 인코딩 해주세요
		encoder.setEncodingStrategy(append);
		System.out.println(encoder.getMessage(message));
		
		// 도전 과제 -
		encoder.setEncodingStrategy(URL);
		System.out.println(encoder.getMessage(message));
		


		

	}
	
	

} // end of class

// 인코딩 전략(인터페이스가 핵심)
interface EncodingStrategy {
	String encode(String text);
}
// 바이트 010101 --> new File();
// 서버측 데이터를 API --- json (json은 문자열)
// Base64 인코딩


// 어떤 문자열을 64개로 변환하는 기능이 만들어져 있음.
class Base64Strategy implements EncodingStrategy{

	@Override
	public String encode(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes()); //getEncoder라는 객체를 얻어서 encodeToString메서드를 실행시킨// text를 바이트로 변환해서 넣어주면 됨.
	}
}

//일반 텍스트 전략
class NormalStrategy implements EncodingStrategy{

	@Override
	public String encode(String text) {
		return text;
	}
}

// 문자열 ABC를 붙여서 보내라
class AppendStrategy implements EncodingStrategy{

	@Override
	public String encode(String text) {
		return "ABC" + text;
	}
}


//문자열 URL로 변경해서 보내라
class URLStrategy implements EncodingStrategy{

	// URLEncoder 사용해서 UTF-8 형식으로 인코딩
	// 공백, 특수문자 등을 % 형식으로 변환해서 전송할 수 있도록 한다.
	@Override
	public String encode(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("실패다");
		}
	}
}


// 클라이언트 클래스
class Encoder {
	
	// DI - 생성자 주입
	// DI - 메서드 주입 (setter)
	
	// 행동을 할 멤버 //encodingStrategy 노말이 될수도 base64가 될수도 append 될 수도
	// 인터페이스로 선언해야 여러가지 사용가능함
	EncodingStrategy encodingStrategy;
	
	// 전략에 따라서 실행할 메서드
	public String getMessage(String message) {
		return this.encodingStrategy.encode(message);
	}
	
	// 전략에 따라서 멤버를 변경할 수 있는 메서드
	public void setEncodingStrategy(EncodingStrategy encodingStrategy) {
		this.encodingStrategy = encodingStrategy;
		
	}
}