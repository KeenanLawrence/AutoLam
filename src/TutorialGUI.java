import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;

public class TutorialGUI extends JFrame{
	
	private static final long serialVersionUID = -8483105505159387371L;
	private static final int DEFAULT_MARK = 3;
	private JPanel contentPane;
	private JTextField edtTemplateLocation;
	private JTextField edtMarkMode;
	private JTextField edtQFile;
	private static TutorialGUI frame;
	private JButton btnMarkingMode;
	private JButton btnTutorialMode;
	private JTextArea txtQuestions;
	private JTextArea textAreaMarkMode;
	private JTextArea textAreaTutMode;
	private String templateLocation;
	private NewTut tut;
	private boolean questionsMade = false; //flag to indicate whether the question objects have successfully been created.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new TutorialGUI();
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
		//components
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
		
		JLabel lblMarkingConfiguration = new JLabel("Marking Configuration");
		lblMarkingConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMarkingConfiguration.setBounds(279, 29, 240, 29);
		pnlConfig.add(lblMarkingConfiguration);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the filepath for the question text file:");
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPleaseEnterThe.setBounds(44, 86, 428, 29);
		pnlConfig.add(lblPleaseEnterThe);

		/*
		 * back button for configuration page
		 */
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
				
					//before saving, check if the questions have been created. If not, display an error
					//else, take the user back to the home screen
					if (questionsMade == false)
					{
						//error message
						JOptionPane.showMessageDialog(null,
						    "No questions have been created.\nPlease provide a text file containing valid lambda expressions to create the questions.",
						    "Unable to save",
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
		
		edtQFile = new JTextField();
		edtQFile.setColumns(10);
		edtQFile.setBounds(44, 126, 228, 29);
		pnlConfig.add(edtQFile);
		
		JButton btnMakeQuestions = new JButton("Create Questions");
		
		/*
		 * Button to make questions from a text file provided by tutor
		 * sets up NewTut class and populates it with question objects
		 * 
		 */
		btnMakeQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtQuestions.setText("");
				tut = new NewTut();
				BufferedReader br = null;

				try {
					String line;
					int count = 1;
					br = new BufferedReader(new FileReader(edtQFile.getText()));
					
					while ((line = br.readLine()) != null) {
						
						//Create the Question objects: format should be: expression [mark]
						questionsMade = true;
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

						Expression e = new Expression(expression);
						Question q = new Question(e,mark);
						if(!q.check()){
							JOptionPane.showMessageDialog(null, "ERROR: " + expression + " is not a valid lambda expression\nPlease reload a file with valid questions only");
							txtQuestions.setText("");
							questionsMade = false; //questions have no successfullly been made
							break;
						}
						tut.addQuestion(q);
						
						txtQuestions.append(count + ". " + expression + "\t" + mark + "\n");
						count++;

						txtQuestions.append("(" + count + ") " + expression + "\t" + mark + "\n");

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
		scroll.setBounds(44, 233, 663, 206);
		scroll.setViewportView(txtQuestions);
		pnlConfig.add(scroll);
		scroll.setVisible(true);
		
		JLabel lblConfig = new JLabel("The questions and their mark allocations are displayed below:");
		lblConfig.setBounds(44, 195, 638, 14);
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
		 * User (Tutor) submits a file for marking:
		 * create answer objects and mark,
		 * output feedback
		 */
		btnSubmitMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//user enters the file path
				String fileName = edtMarkMode.getText();
				//fill the answer array in the NewTut object
				tut.readAns(fileName);
				
				//clear the textArea
				textAreaMarkMode.setText("");
				
				//TODO MARKING %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				//mark the answers
				System.out.println("acount is: " + tut.acount);
				for (int i=0;i<tut.acount;i++){
					System.out.println("Hacked: I'm in");
					tut.answers[i].markAnswer();
					//show the question number
					int qnum = i+1;
					textAreaMarkMode.append("Question " + qnum + "\n");
					//show the feedback
					textAreaMarkMode.append(tut.answers[i].feedbackToString());
					//show the mark
					float perc = tut.answers[i].percentage();
					tut.addMark(perc);
					String percString = Float.toString(perc);
					textAreaMarkMode.append("Mark:" + percString + "\n");
					textAreaMarkMode.append("\n");
					
				}
				String finalMarkString = Float.toString(tut.finalMark());
				textAreaMarkMode.append("Final mark: " + finalMarkString);
			}//end of actionPerformed
		});
		
		btnSubmitMark.setBounds(295, 118, 166, 29);
		pnlMarkMode.add(btnSubmitMark);
		
		JLabel lblMarkFeedback = new JLabel("The student's results are as follows:");
		lblMarkFeedback.setBounds(44, 199, 417, 14);
		pnlMarkMode.add(lblMarkFeedback);
		
		textAreaMarkMode = new JTextArea();
		textAreaMarkMode.setEditable(false);
		textAreaMarkMode.setBounds(44, 224, 660, 231);
		
		JScrollPane scrollMark = new JScrollPane ();
		scrollMark.setBounds(44, 224, 638, 215);
		scrollMark.setViewportView(textAreaMarkMode);
		pnlMarkMode.add(scrollMark);
		scrollMark.setVisible(true);
	
		/*
		 * button to change mode from Mark Mode to Tutorial Mode
		 * 
		 */
		JButton btnChangeModeMark = new JButton("Change Mode");
		btnChangeModeMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Tut");
			}
		});
		btnChangeModeMark.setBounds(491, 506, 213, 29);
		pnlMarkMode.add(btnChangeModeMark);
		
		/*
		 * button to navigate to marking configuration page
		 */
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
		
		edtTemplateLocation = new JTextField();
		edtTemplateLocation.setBounds(44, 118, 228, 29);
		pnlTutMode.add(edtTemplateLocation);
		edtTemplateLocation.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tutorial Mode");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(295, 27, 228, 29);
		pnlTutMode.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please enter a valid location in which to save your submission:");
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
				tut.acount=0;
				//read answers into the NewTut object from template file 
				if (tut.readAns(templateLocation)){	
				
					//TODO marking %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					
					//clear the textArea
					textAreaTutMode.setText("");
					System.out.println("Chillin wit my boy acount, he at "+ tut.acount+ " right now");
					//mark the answers
					for (int i=0;i<tut.acount;i++){
						tut.answers[i].markAnswer();
						//show the question number
						int qnum = i+1;
						textAreaTutMode.append("Question " + qnum + "\n");
						float perc = tut.answers[i].percentage();
						tut.addMark(perc);
						//show the mark
						String percString = Float.toString(perc);
						textAreaTutMode.append("Mark:" + percString + "\n");
						//show the feedback
						textAreaTutMode.append(tut.answers[i].feedbackToString());
						textAreaTutMode.append("\n");	
					}
					String finalMarkString = Float.toString(tut.finalMark());
					textAreaTutMode.append("Final mark: " + finalMarkString);
				}
				else
				{
					//error message
					JOptionPane.showMessageDialog(null,
					    "Please create a template and fill it in before submitting.",
					    "Error",
					    JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		
		btnSubmitTut.setBounds(283, 159, 171, 29);
		pnlTutMode.add(btnSubmitTut);
		
		JLabel lblTutFeedback = new JLabel("Your results are as follows:");
		lblTutFeedback.setBounds(44, 199, 638, 14);
		pnlTutMode.add(lblTutFeedback);
		
		textAreaTutMode = new JTextArea();
		textAreaTutMode.setEditable(false);
		textAreaTutMode.setBounds(44, 224, 417, 231);
		//pnlTutMode.add(textAreaTutMode);
		
		JScrollPane scrollTut = new JScrollPane ();
		scrollTut.setBounds(44, 224, 638, 215);
		scrollTut.setViewportView(textAreaTutMode);
		pnlTutMode.add(scrollTut);
		scrollTut.setVisible(true);
		
		/*
		 * button to change mode from Tutorial Mode to Marking Mode
		 */
		
		JButton btnChangeModeTut = new JButton("Change Mode");
		btnChangeModeTut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) contentPane.getLayout()).show(contentPane, "Mark");
			}
		});
		btnChangeModeTut.setBounds(468, 491, 214, 29);
		pnlTutMode.add(btnChangeModeTut);
		
		/*
		 * Creates a template for a file of tutorial answers to be submitted 
		 * (used in the case of a student writing a tutorial)
		 * 
		 */
		JButton btnCreateTemplate = new JButton("Create Template");
		btnCreateTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				//this will be used again later for marking when the student submits


				templateLocation = edtTemplateLocation.getText()+"\\template.txt";
				
				File template = new File(templateLocation);
				String text = "";
				BufferedWriter output = null;
		        try {
		            output = new BufferedWriter(new FileWriter(template));
		            //loop through tutorial object and write question number, question expression
		           
		            for (int i=0;i<tut.qcount;i++){
		            	int qnum = i+1;
		            	text = qnum + ". " + tut.questions[i].body + "\n";
		            	output.write(text);
		            	//blank line in between each question
		            	output.write("\n");
		            }
		            (Runtime.getRuntime()).exec ("gedit " + templateLocation);
		            
		        } catch ( IOException ex ) {
		        	//error message
					JOptionPane.showMessageDialog(null,
					    "Please enter a valid text file location.",
					    "Error",
					    JOptionPane.WARNING_MESSAGE);
		        } finally {
		            if ( output != null )
						try {
							output.close();
						} catch (IOException e1) {
							//error message
							JOptionPane.showMessageDialog(null,
							    "An error occurred in the creation on the template. Please try again.",
							    "Error",
							    JOptionPane.WARNING_MESSAGE);
						}
		        }
		        
		        //open the text file with the default editor
		       /* try {
		        	(Runtime.getRuntime()).exec ("gedit " + templateLocation);
					//java.awt.Desktop.getDesktop().edit(template);
				} catch (IOException e1) {
					//error message
					JOptionPane.showMessageDialog(null,
					    "Could not open the text file.",
					    "Error",
					    JOptionPane.WARNING_MESSAGE);
				}*/
			}
		});
		btnCreateTemplate.setBounds(282, 118, 172, 29);
		pnlTutMode.add(btnCreateTemplate);
		
		JPanel pnlMode = new JPanel();
		contentPane.add(pnlMode, "Mode");
		pnlMode.setLayout(null);
		
		JLabel label = new JLabel("AutoLam: Automatic Marker for Lambda Calculus");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(143, 51, 480, 20);
		pnlMode.add(label);
		
		btnTutorialMode = new JButton("Tutorial Mode");
		btnTutorialMode.setEnabled(false);
		
		/*
		 * button to navigate to Tutorial Mode
		 */
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
		
		/*
		 * button to navigate to marking configuration from the home screen.
		 * a tutor must do a setup before any marking can be done: 
		 * creating questions and saving enables the other two buttons on the homescreen 
		 */
		
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
