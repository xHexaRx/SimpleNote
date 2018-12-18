package simplenote;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SimpleNoteGui implements ActionListener {
	
	private JTextArea textArea;
	private JFileChooser fileChooser;
	
	public static void main(String[] args) {
		SimpleNoteGui simpleNote = new SimpleNoteGui();
		simpleNote.go();
	}
	
	public void go() {
		JFrame frame = new JFrame("SimpleNote");
		textArea = new JTextArea();
		fileChooser = new JFileChooser();
		JPanel buttonsPanel = new JPanel();
		JButton openButton = new JButton("Open");
		JButton saveButton = new JButton("Save");
		JButton clearButton = new JButton("Clear");
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(openButton);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(clearButton);
		
		openButton.addActionListener(this);
		openButton.setActionCommand("open");
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");
		clearButton.addActionListener(this);
		clearButton.setActionCommand("clear");
		
		frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
		frame.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);
		
		textArea.requestFocus();
		
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if(action.getActionCommand().contains("open")) {
			int openResult = fileChooser.showOpenDialog(textArea);
			if(openResult==JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				open(file);
			}
		}
		else if(action.getActionCommand().contains("save")) {
			int openResult = fileChooser.showOpenDialog(textArea);
			if(openResult==JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				save(file);
			}
		}
		else if(action.getActionCommand().contains("clear")) {
			textArea.setText("");
		}
		
	}

	private void save(File file) {
		try (PrintWriter outputStream = new PrintWriter(new FileWriter(file+".txt"))){
			String tmp = textArea.getText();
			outputStream.write(tmp);
		} catch (Exception ex) {
			textArea.setText(ex.toString());
		}
		
	}

	private void open(File file) {
		textArea.setText("");
		try (BufferedReader inputStream = new BufferedReader(new FileReader(file))){
			String tmp;
			while((tmp=inputStream.readLine()) != null) {
				textArea.append(tmp);
				textArea.append(System.lineSeparator());
			}
		} catch(Exception ex) {
			textArea.setText(ex.toString());
		}
	}
	
}
