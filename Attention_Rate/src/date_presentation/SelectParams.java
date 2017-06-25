package date_presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * SelectParams用于设置参数
 */

public class SelectParams implements ActionListener{
	public static void main(String[] args) {
		JFrame frame = new JFrame("Params Setting");
		frame.setSize(350, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SelectParams selectParams=new SelectParams();
		
		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel,selectParams);

		frame.setVisible(true);
	}
	
	static JTextField score_positive_Text;
	static JTextField num_praise_Text;
	static JTextField num_comment_Text;
	static JTextField num_forward_Text;
	static JTextField score_negative_Text;
	static JTextField num_praise_Text_N;
	static JTextField num_comment_Text_N;
	static JTextField num_forward_Text_N;
	static JTextField key_Text;

	private static void placeComponents(JPanel panel, SelectParams selectParams) {
		panel.setLayout(null);

		JLabel keyLabel = new JLabel(" 品牌名称 ");
		keyLabel.setBounds(10, 20, 100, 25);
		panel.add(keyLabel);

		key_Text = new JTextField(20);
		key_Text.setBounds(100, 20, 60, 25);
		panel.add(key_Text);
		
		JLabel gzdLabel = new JLabel("关注度得分 = ");
		gzdLabel.setBounds(10, 50, 100, 25);
		panel.add(gzdLabel);

		JLabel score_positive_Label = new JLabel(" 积极情感得分 * ");
		score_positive_Label.setBounds(60, 80, 150, 25);
		panel.add(score_positive_Label);

		score_positive_Text = new JTextField(20);
		score_positive_Text.setBounds(160, 80, 60, 25);
		panel.add(score_positive_Text);

		JLabel num_praise_Label = new JLabel(" ( 1 + 点赞数量 * ");
		num_praise_Label.setBounds(60, 110, 150, 25);
		panel.add(num_praise_Label);

		num_praise_Text = new JTextField(20);
		num_praise_Text.setBounds(160, 110, 60, 25);
		panel.add(num_praise_Text);

		JLabel num_comment_Label = new JLabel(" * 评论数量 * ");
		num_comment_Label.setBounds(60, 140, 150, 25);
		panel.add(num_comment_Label);

		num_comment_Text = new JTextField(20);
		num_comment_Text.setBounds(160, 140, 60, 25);
		panel.add(num_comment_Text);

		JLabel num_forward_Label = new JLabel(" * 转发数量 * ");
		num_forward_Label.setBounds(60, 170, 150, 25);
		panel.add(num_forward_Label);

		num_forward_Text = new JTextField(20);
		num_forward_Text.setBounds(160, 170, 60, 25);
		panel.add(num_forward_Text);

		JLabel num_forward_Label_1 = new JLabel(" ) ");
		num_forward_Label_1.setBounds(225, 170, 150, 25);
		panel.add(num_forward_Label_1);

		JLabel score_negative_Label = new JLabel(" + 消极情感得分 * ");
		score_negative_Label.setBounds(60, 200, 150, 25);
		panel.add(score_negative_Label);

		score_negative_Text = new JTextField(20);
		score_negative_Text.setBounds(160, 200, 60, 25);
		panel.add(score_negative_Text);

		JLabel num_praise_Label_N = new JLabel(" ( 1 + 点赞数量 * ");
		num_praise_Label_N.setBounds(60, 230, 150, 25);
		panel.add(num_praise_Label_N);

		num_praise_Text_N = new JTextField(20);
		num_praise_Text_N.setBounds(160, 230, 60, 25);
		panel.add(num_praise_Text_N);

		JLabel num_comment_Label_N = new JLabel(" * 评论数量 * ");
		num_comment_Label_N.setBounds(60, 260, 150, 25);
		panel.add(num_comment_Label_N);

		num_comment_Text_N = new JTextField(20);
		num_comment_Text_N.setBounds(160, 260, 60, 25);
		panel.add(num_comment_Text_N);

		JLabel num_forward_Label_N = new JLabel(" * 转发数量 * ");
		num_forward_Label_N.setBounds(60, 290, 150, 25);
		panel.add(num_forward_Label_N);

		num_forward_Text_N = new JTextField(20);
		num_forward_Text_N.setBounds(160, 290, 60, 25);
		panel.add(num_forward_Text_N);

		JLabel num_forward_Label_N_1 = new JLabel(" ) ");
		num_forward_Label_N_1.setBounds(225, 290, 150, 25);
		panel.add(num_forward_Label_N_1);

		JButton loginButton = new JButton("show");
		loginButton.setBounds(230, 350, 80, 25);
		loginButton.addActionListener(selectParams); 
		panel.add(loginButton);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key=key_Text.getText();
		double p_score_positive=Double.parseDouble(score_positive_Text.getText());
		double p_score_negative=Double.parseDouble(score_negative_Text.getText());
		double p_num_praise_positive=Double.parseDouble(num_praise_Text.getText());
		double p_num_comment_positive=Double.parseDouble(num_comment_Text.getText());
		double p_num_forward_positive=Double.parseDouble(num_forward_Text.getText());
		double p_num_praise_negative=Double.parseDouble(num_praise_Text_N.getText());
		double p_num_comment_negative=Double.parseDouble(num_comment_Text_N.getText());
		double p_num_forward_negative=Double.parseDouble(num_forward_Text_N.getText());
		
		Presentation chart=new Presentation(key,p_score_positive,p_score_negative,p_num_praise_positive,p_num_comment_positive,p_num_forward_positive,p_num_praise_negative,p_num_comment_negative,p_num_forward_negative);
        chart.pack();//以合适的大小显示
        chart.setVisible(true);
	}

}
