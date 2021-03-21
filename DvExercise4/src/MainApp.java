import view.MainView;

public class MainApp {

	public static void main(String[] args) {
		MainView mv = new MainView();
		
        mv.f.pack();
        mv.f.setTitle("title");
        mv.f.setLocationRelativeTo(null);
        mv.f.setVisible(true);

	}

}
