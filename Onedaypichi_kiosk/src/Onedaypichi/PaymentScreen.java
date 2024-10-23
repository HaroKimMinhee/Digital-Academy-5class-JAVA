//결제 클래스

package Onedaypichi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class PaymentScreen {
    public PaymentScreen(Map<String, Cart.MenuItem> cart, int totalItems, int totalPrice, boolean isDineIn) {
        JFrame paymentFrame = new JFrame("결제 방법 선택");
        paymentFrame.setSize(400, 200);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paymentFrame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton cardButton = new JButton(new ImageIcon("img/card.png")); //카드
        cardButton.setPreferredSize(new Dimension(150, 110));
        JButton cashButton = new JButton(new ImageIcon("img/hyeongeum.png")); //현금
        cashButton.setPreferredSize(new Dimension(150, 110));

        cardButton.addActionListener(e -> processPayment("카드", paymentFrame, cart, totalItems, totalPrice, isDineIn));
        cashButton.addActionListener(e -> processPayment("현금", paymentFrame, cart, totalItems, totalPrice, isDineIn));

        panel.add(cardButton);
        panel.add(cashButton);
        paymentFrame.add(panel);
        paymentFrame.setVisible(true);
    }

    private void processPayment(String paymentMethod, JFrame paymentFrame, Map<String, Cart.MenuItem> cart, int totalItems, int totalPrice, boolean isDineIn) {
        JOptionPane.showMessageDialog(paymentFrame, paymentMethod + " 선택했습니다.");
        paymentFrame.dispose(); // 결제 방법 선택 창 닫기

        // 주문 내역 화면 생성
        JFrame orderFrame = new JFrame("주문 내역");
        orderFrame.setSize(500, 400);
        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        orderFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // 테이블 모델 생성
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("메뉴명");
        tableModel.addColumn("개수");
        tableModel.addColumn("가격");

        // 테이블에 장바구니 내용 추가
        for (Map.Entry<String, Cart.MenuItem> entry : cart.entrySet()) {
            Cart.MenuItem item = entry.getValue();
            tableModel.addRow(new Object[]{entry.getKey(), item.quantity, item.getTotalPrice() + "원"});
        }

        // 총 가격을 위한 빈 행 추가
        tableModel.addRow(new Object[]{"", "총 가격", ""});
        tableModel.addRow(new Object[]{"", totalPrice + "원", ""}); // 총 가격 표시 행 추가

        JTable table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null); // 편집 불가
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();

        // 결제 진행 버튼
        JButton proceedButton = new JButton("결제 진행");
        proceedButton.addActionListener(e -> {
            int waitingNumber = (int) (Math.random() * 1000);
            String waitingNumberLabel = isDineIn ? "매장 대기 번호: " + waitingNumber : "포장 대기 번호: " + waitingNumber;
            JOptionPane.showMessageDialog(orderFrame, "결제가 완료되었습니다.\n" + waitingNumberLabel);
            
            // 주문 완료 클래스 호출
            new OrderComplete(waitingNumberLabel);  // 추가된 부분
            
            orderFrame.dispose();
        });
        buttonPanel.add(proceedButton);

        // 결제 취소 버튼
        JButton cancelButton = new JButton("결제 취소");
        cancelButton.addActionListener(e -> {
            orderFrame.dispose(); // 주문 내역 창 닫기
        });
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH); // 하단에 버튼 패널 추가
        orderFrame.add(panel);
        orderFrame.setVisible(true);
    }
}

// 주문 완료 클래스
class OrderComplete {
    public OrderComplete(String waitingInfo) {
        JFrame completeFrame = new JFrame("주문 완료");
        completeFrame.setSize(500, 500);
        completeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 메시지 레이블 중앙 정렬
        JLabel message = new JLabel("주문이 완료되었습니다!!! " +"   "+ waitingInfo, SwingConstants.CENTER);
        message.setFont(new Font("SansSerif", Font.BOLD, 15));
        panel.add(message, BorderLayout.CENTER);

        // 홈으로 돌아가기 버튼 추가
        JButton homeButton = new JButton("홈으로 돌아가기");
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.addActionListener(e -> {
            completeFrame.dispose(); // 주문 완료 화면 닫기
            new Main(); // 홈 화면 열기
        });

        panel.add(homeButton, BorderLayout.SOUTH); // 하단에 버튼 추가
        completeFrame.add(panel);
        completeFrame.setVisible(true);
    }
}