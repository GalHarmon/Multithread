import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome To the Best Pizzeria in the World");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setBounds(12, 13, 387, 73);
		contentPane.add(lblNewLabel);

		textField = new JTextField("2"); // default number for pizza guy drivers
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(301, 99, 72, 23);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField("1"); // default number for the kitchen work time
		textField_1.setColumns(10);
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setBounds(301, 135, 72, 23);
		contentPane.add(textField_1);

		JLabel lblNewLabel_1 = new JLabel("    Enter number of Delivery guys");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_1.setBounds(12, 99, 205, 17);
		contentPane.add(lblNewLabel_1);

		JLabel lblEnterKitchenWork = new JLabel("    Enter Kitchen Worker Work Time");
		lblEnterKitchenWork.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblEnterKitchenWork.setBounds(12, 146, 205, 17);
		contentPane.add(lblEnterKitchenWork);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 12));
		btnNewButton.setBounds(68, 203, 93, 25);
		contentPane.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { // when the user press on start it defines Pizzeria and start
															// to read from file
				int NumOfDeliveries;
				double KWtime;

				NumOfDeliveries = Integer.parseInt(textField.getText()); // cast from String to int

				KWtime = Double.parseDouble(textField_1.getText()); // cast from String to double
				try {
					Pizzeria startPizzeria = new Pizzeria("Call.txt", NumOfDeliveries, KWtime);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton btnNewButton_1 = new JButton("End Program");
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 12));
		btnNewButton_1.setBounds(217, 203, 128, 25);
		contentPane.add(btnNewButton_1);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // ending the Program

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
