import java.io.IOException;
import javax.swing.*;

public class TestMain {
	public static void main(String[] args) throws IOException {
		Main aquarium = new Main();
		aquarium.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aquarium.setSize(800, 500);
		aquarium.setVisible(true);
	}
}
