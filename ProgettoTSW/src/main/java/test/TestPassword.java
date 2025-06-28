package test;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

public class TestPassword {
	public static int main() {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Inserisci la password da criptare: ");
		String password = in.next();
		
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		System.out.println(hashedPassword);		
		return 0;
	}

}
