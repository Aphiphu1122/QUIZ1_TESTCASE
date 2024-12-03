import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillCalculator {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bill Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);

        // Select Bills
        JLabel lblSelectBills = new JLabel("Select Bills");
        lblSelectBills.setBounds(20, 20, 100, 20);
        frame.add(lblSelectBills);
        JRadioButton rbWaterBill = new JRadioButton("Water Bill");
        rbWaterBill.setBounds(20, 50, 100, 20);
        JRadioButton rbElectricBill = new JRadioButton("Electric Bill");
        rbElectricBill.setBounds(20, 80, 100, 20);
        ButtonGroup group = new ButtonGroup();
        group.add(rbWaterBill);
        group.add(rbElectricBill);
        frame.add(rbWaterBill);
        frame.add(rbElectricBill);

        // Room Type
        JLabel lblRoomType = new JLabel("Room Type");
        lblRoomType.setBounds(250, 20, 100, 20);
        frame.add(lblRoomType);
        String[] roomTypes = {"Please Select", "Single Bed", "Double Bed"};
        JComboBox<String> cbRoomType = new JComboBox<>(roomTypes);
        cbRoomType.setBounds(250, 50, 120, 20);
        frame.add(cbRoomType);

        // Meter fields
        JLabel lblLastMeter = new JLabel("Last Meter");
        lblLastMeter.setBounds(20, 120, 100, 20);
        frame.add(lblLastMeter);
        JTextField txtLastMeter = new JTextField();
        txtLastMeter.setBounds(120, 120, 100, 20);
        frame.add(txtLastMeter);
        JLabel lblCurrentMeter = new JLabel("Current Meter");
        lblCurrentMeter.setBounds(20, 150, 100, 20);
        frame.add(lblCurrentMeter);
        JTextField txtCurrentMeter = new JTextField();
        txtCurrentMeter.setBounds(120, 150, 100, 20);
        frame.add(txtCurrentMeter);

        // Unit Amount
        JLabel lblUnitAmount = new JLabel("Unit Amount");
        lblUnitAmount.setBounds(20, 180, 100, 20);
        frame.add(lblUnitAmount);
        JTextField txtUnitAmount = new JTextField();
        txtUnitAmount.setBounds(120, 180, 100, 20);
        txtUnitAmount.setEditable(false);
        frame.add(txtUnitAmount);

        // Result
        JLabel lblResult = new JLabel("Result");
        lblResult.setBounds(20, 210, 100, 20);
        frame.add(lblResult);
        JTextField txtResult = new JTextField();
        txtResult.setBounds(120, 210, 100, 20);
        txtResult.setEditable(false);
        frame.add(txtResult);

        // Total Rental
        JLabel lblTotalRental = new JLabel("Total Rental");
        lblTotalRental.setBounds(20, 240, 100, 20);
        frame.add(lblTotalRental);
        JTextField txtTotalRental = new JTextField();
        txtTotalRental.setBounds(120, 240, 100, 20);
        txtTotalRental.setEditable(false);
        frame.add(txtTotalRental);

        // Progress Bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(20, 270, 350, 20);
        progressBar.setStringPainted(true);
        frame.add(progressBar);

        // Buttons
        JButton btnCalculate = new JButton("Calculate Bill");
        btnCalculate.setBounds(20, 310, 150, 30);
        frame.add(btnCalculate);
        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(200, 310, 150, 30);
        frame.add(btnReset);

        // Action Listeners
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int lastMeter = Integer.parseInt(txtLastMeter.getText());
                    int currentMeter = Integer.parseInt(txtCurrentMeter.getText());
                    if (!validateMeterInputs(lastMeter, currentMeter)) return;

                    int units = calculateUnits(lastMeter, currentMeter);
                    boolean isWaterBill = rbWaterBill.isSelected();
                    boolean isElectricBill = rbElectricBill.isSelected();
                    int billResult = calculateBill(units, isWaterBill, isElectricBill);

                    String roomType = (String) cbRoomType.getSelectedItem();
                    int totalRental = calculateTotalRental(billResult, roomType);

                    txtUnitAmount.setText(String.valueOf(units));
                    txtResult.setText(String.valueOf(billResult));
                    txtTotalRental.setText(String.valueOf(totalRental));
                    progressBar.setValue(100);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for meters", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetValues(txtLastMeter, txtCurrentMeter, txtUnitAmount, txtResult, txtTotalRental, progressBar, group, cbRoomType);
            }
        });

        frame.setVisible(true);
    }

    // ฟังก์ชันตรวจสอบค่ามิเตอร์
    public static boolean validateMeterInputs(int lastMeter, int currentMeter) {
        if (currentMeter < lastMeter) {
            throw new IllegalArgumentException("Current Meter must be greater than Last Meter");
        }
        return true;
    }

    // ฟังก์ชันคำนวณจำนวนหน่วยที่ใช้
    public static int calculateUnits(int lastMeter, int currentMeter) {
        return currentMeter - lastMeter;
    }

    // ฟังก์ชันคำนวณบิล
    public static int calculateBill(int units, boolean isWaterBill, boolean isElectricBill) {
        if (isWaterBill) {
            return units * 5;
        } else if (isElectricBill) {
            return units * 6;
        } else {
            throw new IllegalArgumentException("Invalid bill type selected");
        }
    }

    // ฟังก์ชันคำนวณค่าเช่าทั้งหมด
    public static int calculateTotalRental(int billResult, String roomType) {
        if ("Single Bed".equals(roomType)) {
            return billResult + 1500;
        } else if ("Double Bed".equals(roomType)) {
            return billResult + 2000;
        } else {
            throw new IllegalArgumentException("Invalid room type selected");
        }
    }

    // ฟังก์ชันรีเซ็ตค่าทั้งหมด
    public static void resetValues(JTextField txtLastMeter, JTextField txtCurrentMeter, 
                                    JTextField txtUnitAmount, JTextField txtResult, 
                                    JTextField txtTotalRental, JProgressBar progressBar, 
                                    ButtonGroup group, JComboBox<String> cbRoomType) {
        txtLastMeter.setText("");
        txtCurrentMeter.setText("");
        txtUnitAmount.setText("");
        txtResult.setText("");
        txtTotalRental.setText("");
        progressBar.setValue(0);
        group.clearSelection();
        cbRoomType.setSelectedIndex(0);
    }
}
