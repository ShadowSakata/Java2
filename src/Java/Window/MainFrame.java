package Java.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	
	private static final int WIDTH = 650;
	private static final int HEIGHT = 320;
	
	private JTextField textFieldX;
	private JTextField textFieldY;
	private JTextField textFieldZ;
	
	private JTextField textFieldResult;
	
	private ButtonGroup radioButtons = new ButtonGroup();
	private ButtonGroup memButtons = new ButtonGroup();
	
	private Box hboxFormulaType = Box.createHorizontalBox();
	private Box hboxMemType = Box.createHorizontalBox();
	
	BufferedImage formulaPic1;
	BufferedImage formulaPic2;
	
	JLabel picLabel = new JLabel();
	
	private int formulaId = 1;
	
	public double calculate1(Double x, Double y, Double z) {
		return Math.sin(Math.log(y) + Math.sin(3.14 * y * y) * 
				Math.sqrt(Math.sqrt(x*x+Math.sin(z) + Math.exp(Math.cos(z)))));
	}
	
	public double calculate2(Double x, Double y, Double z) {
		return Math.pow(Math.cos(Math.exp(x)) + Math.log((1 + y) * (1 + y)) +
				Math.sqrt(Math.exp(Math.cos(x)) + Math.sin(3.14 * z) * Math.sin(3.14 * z)) +
				Math.sqrt(1/x) + Math.cos(y * y), Math.sin(z));
	}
	
	private int memId = 1;
	
	private double[] mem = {0, 0, 0};
	
	public void addRadioButton(String buttonName, final int formulaId) {
		JRadioButton button = new JRadioButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				MainFrame.this.formulaId = formulaId;
				switch(formulaId) {
				case 1:
					MainFrame.this.picLabel.setIcon(new ImageIcon(MainFrame.this.formulaPic1));
					break;
				case 2:
					MainFrame.this.picLabel.setIcon(new ImageIcon(MainFrame.this.formulaPic2));
					break;
				}
			}
		});

		radioButtons.add(button);
		hboxFormulaType.add(button);
		}
	
	public void addMemButton(String buttonName, final int memId) {
		JRadioButton button = new JRadioButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				MainFrame.this.memId = memId;
			}
		});
		memButtons.add(button);
		hboxMemType.add(button);
	}
	
	
	
	public MainFrame() throws IOException {
		super("Вычисление формулы");
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		setLocation((kit.getScreenSize().width - WIDTH)/2,(kit.getScreenSize().height - HEIGHT)/2);
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		 
		Box labelBox = Box.createHorizontalBox();
		labelBox.add(Box.createHorizontalGlue());
		labelBox.add(picLabel);
		labelBox.add(Box.createHorizontalGlue());
		formulaPic1 = 
				ImageIO.read(new File("C:/Users/shift/eclipse-workspace/Java2/src/formula1.bmp"));
		formulaPic2 = 
				ImageIO.read(new File("C:/Users/shift/eclipse-workspace/Java2/src/formula2.bmp"));
		
		hboxFormulaType.add(Box.createHorizontalGlue());
		addRadioButton("Формула 1", 1);
		addRadioButton("Формула 2", 2);
		radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);
		MainFrame.this.picLabel.setIcon(new ImageIcon(MainFrame.this.formulaPic1));
		hboxFormulaType.add(Box.createHorizontalGlue());
		hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		
		JLabel labelForX = new JLabel("X: ");
		textFieldX = new JTextField("0", 10);
		textFieldX.setMaximumSize(textFieldX.getPreferredSize());
		
		JLabel labelForY = new JLabel("Y: ");
		textFieldY = new JTextField("0", 10);
		textFieldY.setMaximumSize(textFieldY.getPreferredSize());
		
		JLabel labelForZ = new JLabel("Z: ");
		textFieldZ = new JTextField("0", 10);
		textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
		
		Box hboxVariables = Box.createHorizontalBox();
		
		hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		hboxVariables.add(Box.createHorizontalGlue());
		hboxVariables.add(labelForX);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldX);
		hboxVariables.add(Box.createHorizontalStrut(100));
		hboxVariables.add(labelForY);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldY);
		hboxVariables.add(Box.createHorizontalStrut(100));
		hboxVariables.add(labelForZ);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldZ);
		hboxVariables.add(Box.createHorizontalGlue());
		
		JLabel labelForResult = new JLabel("Результат: ");
		
		textFieldResult = new JTextField("0", 12);
		textFieldResult.setMaximumSize(textFieldResult.getPreferredSize());
		
		Box hboxResult = Box.createHorizontalBox();
		hboxResult.add(Box.createHorizontalGlue());
		hboxResult.add(labelForResult);
		hboxResult.add(Box.createHorizontalStrut(10));
		hboxResult.add(textFieldResult);
		hboxResult.add(Box.createHorizontalGlue());
		hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JButton buttonCalc = new JButton("Вычислить");
		buttonCalc.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ev) {
				try {
					Double x = Double.parseDouble(textFieldX.getText());
					Double y = Double.parseDouble(textFieldY.getText());
					Double z = Double.parseDouble(textFieldZ.getText());
					Double result;
					if(formulaId == 1)
						result = calculate1(x, y, z);
					else
						result = calculate2(x, y, z);
					textFieldResult.setText(result.toString());
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа",
							"Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		JButton buttonReset = new JButton("Очистить поля");
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				textFieldX.setText("0");
				textFieldY.setText("0");
				textFieldZ.setText("0");
				textFieldResult.setText("0");
			}	
		});
		
		Box hboxButtons = Box.createHorizontalBox();
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.add(buttonCalc);
		hboxButtons.add(Box.createHorizontalStrut(30));
		hboxButtons.add(buttonReset);
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		
		hboxMemType.add(Box.createHorizontalGlue());
		addMemButton("Переменная 1", 1);
		addMemButton("Переменная 2", 2);
		addMemButton("Переменная 3", 3);
		memButtons.setSelected(memButtons.getElements().nextElement().getModel(), true);
		hboxMemType.add(Box.createHorizontalGlue());
		hboxMemType.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		Box memButtons = Box.createHorizontalBox();
		memButtons.add(Box.createHorizontalGlue());
		JLabel memLabel1 = new JLabel("Переменная 1 : ");
		JTextField textForM1 = new JTextField("0",10);
		textForM1.setMaximumSize(textForM1.getPreferredSize());
		textForM1.setEditable(false);
		JLabel memLabel2 = new JLabel("Переменная 2 : ");
		JTextField textForM2 = new JTextField("0",10);
		textForM2.setMaximumSize(textForM2.getPreferredSize());
		textForM2.setEditable(false);
		JLabel memLabel3 = new JLabel("Переменная 3 : ");
		JTextField textForM3 = new JTextField("0",10);
		textForM3.setMaximumSize(textForM3.getPreferredSize());
		textForM3.setEditable(false);
		
		JButton MP = new JButton("M+");
		MP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				MainFrame.this.mem[MainFrame.this.memId - 1] += 
						Double.parseDouble(textFieldResult.getText());
				Double result = MainFrame.this.mem[MainFrame.this.memId - 1];
				textFieldResult.setText(result.toString());
				switch(MainFrame.this.memId) {
				case 1:
					textForM1.setText(result.toString());
					break;
				case 2:
					textForM2.setText(result.toString());
					break;
				case 3:
					textForM3.setText(result.toString());
					break;
				}
			}
		});
		JButton MC = new JButton("MC");
		MC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				MainFrame.this.mem[MainFrame.this.memId - 1] = 0;
				textFieldResult.setText("0");
				switch(MainFrame.this.memId) {
				case 1:
					textForM1.setText("0");
					break;
				case 2:
					textForM2.setText("0");
					break;
				case 3:
					textForM3.setText("0");
					break;
				}
			}
		});
		memButtons.add(MP);
		memButtons.add(Box.createHorizontalStrut(5));
		memButtons.add(MC);
		memButtons.add(Box.createHorizontalGlue());
		memButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		Box contentBox = Box.createVerticalBox();
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(labelBox);
		contentBox.add(hboxFormulaType);
		contentBox.add(hboxVariables);
		contentBox.add(hboxResult);
		contentBox.add(hboxButtons);
		contentBox.add(hboxMemType);
		contentBox.add(memButtons);
		contentBox.add(Box.createVerticalGlue());
		JPanel mainPanel = new JPanel();
		JPanel memPanel = new JPanel();
		
		Box memLabelBox = Box.createHorizontalBox();
		
		memLabelBox.add(memLabel1);
		memLabelBox.add(textForM1);
		memLabelBox.add(memLabel2);
		memLabelBox.add(textForM2);
		memLabelBox.add(memLabel3);
		memLabelBox.add(textForM3);
		memLabelBox.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		
		memPanel.add(memLabelBox);
		
		mainPanel.add(contentBox);
		getContentPane().add(contentBox, BorderLayout.CENTER);
		getContentPane().add(memPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
		
		public static void main(String[] args) throws IOException {
			new MainFrame();
		}
		
		
		
	}

