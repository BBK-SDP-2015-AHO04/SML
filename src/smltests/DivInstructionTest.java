package smltests;

import org.junit.Before;
import org.junit.Test;
import sml.*;

import static org.junit.Assert.*;

public class DivInstructionTest {

    private Machine testMachine;
    private Registers testReg;
    Instruction testDivInst;

    @Before
    public void setUp() throws Exception {
        testMachine = new Machine();
        testMachine.setRegisters(new Registers());
        testReg = testMachine.getRegisters();
        testReg.setRegister(6,465);
        testReg.setRegister(8,215);
        testReg.setRegister(21,14);
        testDivInst = new DivInstruction("test0",21,465,215);
    }

    @Test
    public void testExecute() throws Exception {
        try {
            testDivInst.execute(testMachine);
            assertEquals(2, testReg.getRegister(21));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("test0: div 465 / 215 to 21",testDivInst.toString());
    }
}