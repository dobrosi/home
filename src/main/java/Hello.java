import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class Hello {
	public static void main(String[] args) {
		GpioController c = GpioFactory.getInstance();
		System.out.println(c);

	}
}
