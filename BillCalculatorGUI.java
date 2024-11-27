import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class BillCalculatorGUI {
   public static void main(String[] args) {
       JFrame frame = new JFrame("Bill Calculator");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(400, 400);
       frame.setLayout(null);

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

       JLabel lblRoomType = new JLabel("Room Type");
       lblRoomType.setBounds(250, 20, 100, 20);
       frame.add(lblRoomType);
       String[] roomTypes = {"Please Select", "Single Bed", "Double Bed"};
       JComboBox<String> cbRoomType = new JComboBox<>(roomTypes);
       cbRoomType.setBounds(250, 50, 120, 20);
       frame.add(cbRoomType);

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

       JLabel lblUnitAmount = new JLabel("Unit Amount");
       lblUnitAmount.setBounds(20, 180, 100, 20);
       frame.add(lblUnitAmount);
       JTextField txtUnitAmount = new JTextField();
       txtUnitAmount.setBounds(120, 180, 100, 20);
       txtUnitAmount.setEditable(false);
       frame.add(txtUnitAmount);
       JLabel lblWaiting = new JLabel("Waiting");
       lblWaiting.setBounds(230, 180, 100, 20);
       frame.add(lblWaiting);

       JLabel lblResult = new JLabel("Result");
       lblResult.setBounds(20, 210, 100, 20);
       frame.add(lblResult);
       JTextField txtResult = new JTextField();
       txtResult.setBounds(120, 210, 100, 20);
       txtResult.setEditable(false);
       frame.add(txtResult);

       JLabel lblTotalRental = new JLabel("Total Rental");
       lblTotalRental.setBounds(20, 240, 100, 20);
       frame.add(lblTotalRental);
       JTextField txtTotalRental = new JTextField();
       txtTotalRental.setBounds(120, 240, 100, 20);
       txtTotalRental.setEditable(false);
       frame.add(txtTotalRental);

       JProgressBar progressBar = new JProgressBar(0, 100);
       progressBar.setBounds(20, 270, 350, 20);
       progressBar.setStringPainted(true);
       frame.add(progressBar);

       JButton btnCalculate = new JButton("Calculate Bill");
       btnCalculate.setBounds(20, 310, 150, 30);
       frame.add(btnCalculate);
       JButton btnReset = new JButton("Reset");
       btnReset.setBounds(200, 310, 150, 30);
       frame.add(btnReset);

       btnCalculate.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   int lastMeter = Integer.parseInt(txtLastMeter.getText());
                   int currentMeter = Integer.parseInt(txtCurrentMeter.getText());
                   if (currentMeter < lastMeter) {
                       JOptionPane.showMessageDialog(frame, "Current Meter must higher than Last Meter", "Error", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   int units = currentMeter - lastMeter;
                   int billResult;
                   if (rbWaterBill.isSelected()) {
                       billResult = units * 5;
                   } else if (rbElectricBill.isSelected()) {
                       billResult = units * 6;
                   } else {
                       JOptionPane.showMessageDialog(frame, "Please select a bill type", "Error", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   txtUnitAmount.setText(String.valueOf(units));
                   txtResult.setText(String.valueOf(billResult));
                   String roomType = (String) cbRoomType.getSelectedItem();
                   int totalRental = 0;
                   if ("Single Bed".equals(roomType)) {
                       totalRental = billResult + 1500;
                       progressBar.setValue(100);
                   } else if ("Double Bed".equals(roomType)) {
                       totalRental = billResult + 2000;
                       progressBar.setValue(100);
                   } else {
                       JOptionPane.showMessageDialog(frame, "Please select a room type", "Error", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   txtTotalRental.setText(String.valueOf(totalRental));
               } catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(frame, "Please enter valid numbers for meters", "Error", JOptionPane.ERROR_MESSAGE);
               }
           }
       });
       btnReset.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               txtLastMeter.setText("");
               txtCurrentMeter.setText("");
               txtUnitAmount.setText("");
               txtResult.setText("");
               txtTotalRental.setText("");
               progressBar.setValue(0);
               group.clearSelection();
               cbRoomType.setSelectedIndex(0);
           }
       });
       frame.setVisible(true);
   }
}