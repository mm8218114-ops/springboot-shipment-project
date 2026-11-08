package jsp.springboot.springboot_demo_curd;

public class IdNotFoundException extends RuntimeException {
	
	IdNotFoundException(String message) {
		super(message);
	}

}
