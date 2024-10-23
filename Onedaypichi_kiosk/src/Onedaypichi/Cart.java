//장바구니 클래스

package Onedaypichi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Cart {
	private JTable table;
	private JLabel qu;
	private int totalItems = 0;
	private int totalPrice = 0;
    private boolean isDineIn; // 매장 식사 여부 추가
	
	// 상품 정보를 저장할 Map (메뉴 이름, 개수, 가격, 추가 옵션)
    public Map<String, MenuItem> cart = new HashMap<>();
    
    // 메뉴 항목을 저장할 클래스
    public static class MenuItem {
        int quantity;
        int price;
        String options;

        MenuItem(int quantity, int price, String options) {
            this.quantity = quantity;
            this.price = price;
            this.options = options;
        }

        int getTotalPrice() {
            return quantity * price;
        }
    }
    
    // Cart 생성자에 isDineIn 추가
    public Cart(boolean isDineIn) {
        this.isDineIn = isDineIn;
    }
	
	public JPanel crea(JFrame m) {
		JPanel payment = new JPanel(new BorderLayout());
		
        // 테이블 모델 설정
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("메뉴명");
        tableModel.addColumn("개수");
        tableModel.addColumn("가격");
        tableModel.addColumn("합계");

        table = new JTable(tableModel);
        table.setRowHeight(30); // 행 높이 설정
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // 첫 번째 열 너비
        table.getColumnModel().getColumn(1).setPreferredWidth(50);  // 두 번째 열 너비
        table.getColumnModel().getColumn(2).setPreferredWidth(50);  // 세 번째 열 너비
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // 네 번째 열 너비

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 150)); // 스크롤 패널 크기 설정
        payment.add(scrollPane, BorderLayout.CENTER);

        qu = new JLabel("수량: 0");
        payment.add(qu, BorderLayout.SOUTH);

        JButton delete = new JButton("전체 삭제");
        delete.addActionListener(e -> {
            totalItems = 0;
            totalPrice = 0;
            cart.clear(); // 장바구니 초기화
            tableModel.setRowCount(0); // 테이블 내용 초기화
            qu.setText("수량: 0");
        });

        JButton partialDelete = new JButton("부분 삭제");
        partialDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String itemName = (String) table.getValueAt(selectedRow, 0);
                MenuItem item = cart.get(itemName);
                if (item != null) {
                    totalItems -= 1; // 수량 1 감소
                    totalPrice -= item.price; // 가격 감소
                    item.quantity -= 1; // 개수 감소
                    if (item.quantity <= 0) {
                        cart.remove(itemName); // 수량이 0이면 항목 삭제
                    }
                    updateTable(); // 테이블 업데이트
                }
            } else {
                JOptionPane.showMessageDialog(payment, "삭제할 항목을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
				
        JButton payB = new JButton("결제하기");
        payB.addActionListener(e -> {
            // 장바구니가 비어있으면 경고 메시지 표시
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(payment, "장바구니에 상품이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

         // 결제 화면으로 이동, 장바구니 내용과 함께 전달
            new PaymentScreen(cart, totalItems, totalPrice, isDineIn); // cart, totalItems, totalPrice, isDineIn 전달
        });
		
        JPanel buttonPanel = new JPanel(); // 버튼 패널
        buttonPanel.add(partialDelete);
        buttonPanel.add(delete);
        buttonPanel.add(payB);
        payment.add(buttonPanel, BorderLayout.SOUTH); // 하단에 추가
        return payment;
    }
	
	public void updatePayment(String itemName, int quantity, int price, String options) {
        if (cart.containsKey(itemName)) {
            MenuItem item = cart.get(itemName);
            item.quantity += quantity; // 기존 수량 추가
        } else {
            cart.put(itemName, new MenuItem(quantity, price, options)); // 새 항목 추가
        }
        totalItems += quantity;
        totalPrice += price * quantity; // 총 가격 업데이트

        updateTable(); // 테이블 내용 업데이트
    }

    private void updateTable() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); // 기존 데이터 삭제

        for (Map.Entry<String, MenuItem> entry : cart.entrySet()) {
            MenuItem item = entry.getValue();
            tableModel.addRow(new Object[]{
                entry.getKey(),
                item.quantity,
                item.price,
                item.getTotalPrice() + "원"
            });
        }

        qu.setText("수량: " + totalItems);
    }
}
