package robot;

import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	private int delay;
	private boolean flag = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("自动点击");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 236, 79);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("时间间隔：");
		label.setBounds(10, 10, 74, 15);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(94, 7, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblMs = new JLabel("ms");
		lblMs.setBounds(170, 10, 22, 15);
		contentPane.add(lblMs);

		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, (int) 'Q');
		JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT, (int) 'O');
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			@Override
			public void onHotKey(int markCode) {
				Thread t = null;
				switch (markCode) {
				case 1:
					delay = Integer.parseInt(MainWindow.this.textField.getText());
					try {
						final Robot robot = new Robot();
						robot.delay(1000);
						flag = true;
						t = new Thread(){
							public void run() {
								while (flag) {
									robot.mousePress(InputEvent.BUTTON1_MASK);
									robot.mouseRelease(InputEvent.BUTTON1_MASK);
									robot.delay(delay);
								}
							};
						};
						t.start();
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;
				case 2:
					flag = false;
					t = null;
					break;
				}
			}
		});
	}
}
