import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.*;


public class TutorialGUI extends JFrame{
	
	private static final int DEFAULT_MARK = 3;
	private JPanel contentPane;
	private JTextField edtTutMode;
	private JTextField edtMarkMode;
	private JTextField edtPenalty;
	private JTextField edtQFile;
	private static TutorialGUI frame;
	private JButton btnMarkingMode;
	private JButton btnTutorialMode;
	private JTextArea txtQuestions;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new TutorialGUI();
					//frame.setSize(new Dimension (300,300));
					//frame.pack();
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
	public TutorialGUI() 
	
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 761, 625);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0,0);
		contentPane.setLayout(cl);
		
		JPanel pnlConfig = new JPanel();
		pnlConfig.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlConfig.setLayout(null);
		contentPane.add(pnlConfig, "Config");
		
		edtPenalty = new JTextField();
		edtPenalty.setColumns(10);
		edtPenalty.setBounds(213, 182, 61, 29);
		pnlConfig.add(edtPenalty);
		
		JLabel lblMarkingConfiguration = new JLabel("Marking Configuration");
		lblMarkingConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMarkingConfiguration.setBounds(279, 29, 240, 29);
		pnlConfig.add(lblMarkingConfiguration);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the filepath for the question text file:");
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPleaseEnterThe.setBounds(44, 86, 428, 29);
		pnlConfig.add(lblPleaseEnterThe);

		//back button for configuration page
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Mark");
			}
		});
		btnBack.setBounds(523, 503, 159, 29);
		pnlConfig.add(btnBack);
		
		JButton btnSaveConfig = new JButton("Save");
		
		/*
		 * Save button for the marking Configuration
		 * 
		 */
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO: check question and tutorial objects have been set up correctly
				
				if (edtPenalty.getText().equals("")){
					JOptionPane.showMessageDialog(null,
				   "Please enter a penalty.",
				   "No penalty value",
				   	JOptionPane.WARNING_MESSAGE);
				}
				else{
					btnMarkingMode.setEnabled(true);
					btnTutorialMode.setEnabled(true);
					((CardLayout) contentPane.getLayout()).show(contentPane, "Mode");
				}
				
			}
		});
		
		
		btnSaveConfig.setBounds(523, 463, 159, 29);
		pnlConfig.add(btnSaveConfig);
		
		JLabel lblNewLabel_2 = new JLabel("Penalty Percentage:");
		lblNewLabel_2.setBounds(44, 189, 159, 14);
		pnlConfig.add(lblNewLabel_2);
		
		edtQFile = new JTextField();
		edtQFile.setColumns(10);
		edtQFile.setBounds(44, 126, 228, 29);
		pnlConfig.add(edtQFile);
		
JButton btnMakeQuestions = new JButton("Create Questions");
		
		/*
		 * Button in making configuration to make questions from a text file
		 * reads file of questions and create components
		 * sets up tutorial class and populates it with question objects
		 * 
		 */
		btnMakeQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				BufferedReader br = null;

				try {

					String line;
					//int count = 1;
					br = new BufferedReader(new FileReader(edtQFile.getText()));
					
					while ((line = br.readLine()) != null) {
						
						//Create the Question objects: format should be: expression [mark]
						
						String expression;
						int mark;
						
						//Check that there is a mark that follows the expression
						if (line.indexOf('[') == -1){
							expression = line;
							mark = DEFAULT_MARK;
						}
						else{
							expression = line.substring(0, line.indexOf('['));
							mark = Integer.parseInt(line.substring(line.indexOf('[')+1, line.indexOf(']')));
						}
						
						
						txtQuestions.append(expression + "\t" + mark + "\n");
						
						/*JLabel lblQuestion1 = new JLabel("(" + count + ") " + line);
						lblQuestion1.setBounds(10, 25 + (count-1)*25 + 5, 301, 14);
						//lblQuestion1.setSize(new Dimension(300,14));
						pnlQuestions.add(lblQuestion1);
						
						edtQuestion1 = new JTextField();
						edtQuestion1.setBounds(321, 22 + (count-1)*25 + 5, 70, 20);
						pnlQuestions.add(edtQuestion1);
						edtQuestion1.setColumns(10);
						edtQuestion1.setText(DEFAULT_MARK + "");
						count++;
						
						//pnlQuestions.validate();
						pnlQuestions.revalidate();
						pnlQuestions.repaint();
						*/
						
					}

				} catch (IOException e) {
					//error message
					JOptionPane.showMessageDialog(null,
					    "Please enter a valid text file location.",
					    "No text file",
					    JOptionPane.WARNING_MESSAGE);
				} finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						//error message
						JOptionPane.showMessageDialog(null,
						    "Please enter a valid text file location.",
						    "No text file",
						    JOptionPane.WARNING_MESSAGE);
					}
				}
				
				
				//TODO: create tutorial object with question/answer attributes
				
				
				
				
			}//end of actionperformed definition
		});//end of eventhandler
		
		
		btnMakeQuestions.setBounds(317, 126, 159, 29);
		pnlConfig.add(btnMakeQuestions);
		
		txtQuestions = new JTextArea();
		txtQuestions.setEditable(false);
		txtQuestions.setBounds(44, 264, 417, 175);
		//pnlConfig.add(txtQuestions);
		txtQuestions.setVisible(true);
		
		JScrollPane scroll = new JScrollPane (); 
				  //JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(44, 264, 663, 175);
		scroll.setViewportView(txtQuestions);
		pnlConfig.add(scroll);
		scroll.setVisible(true);
		
		JLabel lblConfig = new JLabel("The questions and their mark allocations are displayed below:");
		lblConfig.setBounds(44, 239, 638, 14);
		pnlConfig.add(lblConfig);
		
		JPanel pnlMarkMode = new JPanel();
		pnlMarkMode.setLayout(null);
		contentPane.add(pnlMarkMode, "Mark");
		
		edtMarkMode = new JTextField();
		edtMarkMode.setColumns(10);
		edtMarkMode.setBounds(44, 118, 228, 29);
		pnlMarkMode.add(edtMarkMode);
		
		JLabel lblMarkingMode = new JLabel("Marking Mode");
		lblMarkingMode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMarkingMode.setBounds(312, 27, 199, 29);
		pnlMarkMode.add(lblMarkingMode);
		
		JLabel label_2 = new JLabel("Please enter the filepath for the textfile you wish to submit:");
		label_2.setBounds(44, 86, 446, 29);
		pnlMarkMode.add(label_2);
		
		JButton btnSubmitMark = new JButton("Submit");
		
		/*
		 * User submits a file for marking:
		 * create answer objects and mark
		 * output feedback
		 */
		btnSubmitMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO
				
			}//end of actionPerformed
		});
		
		
		
		btnSubmitMark.setBounds(295, 118, 166, 29);
		pnlMarkMode.add(btnSubmitMark);
		
		JLabel lblMarkFeedback = new JLabel("The student's results are as follows:");
		lblMarkFeedback.setBounds(44, 199, 417, 14);
		pnlMarkMode.add(lblMarkFeedback);
		
		JTextArea textAreaMarkMode = new JTextArea();
		textAreaMarkMode.setEditable(false);
		textAreaMarkMode.setBounds(44, 224, 660, 231);
		
		JScrollPane scrollMark = new JScrollPane ();
		scrollMark.setBounds(44, 224, 638, 215);
		scrollMark.setViewportView(textAreaMarkMode);
		pnlMarkMode.add(scrollMark);
		scrollMark.setVisible(true);
		
		
		JButton btnChangeModeMark = new JButton("Change Mode");
		btnChangeModeMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Tut");
			}
		});
		btnChangeModeMark.setBounds(491, 506, 213, 29);
		pnlMarkMode.add(btnChangeModeMark);
		
		JButton btnConfig = new JButton("Marking Settings");
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Config");
			}
		});
		btnConfig.setBounds(491, 466, 213, 29);
		pnlMarkMode.add(btnConfig);
		
		JPanel pnlTutMode = new JPanel();
		contentPane.add(pnlTutMode, "Tut");
		pnlTutMode.setLayout(null);
		
		edtTutMode = new JTextField();
		edtTutMode.setBounds(44, 118, 228, 29);
		pnlTutMode.add(edtTutMode);
		edtTutMode.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tutorial Mode");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(295, 27, 228, 29);
		pnlTutMode.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please enter the filepath for the textfile you wish to submit:");
		lblNewLabel_1.setBounds(44, 86, 435, 29);
		pnlTutMode.add(lblNewLabel_1);
		
		JButton btnSubmitTut = new JButton("Submit");
		
		/*
		 * User (Student) Submits tutorial to be marked:
		 * create answer objects and mark, give feedback
		 * 
		 */
		btnSubmitTut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO
			}
		});
		
		
		btnSubmitTut.setBounds(282, 118, 146, 29);
		pnlTutMode.add(btnSubmitTut);
		
		JLabel lblTutFeedback = new JLabel("Your results are as follows:");
		lblTutFeedback.setBounds(44, 199, 638, 14);
		pnlTutMode.add(lblTutFeedback);
		
		JTextArea textAreaTutMode = new JTextArea();
		textAreaTutMode.setEditable(false);
		textAreaTutMode.setBounds(44, 224, 417, 231);
		//pnlTutMode.add(textAreaTutMode);
		
		JScrollPane scrollTut = new JScrollPane ();
		scrollTut.setBounds(44, 224, 638, 215);
		scrollTut.setViewportView(textAreaTutMode);
		pnlTutMode.add(scrollTut);
		scrollTut.setVisible(true);
		
		
		JButton btnChangeModeTut = new JButton("Change Mode");
		btnChangeModeTut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Mark");
			}
		});
		btnChangeModeTut.setBounds(468, 491, 214, 29);
		pnlTutMode.add(btnChangeModeTut);
		
		JPanel pnlMode = new JPanel();
		contentPane.add(pnlMode, "Mode");
		pnlMode.setLayout(null);
		
		JLabel label = new JLabel("AutoLam: Automatic Marker for Lambda Calculus");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(143, 51, 480, 20);
		pnlMode.add(label);
		
		btnTutorialMode = new JButton("Tutorial Mode");
		btnTutorialMode.setEnabled(false);
		btnTutorialMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Tut");
			}
		});
		btnTutorialMode.setBounds(255, 107, 163, 32);
		pnlMode.add(btnTutorialMode);
		
		btnMarkingMode = new JButton("Marking Mode");
		btnMarkingMode.setEnabled(false);
		btnMarkingMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Mark");
			}
		});
		btnMarkingMode.setBounds(255, 167, 163, 32);
		pnlMode.add(btnMarkingMode);
		
		JButton btnSetup = new JButton("Setup");
		btnSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Config");
			}
		});
		btnSetup.setBounds(255, 229, 163, 32);
		pnlMode.add(btnSetup);
	
		
	 //set the initial page.
		((CardLayout) contentPane.getLayout()).show(contentPane, "Mode");
	}
}
