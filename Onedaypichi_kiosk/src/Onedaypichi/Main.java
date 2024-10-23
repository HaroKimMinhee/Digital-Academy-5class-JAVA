//메인 클래스

package Onedaypichi;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame; // 스윙 패키지 가져오기
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class Main {
	//메인 메서드
	public Main() {
		JFrame f = new JFrame(); //JFrame 생성
		f.setTitle("하루피치"); //프레임 제목
		f.setSize(1024, 730); //프레임 크기
			 
		f.setIconImage(new ImageIcon("img/pizzachicken.png").getImage()); // 프레임 아이콘이미지 설정
			
			
		JPanel g = pw();
		JButton or = cr(f); // 현재 프레임을 파라미터로 전달
		g.setLayout(new BorderLayout()); //BorderLayout은 컴포넌트를 5개의 영역(북, 남, 동, 서, 센터)으로 나누어 배치할 수 있는 레이아웃
		g.add(or, BorderLayout.CENTER); //CENTER은 패널의 중앙에 배치하게 설정 
			
		f.add(g); //패널을 프레임에 추가(필수)
		f.setVisible(true); //프레임 보이기
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
		
	//메인 실행 메서드
	public static void main(String[] args) {
		new Main();

	}
	
	//패널 메소드
	public static JPanel pw() { 
		JPanel p = new JPanel() { // 패널 생성
			Image background = new ImageIcon("img/pc.png").getImage(); //패널 배경 이미지 설정
				
			@Override //오버로딩
			protected void paintComponent(Graphics g) {
				super.paintComponent(g); //// 부모 클래스의 paintComponent 메서드를 호출하여 기본 그리기 작업 수행
				if(background != null) { // 배경 이미지가 로드되었는지 확인
					// 패널의 크기에 맞게 배경 이미지를 그리기
					g.drawImage(background, 0, 0, 1024, 730,this); // 배경 이미지 그리기
					// g.drawImage 메서드 : 이미지 그리기 /background는 배경 이미지 나타내는 변수   
					// (0,0)은 이미지를 그릴 시작 좌표
					// getWidth()와 getHeight()는 패널의 현재 크기를 나타낸다.
					// this는 이미지 그리기 작업에서 필요한 ImageObserver를 지정하는 데 사용됨
						
				} else {
					System.out.println("이미지 로드 실패"); // 배경 이미지가 null일 경우 콘솔에 메시지 출력
				}
			}
		};
			
		return p;
	}
		
		
	//버튼 메소드에 현재 프레임을 파라미터로 추가
	public static JButton cr(JFrame curr) { 
		JButton o1 = new JButton("<html><center>하루피치<br>간편하게 주문하세요~<br><br>손으로 눌러주세요.</center></html>");
			
		o1.setOpaque(false);
		o1.setContentAreaFilled(false);
		o1.setBorderPainted(false);
		o1.setFont(new Font("SansSerif", Font.BOLD, 55)); //글꼴(폰트 이름, 폰트 스타일, 크기)
		o1.setForeground(new Color(0,0,0,255));
			
		o1.addActionListener(e -> {
				
			new Takeaway(); // takeaway 프레임으로 이동
			curr.dispose(); //현재 프레임 닫기
		});
			
		return o1;
	}
	
	
		
}