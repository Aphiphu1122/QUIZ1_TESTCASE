import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class BillCalculatorTest {

    // ทดสอบฟังก์ชัน validateMeterInputs
    @Test
    public void testValidateMeterInputs_validInput() {
        int lastMeter = 100;
        int currentMeter = 150;
        assertDoesNotThrow(() -> BillCalculator.validateMeterInputs(lastMeter, currentMeter), 
                           "Should not throw exception for valid inputs");
    }

    @Test
    public void testValidateMeterInputs_invalidInput() {
        int lastMeter = 150;
        int currentMeter = 100;
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> 
            BillCalculator.validateMeterInputs(lastMeter, currentMeter),
            "Expected IllegalArgumentException for current meter less than last meter"
        );
        assertEquals("Current Meter must be greater than Last Meter", thrown.getMessage());
    }

    // ทดสอบฟังก์ชัน calculateUnits
    @Test
    public void testCalculateUnits() {
        int lastMeter = 100;
        int currentMeter = 150;
        int expectedUnits = 50;
        int result = BillCalculator.calculateUnits(lastMeter, currentMeter);
        assertEquals(expectedUnits, result, "Units calculation should be correct");
    }

    // ทดสอบฟังก์ชัน calculateBill
    @Test
    public void testCalculateBill_waterBill() {
        int units = 50;
        boolean isWaterBill = true;
        boolean isElectricBill = false;
        int expectedBill = 50 * 5; // 5 is the unit price for water
        int result = BillCalculator.calculateBill(units, isWaterBill, isElectricBill);
        assertEquals(expectedBill, result, "Water bill calculation should be correct");
    }

    @Test
    public void testCalculateBill_electricBill() {
        int units = 50;
        boolean isWaterBill = false;
        boolean isElectricBill = true;
        int expectedBill = 50 * 6; // 6 is the unit price for electricity
        int result = BillCalculator.calculateBill(units, isWaterBill, isElectricBill);
        assertEquals(expectedBill, result, "Electric bill calculation should be correct");
    }

    @Test
    public void testCalculateBill_invalidBillType() {
        int units = 50;
        boolean isWaterBill = false;
        boolean isElectricBill = false;
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> 
            BillCalculator.calculateBill(units, isWaterBill, isElectricBill),
            "Expected IllegalArgumentException for invalid bill type"
        );
        assertEquals("Invalid bill type selected", thrown.getMessage());
    }

    // ทดสอบฟังก์ชัน calculateTotalRental
    @Test
    public void testCalculateTotalRental_singleBed() {
        int billResult = 300;
        String roomType = "Single Bed";
        int expectedRental = 300 + 1500; // Single bed has an additional rental of 1500
        int result = BillCalculator.calculateTotalRental(billResult, roomType);
        assertEquals(expectedRental, result, "Single bed total rental should be correct");
    }

    @Test
    public void testCalculateTotalRental_doubleBed() {
        int billResult = 300;
        String roomType = "Double Bed";
        int expectedRental = 300 + 2000; // Double bed has an additional rental of 2000
        int result = BillCalculator.calculateTotalRental(billResult, roomType);
        assertEquals(expectedRental, result, "Double bed total rental should be correct");
    }

    @Test
    public void testCalculateTotalRental_invalidRoomType() {
        int billResult = 300;
        String roomType = "Triple Bed"; // Invalid room type
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> 
            BillCalculator.calculateTotalRental(billResult, roomType),
            "Expected IllegalArgumentException for invalid room type"
        );
        assertEquals("Invalid room type selected", thrown.getMessage());
    }

    // ทดสอบฟังก์ชัน resetValues
    @Test
    public void testResetValues() {
        JTextField txtLastMeter = new JTextField("100");
        JTextField txtCurrentMeter = new JTextField("150");
        JTextField txtUnitAmount = new JTextField("50");
        JTextField txtResult = new JTextField("250");
        JTextField txtTotalRental = new JTextField("1750");
        JProgressBar progressBar = new JProgressBar();
        ButtonGroup group = new ButtonGroup();
        JComboBox<String> cbRoomType = new JComboBox<>(new String[]{"Single Bed"});

        // สร้างค่าก่อนรีเซ็ต
        BillCalculator.resetValues(txtLastMeter, txtCurrentMeter, txtUnitAmount, txtResult, txtTotalRental, progressBar, group, cbRoomType);

        // ตรวจสอบว่า field ถูกรีเซ็ต
        assertEquals("", txtLastMeter.getText(), "Last meter should be empty after reset");
        assertEquals("", txtCurrentMeter.getText(), "Current meter should be empty after reset");
        assertEquals("", txtUnitAmount.getText(), "Unit amount should be empty after reset");
        assertEquals("", txtResult.getText(), "Result should be empty after reset");
        assertEquals("", txtTotalRental.getText(), "Total rental should be empty after reset");
        assertEquals(0, progressBar.getValue(), "Progress bar should be reset to 0");
        assertNull(group.getSelection(), "Radio buttons should be unselected");
        assertEquals(0, cbRoomType.getSelectedIndex(), "Room type should be reset to default");
    }
}
