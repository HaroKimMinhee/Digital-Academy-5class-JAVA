// 메뉴 클래스

package Onedaypichi;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Menu {
	private Cart payment;
	
    private boolean isDineIn;

    public Menu(boolean isDineIn) {
        this.isDineIn = isDineIn;
        Menu();
    }
	
	private static JButton lastButton = null;//마지막으로 눌린 버튼을 저장할 변수
	
	private JPanel menuPanel; //메뉴 패널
	
	public void Menu() {
		JFrame m = new JFrame(); //JFrame 생성
		m.setTitle("하루피치 메뉴"); //프레임 제목
		m.setSize(1024, 730); //프레임 크기
		 
		m.setIconImage(new ImageIcon("img/pizzachicken.png").getImage()); // 프레임 아이콘이미지 설정
		
		JPanel menu = new JPanel();
		menu.setLayout(new BorderLayout());
		
		menu.add(Home(m), BorderLayout.NORTH);
		
		menuPanel = menug();
		menu.add(menuPanel, BorderLayout.CENTER);
		
		payment = new Cart(isDineIn);
		menu.add(payment.crea(m), BorderLayout.SOUTH);
		
		m.add(menu);
		m.setVisible(true); //프레임 보이기
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}

	//메인 메서드
	public static void main(String[] args) {
		new Menu(true);

	}
	
	//상단 패널
	//(Home 버튼 왼쪽 정렬)
	public static JPanel Home(JFrame ho) {
		JPanel toppan = new JPanel();
		toppan.setLayout(new BorderLayout());//왼쪽 정렬 배치
		toppan.setBackground(new Color(255,253,208));
		
		JButton home = new JButton(new ImageIcon("img/home.jpg"));
		home.setPreferredSize(new Dimension(50, 50));
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Main();
				ho.dispose();
			}
		});
		
		toppan.add(home, BorderLayout.WEST);
		
		
		//홈 버튼과 레이블 사이의 간격을 위한 패널
		JPanel sp = new JPanel(); 
		sp.setPreferredSize(new Dimension(350, 0)); //간격 조절(너비만 조절)
		
		JLabel m = new JLabel("하루피치 메뉴");
		
		m.setFont(new Font("SansSerif", Font.BOLD, 30));
		m.setHorizontalAlignment(SwingConstants.CENTER);
		
	    // 관리자 모드 버튼 추가
	    JButton adminButton = new JButton(new ImageIcon("img/seoljeong.png")); //카드
	    adminButton.addActionListener(e -> new Admin()); // 관리자 모드 창 호출
	    adminButton.setPreferredSize(new Dimension(50, 50));
	    toppan.add(adminButton, BorderLayout.EAST); // 관리자 모드 버튼을 오른쪽에 추가
		
		toppan.add(home, BorderLayout.WEST); //왼쪽 패널 추가
		toppan.add(sp, BorderLayout.CENTER); //간격 패널 추가
		toppan.add(m, BorderLayout.CENTER); // 중앙 레이블 추가

		
		return toppan;
	}
	
	//가운데 패널
	// 카테고리 버튼 패널
    public JPanel menug() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        String[] menuLabels = {"피자", "치킨", "피&치", "디저트", "음료"};
        for (String label : menuLabels) {
            JButton menuButton = new JButton(label);
            menuButton.addActionListener(new MenuButtonListener(menuButton));
            buttonPanel.add(menuButton);
        }

        // 버튼 패널을 메뉴 패널의 상단에 추가
        menuPanel.add(buttonPanel, BorderLayout.NORTH);

        // 메뉴 패널의 중앙 영역도 비워 두기
        JPanel centralPanel = new JPanel(); // 중앙 영역 패널
        menuPanel.add(centralPanel, BorderLayout.CENTER); // 중앙 패널 추가

        return menuPanel;
    }

    // 버튼 클릭 리스너
    private class MenuButtonListener implements ActionListener {
        private JButton clickedButton;

        public MenuButtonListener(JButton button) {
            this.clickedButton = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 마지막으로 눌린 버튼의 색을 원래 색으로 돌린다
            if (lastButton != null) {
                lastButton.setBackground(null);
                lastButton.setForeground(Color.black);
            }

            // 현재 버튼의 색을 변경
            clickedButton.setBackground(Color.GRAY);
            clickedButton.setForeground(Color.white);
            lastButton = clickedButton; // 현재 버튼을 마지막 버튼으로 설정

            // 메뉴 초기화
            JPanel centralPanel = (JPanel) menuPanel.getComponent(1); // 중앙 패널 가져오기
            centralPanel.removeAll(); // 기존 메뉴 내용 제거

            // 선택한 카테고리에 따라 메뉴 추가
            if (clickedButton.getText().equals("피자")) {
                centralPanel.add(showPizzaMenu()); // 피자 메뉴 추가
            } else if (clickedButton.getText().equals("치킨")) {
            	centralPanel.add(showchickenMenu());
            } else if (clickedButton.getText().equals("피&치")) {
            	centralPanel.add(showpichMenu());
            } else if (clickedButton.getText().equals("디저트")) {
            	centralPanel.add(showdessertMenu());
            } else if (clickedButton.getText().equals("음료")) {
            	centralPanel.add(showdrinkMenu());
            }
            // 다른 메뉴 항목에 대해서도 필요 시 추가 가능
            
            centralPanel.revalidate(); // 패널 갱신
            centralPanel.repaint(); // 재그리기
        }
    }
    
    // 피자 메뉴 표시
    private JPanel showPizzaMenu() {
        JPanel pizzaButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        String[] pizzamanus = {
            "콤비네이션 피자", "포테이토 피자", "베이컨체다치즈 피자", "불고기 피자", "야채 피자", 
            "페페로니 피자", "치즈 피자", "옥수수 피자", "하와이안 피자", "고구마 피자"
        };

        String[] imagePaths = {
            "img/pizza/1 Combination Pizza.png",
            "img/pizza/2 Potato Pizza.png",
            "img/pizza/3 Bacon Cheddar Cheese Pizza.png",
            "img/pizza/4 Bulgogi Pizza.png",
            "img/pizza/5 Vegetable Pizza.png",
            "img/pizza/6 Pepperoni Pizza.png",
            "img/pizza/7 Cheese Pizza.png",
            "img/pizza/8 Corn Pizza.png",
            "img/pizza/9 Hawaiian Pizza.png",
            "img/pizza/10 Sweet Potato Pizza.png"
        };
        
        int[] pizzaprice = {
        		19000, 20000, 20000, 18000, 18000, 19000, 18000, 20000, 21000, 20000
        };

        for (int i = 0; i < pizzamanus.length; i++) {
            String pizzaName = pizzamanus[i];
            String imagePath = imagePaths[i];
            int price = pizzaprice[i];
            
            JButton pizzaButton = new JButton(new ImageIcon(imagePath));
            
            // 버튼 클릭 이벤트를 람다식으로 표현
            pizzaButton.addActionListener(e -> showPizzaDetail(pizzaName, price, imagePath));
            
            // 버튼 크기 설정
            pizzaButton.setPreferredSize(new Dimension(180, 180)); // 원하는 크기로 조정
            pizzaButtonPanel.add(pizzaButton);
        }
        
        return pizzaButtonPanel;
    }
    
    
    //피자 추가 옵션 메소드
    private void showPizzaDetail(String pizzaName, int pizzaPrice, String imagePath) {
        // 재고 수량 가져오기
        int stock = getStock(pizzaName);
        System.out.println(stock);
        if (stock <= 0) {
            JOptionPane.showMessageDialog(null, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
            return; // 품절이면 메소드 종료
        }
    	
    	JDialog dialog = new JDialog();
        dialog.setTitle("피자 상세");
        dialog.setSize(500, 500);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // BoxLayout 사용

        // 피자 정보 패널 (상단)
        JPanel pizzaInfoPanel = new JPanel();
        pizzaInfoPanel.setLayout(new BoxLayout(pizzaInfoPanel, BoxLayout.Y_AXIS));

        // 이미지 표시 패널
        JLabel imageLabel;
        if (stock <= 0) {
            // 품절 상태
            imageLabel = new JLabel(new ImageIcon("img/out_of_stock.png")); // 품절 이미지
            JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
        } else {
            // 정상 상태
            imageLabel = new JLabel(new ImageIcon(imagePath)); // 정상 피자 이미지
        }
        
        // 이미지 경로 설정
        imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 중앙 정렬

        // 패널에 이미지와 이름 추가
        pizzaInfoPanel.add(imageLabel);
        pizzaInfoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 여백 추가

        
        // 수량 패널 (중앙)
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬
        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        JLabel quantityLabel = new JLabel("0");

        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) quantityLabel.setText(String.valueOf(quantity - 1));
        });

        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity < stock) { // 재고 수량과 비교
                quantityLabel.setText(String.valueOf(quantity + 1));
            } else {
                JOptionPane.showMessageDialog(dialog, "재고가 부족합니다.", "재고 부족", JOptionPane.WARNING_MESSAGE);
            }
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);

        // 추가 옵션 패널 (가운데)
        JPanel optionPanel = new JPanel();
        JRadioButton colaButton = new JRadioButton("콜라 (100원)");
        JRadioButton ciderButton = new JRadioButton("사이다 (0원)");
        JRadioButton fantaButton = new JRadioButton("환타 (300원)");
        JRadioButton milkButton = new JRadioButton("우유 (500원)");

        ButtonGroup group = new ButtonGroup();
        group.add(colaButton);
        group.add(ciderButton);
        group.add(fantaButton);
        group.add(milkButton);

        optionPanel.add(colaButton);
        optionPanel.add(ciderButton);
        optionPanel.add(fantaButton);
        optionPanel.add(milkButton);

        // 확인 및 취소 버튼 패널 (하단)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        confirmButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > stock) {
                JOptionPane.showMessageDialog(dialog, "선택한 수량이 재고를 초과합니다.", "재고 초과", JOptionPane.WARNING_MESSAGE);
                return; // 재고 초과 시 메소드 종료
            }
            
            int totalPrice = pizzaPrice * quantity;
            
            // 재고 감소
            updateStock4(pizzaName, stock - quantity);
            
            // 선택된 옵션에 따라 가격 추가
            String options = "선택 안함"; // 기본 옵션
            if (colaButton.isSelected()) {
                options = "콜라 (0원)";
            } else if (ciderButton.isSelected()) {
                options = "사이다 (0원)";
            } else if (fantaButton.isSelected()) {
                options = "환타 (300원)";
                totalPrice += 300;
            } else if (milkButton.isSelected()) {
                options = "우유 (500원)";
                totalPrice += 500;
            }
            payment.updatePayment(pizzaName, quantity, totalPrice, options); // 상품 이름, 수량, 가격, 추가 옵션 전달

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // 패널 추가
        dialog.add(pizzaInfoPanel);  // 피자 정보 패널 추가
        dialog.add(quantityPanel);    // 수량 패널 추가
        dialog.add(optionPanel);       // 음료 옵션 패널 추가
        dialog.add(buttonPanel);       // 버튼 패널 추가
        dialog.setVisible(true);
    }
    
    // 재고 업데이트 메소드
    private void updateStock(String pizzaName, int newStock) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("UPDATE Menu SET stock = ? WHERE name = ?")) {
            statement.setInt(1, newStock);
            statement.setString(2, pizzaName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // 재고 수량을 데이터베이스에서 가져오는 메소드
    private int getStock(String pizzaName) {
        int stock = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("SELECT stock FROM Menu WHERE name = ?")) {
            statement.setString(1, pizzaName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }
    

    
    // 치킨 메뉴 표시
    private JPanel showchickenMenu() {
        JPanel chickenButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        String[] chickenmanus = {
            "양념 치킨", "후라이드 치킨", "소이갈릭 치킨", "오리엔탈파닭", "양념반후라이드반",
            "치즈스노윙 치킨", "매콤치즈스노윙 치킨", "찜닭", "마늘치킨", "레드마블 치킨"
        };

        String[] imagePaths = {
            "img/chicken/1 chicken.png",
            "img/chicken/2 chicken.png",
            "img/chicken/3 chicken.png",
            "img/chicken/4 chicken.png",
            "img/chicken/5 chicken.png",
            "img/chicken/6 chicken.png",
            "img/chicken/7 chicken.png",
            "img/chicken/8 chicken.png",
            "img/chicken/9 chicken.png",
            "img/chicken/10 chicken.png"
        };
        
        int[] chickenprice = {
        		18000, 18000, 20000, 23000, 19000, 20000, 21000, 21000, 20000, 23000
        };

        for (int i = 0; i < chickenmanus.length; i++) {
            String chickenName = chickenmanus[i];
            String imagePath = imagePaths[i];
            int price = chickenprice[i];
            
            JButton chickenButton = new JButton(new ImageIcon(imagePath));

            chickenButton.addActionListener(e ->
                showchickenDetail(chickenName, price, imagePath));
            
            // 버튼 크기 설정
            chickenButton.setPreferredSize(new Dimension(180, 180)); // 원하는 크기로 조정
            chickenButtonPanel.add(chickenButton);
        }
        
        return chickenButtonPanel;
    }
    
    //치킨 추가 옵션 메소드
    private void showchickenDetail(String chickenName, int chickenprice, String imagePath) {
        // 재고 수량 가져오기
        int stock = getStock(chickenName);
    	
    	JDialog dialog = new JDialog();
        dialog.setTitle("치킨 상세");
        dialog.setSize(500, 500);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // BoxLayout 사용

        // 치킨 정보 패널 (상단)
        JPanel chickenInfoPanel = new JPanel();
        chickenInfoPanel.setLayout(new BoxLayout(chickenInfoPanel, BoxLayout.Y_AXIS));

        // 이미지 표시 패널
        JLabel imageLabel;
        if (stock <= 0) {
        	System.out.println(stock);
            // 품절 상태
            imageLabel = new JLabel(new ImageIcon("img/out_of_stock.png")); // 품절 이미지
            JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
        } else {
            // 정상 상태
            imageLabel = new JLabel(new ImageIcon(imagePath)); // 정상 치킨 이미지
        }
        
        // 이미지 경로 설정
        imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 중앙 정렬

        // 패널에 이미지 추가
        chickenInfoPanel.add(imageLabel);
        chickenInfoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 여백 추가

        
        // 수량 패널 (중앙)
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬
        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        JLabel quantityLabel = new JLabel("0");

        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) quantityLabel.setText(String.valueOf(quantity - 1));
        });

        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity < stock) { // 재고 수량과 비교
                quantityLabel.setText(String.valueOf(quantity + 1));
            } else {
                JOptionPane.showMessageDialog(dialog, "재고가 부족합니다.", "재고 부족", JOptionPane.WARNING_MESSAGE);
            }
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);

        // 추가 옵션 패널 (가운데)
        JPanel optionPanel = new JPanel();
        JRadioButton colaButton = new JRadioButton("콜라 (0원)");
        JRadioButton ciderButton = new JRadioButton("사이다 (0원)");
        JRadioButton fantaButton = new JRadioButton("환타 (300원)");
        JRadioButton milkButton = new JRadioButton("우유 (500원)");

        ButtonGroup group = new ButtonGroup();
        group.add(colaButton);
        group.add(ciderButton);
        group.add(fantaButton);
        group.add(milkButton);

        optionPanel.add(colaButton);
        optionPanel.add(ciderButton);
        optionPanel.add(fantaButton);
        optionPanel.add(milkButton);

        // 확인 및 취소 버튼 패널 (하단)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");
        
        confirmButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (stock <= 0) {
                JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
                return; // 품절이면 메소드 종료
            }
            
            if (quantity > stock) {
                JOptionPane.showMessageDialog(dialog, "선택한 수량이 재고를 초과합니다.", "재고 초과", JOptionPane.WARNING_MESSAGE);
                return; // 재고 초과 시 메소드 종료
            }

            int totalPrice = chickenprice * quantity;
            
            // 재고 감소
            updateStock2(chickenName, stock - quantity);
            
            // 선택된 옵션에 따라 가격 추가
            String options = "선택 안함"; // 기본 옵션
            if (colaButton.isSelected()) {
                options = "콜라 (0원)";
            } else if (ciderButton.isSelected()) {
                options = "사이다 (0원)";
            } else if (fantaButton.isSelected()) {
                options = "환타 (300원)";
                totalPrice += 300;
            } else if (milkButton.isSelected()) {
                options = "우유 (500원)";
                totalPrice += 500;
            }
            payment.updatePayment(chickenName, quantity, totalPrice, options); // 상품 이름, 수량, 가격, 추가 옵션 전달

//            JOptionPane.showMessageDialog(dialog, "총 갯수: " + quantity + "\n총 가격: " + totalPrice + "원");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // 패널 추가
        dialog.add(chickenInfoPanel);  // 치킨 정보 패널 추가
        dialog.add(quantityPanel);    // 수량 패널 추가
        dialog.add(optionPanel);       // 음료 옵션 패널 추가
        dialog.add(buttonPanel);       // 버튼 패널 추가
        dialog.setVisible(true);
    }
    
    // 재고 업데이트 메소드
    private void updateStock2(String chickenName, int newStock) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("UPDATE Menu SET stock = ? WHERE name = ?")) {
            statement.setInt(1, newStock);
            statement.setString(2, chickenName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    // 재고 수량을 데이터베이스에서 가져오는 메소드
     private int getStock2(String chickenName) {
         int stock = 0;
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
                 PreparedStatement statement = connection.prepareStatement("SELECT stock FROM Menu WHERE name = ?")) {
                statement.setString(1, chickenName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    stock = resultSet.getInt("stock");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return stock;
      }
    
    
    // 피&치 메뉴 표시
    private JPanel showpichMenu() {
        JPanel pichButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        String[] pichamanus = {
            "콤비네이션피자&후라이드치킨", "고구마피자&후라이드치킨", "콤비네이션피자&페퍼간장치킨", "스테이크 피자&후라이드치킨", "치즈피자&후라이드치킨",
            "스위트불고기피자&후라이드치킨","더블포테이토피자&후라이드치킨","통새우피자&후라이드치킨","콤비네이션피자&치즈크리스피치킨","콤비네이션피자&고추깐풍치킨"
        };

        String[] imagePaths = {
            "img/pi&ch/1 pi&ch.png",
            "img/pi&ch/2 pi&ch.png",
            "img/pi&ch/3 pi&ch.png",
            "img/pi&ch/4 pi&ch.png",
            "img/pi&ch/5 pi&ch.png",
            "img/pi&ch/6 pi&ch.png",
            "img/pi&ch/7 pi&ch.png",
            "img/pi&ch/8 pi&ch.png",
            "img/pi&ch/9 pi&ch.png",
            "img/pi&ch/10 pi&ch.png"
        };
        
        int[] pichprice = {
        		20900, 21000, 19000, 20000, 22000, 21000, 21000, 24000, 23000, 22000
        };

        for (int i = 0; i < pichamanus.length; i++) {
            String pichName = pichamanus[i];
            String imagePath = imagePaths[i];
            int price = pichprice[i];
            
            JButton pichButton = new JButton(new ImageIcon(imagePath));

            pichButton.addActionListener(e ->
                showpichDatail(pichName, price, imagePath)
            );
            
            // 버튼 크기 설정
            pichButton.setPreferredSize(new Dimension(180, 180)); // 원하는 크기로 조정
            pichButtonPanel.add(pichButton);
        }
        
        return pichButtonPanel;
    }
    
    //피자&치킨 추가옵션 메소드
    private void showpichDatail(String pichName, int pichprice, String imagePath) {
        // 재고 수량 가져오기
        int stock = getStock3(pichName);
        if (stock <= 0) {
            JOptionPane.showMessageDialog(null, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
            return; // 품절이면 메소드 종료
        }
    	
    	JDialog dialog = new JDialog();
        dialog.setTitle("피자&치킨 상세");
        dialog.setSize(500, 500);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // BoxLayout 사용

        // 피자&치킨 정보 패널 (상단)
        JPanel pichInfoPanel = new JPanel();
        pichInfoPanel.setLayout(new BoxLayout(pichInfoPanel, BoxLayout.Y_AXIS));

        // 이미지 표시 패널
        JLabel imageLabel;
        if (stock <= 0) {
            // 품절 상태
            imageLabel = new JLabel(new ImageIcon("img/out_of_stock.png")); // 품절 이미지
            JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
        } else {
            // 정상 상태
            imageLabel = new JLabel(new ImageIcon(imagePath)); // 정상 피자&치킨 이미지
        }
        
        // 이미지 경로 설정
        imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 중앙 정렬

        // 패널에 이미지와 이름 추가
        pichInfoPanel.add(imageLabel);
        pichInfoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 여백 추가

        
        // 수량 패널 (중앙)
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬
        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        JLabel quantityLabel = new JLabel("0");

        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) quantityLabel.setText(String.valueOf(quantity - 1));
        });

        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity < stock) { // 재고 수량과 비교
                quantityLabel.setText(String.valueOf(quantity + 1));
            } else {
                JOptionPane.showMessageDialog(dialog, "재고가 부족합니다.", "재고 부족", JOptionPane.WARNING_MESSAGE);
            }
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);

        // 추가 옵션 패널 (가운데)
        JPanel optionPanel = new JPanel();
        JRadioButton colaButton = new JRadioButton("콜라 (0원)");
        JRadioButton ciderButton = new JRadioButton("사이다 (0원)");
        JRadioButton fantaButton = new JRadioButton("환타 (300원)");
        JRadioButton milkButton = new JRadioButton("우유 (500원)");

        ButtonGroup group = new ButtonGroup();
        group.add(colaButton);
        group.add(ciderButton);
        group.add(fantaButton);
        group.add(milkButton);

        optionPanel.add(colaButton);
        optionPanel.add(ciderButton);
        optionPanel.add(fantaButton);
        optionPanel.add(milkButton);

        // 확인 및 취소 버튼 패널 (하단)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        confirmButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > stock) {
                JOptionPane.showMessageDialog(dialog, "선택한 수량이 재고를 초과합니다.", "재고 초과", JOptionPane.WARNING_MESSAGE);
                return; // 재고 초과 시 메소드 종료
            }
            
            int totalPrice = pichprice * quantity;
            
            // 재고 감소
            updateStock4(pichName, stock - quantity);
            
            // 선택된 옵션에 따라 가격 추가
            String options = "선택 안함"; // 기본 옵션
            if (colaButton.isSelected()) {
                options = "콜라 (0원)";
            } else if (ciderButton.isSelected()) {
                options = "사이다 (0원)";
            } else if (fantaButton.isSelected()) {
                options = "환타 (300원)";
                totalPrice += 300;
            } else if (milkButton.isSelected()) {
                options = "우유 (500원)";
                totalPrice += 500;
            }
            payment.updatePayment(pichName, quantity, totalPrice, options); // 상품 이름, 수량, 가격, 추가 옵션 전달

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // 패널 추가
        dialog.add(pichInfoPanel);  // 피자&치킨 정보 패널 추가
        dialog.add(quantityPanel);    // 수량 패널 추가
        dialog.add(optionPanel);       // 음료 옵션 패널 추가
        dialog.add(buttonPanel);       // 버튼 패널 추가
        dialog.setVisible(true);
    }
    
    // 재고 업데이트 메소드
    private void updateStock3(String pichName, int newStock) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("UPDATE Menu SET stock = ? WHERE name = ?")) {
            statement.setInt(1, newStock);
            statement.setString(2, pichName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // 재고 수량을 데이터베이스에서 가져오는 메소드
    private int getStock3(String pichName) {
        int stock = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("SELECT stock FROM Menu WHERE name = ?")) {
            statement.setString(1, pichName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("stock");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }
    
    
    
    // 디저트 메뉴 표시
    private JPanel showdessertMenu() {
        JPanel dessertButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        String[] dessertmanus = {
            "클라우드 치즈 케이크", "초코 크레이프 케이크", "블루베리 마카롱", "바닐라 마카롱", "다크 초콜릿 마카롱", 
            "베이컨 치즈 토스트", "까망베리 치즈 피낭시에", "베이컨 체다& 오믈렛 샌드위치", "밀크 푸딩", "치킨 베이컨 랩"
        };

        String[] imagePaths = {
            "img/dessert/1 dessert.png",
            "img/dessert/2 dessert.png",
            "img/dessert/3 dessert.png",
            "img/dessert/4 dessert.png",
            "img/dessert/5 dessert.png",
            "img/dessert/6 dessert.png",
            "img/dessert/7 dessert.png",
            "img/dessert/8 dessert.png",
            "img/dessert/9 dessert.png",
            "img/dessert/10 dessert.png"
        };
        
        int[] dessertprice = {
        		5000, 5000, 3000, 3000, 3000, 2500, 3000, 4000, 1000, 2500
        };

        for (int i = 0; i < dessertmanus.length; i++) {
            String dessertName = dessertmanus[i];
            String imagePath = imagePaths[i];
            int price = dessertprice[i];
            
            JButton dessertButton = new JButton(new ImageIcon(imagePath));

            dessertButton.addActionListener(e ->
                showdessertDetail(dessertName, price, imagePath)
            );
            
            // 버튼 크기 설정
            dessertButton.setPreferredSize(new Dimension(180, 180)); // 원하는 크기로 조정
            dessertButtonPanel.add(dessertButton);
        }
        
        return dessertButtonPanel;
    }
    
    //디저트 추가 옵션 메소드
    private void showdessertDetail(String dessertName, int dessertprice, String imagePath) {
        // 재고 수량 가져오기
        int stock = getStock(dessertName);
    	
    	JDialog dialog = new JDialog();
        dialog.setTitle("디저트 상세");
        dialog.setSize(350, 350);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // BoxLayout 사용

        // 디저트 정보 패널 (상단)
        JPanel dessertInfoPanel = new JPanel();
        dessertInfoPanel.setLayout(new BoxLayout(dessertInfoPanel, BoxLayout.Y_AXIS));

        // 이미지 표시 패널
        JLabel imageLabel;
        if (stock <= 0) {
            // 품절 상태
            imageLabel = new JLabel(new ImageIcon("img/out_of_stock.png")); // 품절 이미지
            JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
        } else {
            // 정상 상태
            imageLabel = new JLabel(new ImageIcon(imagePath)); // 정상 디저트 이미지
        }
        
        // 이미지 경로 설정
        imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 중앙 정렬

        // 패널에 이미지와 이름 추가
        dessertInfoPanel.add(imageLabel);
        dessertInfoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 여백 추가

        
        // 수량 패널 (중앙)
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬
        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        JLabel quantityLabel = new JLabel("0");

        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) quantityLabel.setText(String.valueOf(quantity - 1));
            
        });

        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity < stock) { // 재고 수량과 비교
                quantityLabel.setText(String.valueOf(quantity + 1));
            } else {
                JOptionPane.showMessageDialog(dialog, "재고가 부족합니다.", "재고 부족", JOptionPane.WARNING_MESSAGE);
            }
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);

        // 확인 및 취소 버튼 패널 (하단)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        confirmButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (stock <= 0) {
                JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
                return; // 품절이면 메소드 종료
            }
            
            if (quantity > stock) {
                JOptionPane.showMessageDialog(dialog, "선택한 수량이 재고를 초과합니다.", "재고 초과", JOptionPane.WARNING_MESSAGE);
                return; // 재고 초과 시 메소드 종료
            }
            int totalPrice = dessertprice * quantity;
            
            	// 재고 감소
            updateStock4(dessertName, stock - quantity);
            // 선택된 옵션에 따라 가격 추가
            String options = "선택 안함"; // 기본 옵션
            payment.updatePayment(dessertName, quantity, totalPrice, options); // 상품 이름, 수량, 가격, 추가 옵션 전달

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // 패널 추가
        dialog.add(dessertInfoPanel);  // 디저트 정보 패널 추가
        dialog.add(quantityPanel);    // 수량 패널 추가
        dialog.add(buttonPanel);       // 버튼 패널 추가
        dialog.setVisible(true);
    }
    
 // 재고 업데이트 메소드
    private void updateStock4(String dessertName, int newStock) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("UPDATE Menu SET stock = ? WHERE name = ?")) {
            statement.setInt(1, newStock);
            statement.setString(2, dessertName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    
 // 재고 수량을 데이터베이스에서 가져오는 메소드
    private int getStock4(String dessertName) {
        int stock = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("SELECT stock FROM Menu WHERE name = ?")) {
            statement.setString(1, dessertName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }
    
    
    // 음료 메뉴 표시
    private JPanel showdrinkMenu() {
        JPanel drinkButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        String[] drinkmanus = {
            "복숭아 아이스티", "아이스 자몽 허니 블랙티", "말차 라떼", "까망 크림 프라푸치노", "딸기 글레이즈드 크림 프라푸치노", 
            "아메리카노", "화이트 초콜릿 모카", "카라멜 마키아또", "딸기 가득 요거트", "레드 파워 패션티"
        };

        String[] imagePaths = {
            "img/drink/1 drink.png",
            "img/drink/2 drink.png",
            "img/drink/3 drink.png",
            "img/drink/4 drink.png",
            "img/drink/5 drink.png",
            "img/drink/6 drink.png",
            "img/drink/7 drink.png",
            "img/drink/8 drink.png",
            "img/drink/9 drink.png",
            "img/drink/10 drink.png"
        };
        
        int[] drinkprice = {
        		3000, 3000, 4000, 4500, 5000, 2000, 3000, 3000, 2000, 3500
        };

        for (int i = 0; i < drinkmanus.length; i++) {
            String drinkName = drinkmanus[i];
            String imagePath = imagePaths[i];
            int price = drinkprice[i];
            
            JButton drinkButton = new JButton(new ImageIcon(imagePath));

            
            //버튼 클릭 이벤트를 람다식으로 표현
            drinkButton.addActionListener(e ->
               	showdrinkDatail(drinkName, price, imagePath)
            );
            
            // 버튼 크기 설정
            drinkButton.setPreferredSize(new Dimension(180, 180)); // 원하는 크기로 조정
            drinkButtonPanel.add(drinkButton);
        }
        
        return drinkButtonPanel;
    }
    
    //음료 추가 옵션 메소드
    private void showdrinkDatail(String drinkName, int drinkprice, String imagePath) {
        // 재고 수량 가져오기
        int stock = getStock(drinkName);
    	
    	JDialog dialog = new JDialog();
        dialog.setTitle("음료 상세");
        dialog.setSize(350, 350);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // BoxLayout 사용

        // 음료 정보 패널 (상단)
        JPanel drinkInfoPanel = new JPanel();
        drinkInfoPanel.setLayout(new BoxLayout(drinkInfoPanel, BoxLayout.Y_AXIS));

        // 이미지 표시 패널
        JLabel imageLabel;
        if (stock <= 0) {
            // 품절 상태
            imageLabel = new JLabel(new ImageIcon("img/out_of_stock.png")); // 품절 이미지
            JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
        } else {
            // 정상 상태
            imageLabel = new JLabel(new ImageIcon(imagePath)); // 정상 음료 이미지
        }
        
        
        // 이미지 경로 설정
        imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 중앙 정렬

        // 패널에 이미지와 이름 추가
        drinkInfoPanel.add(imageLabel);
        drinkInfoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 여백 추가

        
        // 수량 패널 (중앙)
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬
        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        JLabel quantityLabel = new JLabel("0");

        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) quantityLabel.setText(String.valueOf(quantity - 1));
            
        });

        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity < stock) { // 재고 수량과 비교
                quantityLabel.setText(String.valueOf(quantity + 1));
            } else {
                JOptionPane.showMessageDialog(dialog, "재고가 부족합니다.", "재고 부족", JOptionPane.WARNING_MESSAGE);
            }
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);

        // 확인 및 취소 버튼 패널 (하단)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        confirmButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (stock <= 0) {
                JOptionPane.showMessageDialog(dialog, "품절된 메뉴입니다.", "품절", JOptionPane.WARNING_MESSAGE);
                return; // 품절이면 메소드 종료
            }
            
            if (quantity > stock) {
                JOptionPane.showMessageDialog(dialog, "선택한 수량이 재고를 초과합니다.", "재고 초과", JOptionPane.WARNING_MESSAGE);
                return; // 재고 초과 시 메소드 종료
            }
            int totalPrice = drinkprice * quantity;
            // 재고 감소
            updateStock5(drinkName, stock - quantity);
            // 선택된 옵션에 따라 가격 추가
            String options = "선택 안함"; // 기본 옵션
            payment.updatePayment(drinkName, quantity, totalPrice, options); // 상품 이름, 수량, 가격, 추가 옵션 전달

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // 패널 추가
        dialog.add(drinkInfoPanel);  // 음료 정보 패널 추가
        dialog.add(quantityPanel);    // 수량 패널 추가
        dialog.add(buttonPanel);       // 버튼 패널 추가
        dialog.setVisible(true);
    }
    
 // 재고 업데이트 메소드
    private void updateStock5(String drinkName, int newStock) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("UPDATE Menu SET stock = ? WHERE name = ?")) {
            statement.setInt(1, newStock);
            statement.setString(2, drinkName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // 재고 수량을 데이터베이스에서 가져오는 메소드
    private int getStock5(String drinkName, int newStock) {
        int stock = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("SELECT stock FROM Menu WHERE name = ?")) {
            statement.setString(1, drinkName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

}