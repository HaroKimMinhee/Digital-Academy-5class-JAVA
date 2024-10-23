//관리자 모드

package Onedaypichi;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Admin {
    private JFrame frame;
    private JTextField itemNameField;
    private JTextField stockField;

    public Admin() {
        frame = new JFrame("관리자 모드");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        frame.add(new JLabel("메뉴 이름:"));
        itemNameField = new JTextField();
        frame.add(itemNameField);

        frame.add(new JLabel("재고 수량:"));
        stockField = new JTextField();
        frame.add(stockField);

        JButton updateButton = new JButton("재고 업데이트");
        updateButton.addActionListener(new UpdateStockAction());
        frame.add(updateButton);

        JButton checkButton = new JButton("품절 확인");
        checkButton.addActionListener(new CheckOutOfStockAction());
        frame.add(checkButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class UpdateStockAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            int additionalStock;

            try {
                additionalStock = Integer.parseInt(stockField.getText());
                if (additionalStock < 0) {
                    JOptionPane.showMessageDialog(frame, "재고 수량은 0 이상의 값이어야 합니다.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "유효한 재고 수량을 입력하세요.");
                return;
            }

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234")) {
                // 현재 재고를 가져오는 쿼리
                String selectQuery = "SELECT stock FROM Menu WHERE name = ?";
                try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                    selectStatement.setString(1, itemName);
                    ResultSet resultSet = selectStatement.executeQuery();

                    if (resultSet.next()) {
                        int currentStock = resultSet.getInt("stock");
                        int newStock = currentStock + additionalStock; // 현재 재고에 추가 재고를 더함

                        // 업데이트 쿼리
                        String updateQuery = "UPDATE Menu SET stock = ? WHERE name = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newStock);
                            updateStatement.setString(2, itemName);
                            int rowsAffected = updateStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(frame, "재고가 업데이트되었습니다.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "해당 메뉴를 찾을 수 없습니다.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "해당 메뉴를 찾을 수 없습니다.");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "데이터베이스 오류: " + ex.getMessage());
            }
        }
    }

    private class CheckOutOfStockAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Onedaypichi", "root", "1234")) {
                String query = "SELECT stock FROM Menu WHERE name = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, itemName);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        int stock = resultSet.getInt("stock");
                        if (stock <= 0) {
                            JOptionPane.showMessageDialog(frame, "품절입니다.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "현재 재고: " + stock);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "해당 메뉴를 찾을 수 없습니다.");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "데이터베이스 오류: " + ex.getMessage());
            }
        }
    }
}
