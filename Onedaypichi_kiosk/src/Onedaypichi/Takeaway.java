//매장포장 선택여부 클래스

package Onedaypichi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Takeaway {
	//메인 메서드
	public Takeaway() {
		JFrame t = new JFrame(); //JFrame 생성
		t.setTitle("하루피치 포장여부"); //프레임 제목
		t.setSize(1024, 730); //프레임 크기
		 
		t.setIconImage(new ImageIcon("img/pizzachicken.png").getImage()); // 프레임 아이콘이미지 설정
		
		JPanel take = new JPanel();
		take.setLayout(new BorderLayout());
		
		take.add(ptop(t), BorderLayout.NORTH);
		take.add(pmint(), BorderLayout.CENTER);
		take.add(pbot(t), BorderLayout.SOUTH);
		
		t.add(take);
		t.setVisible(true); //프레임 보이기
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//메인 실행 메서드
	public static void main(String[] args) {
		new Takeaway();

	}
	
	//매장 식사
	public static JPanel ptop(JFrame curr) { 
		JPanel pt = new JPanel();
		pt.setLayout(new FlowLayout());
		
		//위 패널 - 버튼 추가
		JPanel toppt = new JPanel();
		JButton eattop = new JButton("매장에서 식사하시겠습니까?", new ImageIcon("img/eat.png"));
		eattop.setFont(new Font("SansSerif", Font.BOLD, 30));
		eattop.setHorizontalTextPosition(SwingConstants.LEFT); // 텍스트를 왼쪽으로 정렬
		eattop.setBackground(new Color(253,253,150));
		eattop.setPreferredSize(new Dimension(1024, 250));
		eattop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Menu(true);
				curr.dispose();
			}
		});
		
		pt.add(eattop);
		return pt;
	}
	
	//식사 장소 선택 메서드
	public static JPanel pmint() {
		JPanel pm = new JPanel();
		JLabel ch = new JLabel("식사하실 장소를 선택해주세요.");
		
		ch.setFont(new Font("SansSerif", Font.BOLD, 30));
		ch.setBackground(new Color(230,230,250));
		ch.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트를 가운데 정렬
		pm.add(ch);
		return pm;
	}
	
	//포장 메서드
	public static JPanel pbot(JFrame curr) {
		JPanel pbot = new JPanel();
		JButton eatbot = new JButton("포장하시겠습니까?", new ImageIcon("img/packing.png"));
		eatbot.setHorizontalTextPosition(SwingConstants.LEFT); //텍스트를 왼쪽으로 정렬
		eatbot.setFont(new Font("SansSerif", Font.BOLD, 30));
		eatbot.setBackground(new Color(174,198,207));
		eatbot.setPreferredSize(new Dimension(1024, 250));
		eatbot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Menu(false);
				curr.dispose();
			}
		});
		
		pbot.add(eatbot);
		return pbot;
	}
}
