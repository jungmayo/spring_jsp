package ch01;


// 생성, 구조, 행위

// 빌더 패턴
// 사전 기반 = this. 사용, this() <--
public class Person {
	
	// 멤버 변수 선언(final이므로 초기화를 해주어야함)
	private final String name;
	private final int age;
	
	// 외부 클래스 생성자
	private Person(PersonBuilder builder) {
		this.name = builder.name;
		this.age = builder.age;
	}
	
	// Builder 클래스 정의
	// static 내부 클래스로 정의 된다
	
	public static class PersonBuilder{
		private String name;
		private int age;
		
		public PersonBuilder() {} // 명시적인 생성자가 정의되어 있기때문에 기본생성자도 다시 생성해줘야 함.
		
		// 초기화 - 필수 속성 초기화 하는 생성자
		public PersonBuilder(String name, int age) {
			this.name = name;
			this.age = age;
		}
		
		// builder의 장점 : 선택적 속성을 설정할 수 있음
		// 선택적 속성을 설정하는 메서드
		public PersonBuilder name(String name) {
			this.name = name;
			return this; // 메서드 체이닝을 위해 this를 반환
		}
		public PersonBuilder age(int age) {
			this.age = age;
			return this; // 메서드 체이닝을 위해 this를 반환
		}
		
		// 빌더 패턴의 마지막 핵심
		public Person build() {
			return new Person(this); // 이 시점의 this는 PersonBuilder가 된다.
		}
	}
	
	public static void main(String[] args) {
		
		
		
		//Person person = new Person.PersonBuilder("정마요", 10).build();
		Person person = new Person.PersonBuilder().age(10).name("정마요").build();
	}
	
	
	

}
