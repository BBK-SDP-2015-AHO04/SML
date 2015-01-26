package smltests;

import org.junit.Before;
import org.junit.Test;
import sml.*;

import static org.junit.Assert.*;

public class MulInstructionTest {

    private Machine testMachine;
    private Registers testReg;
    Instruction testMulInst;

    @Before
    public void setUp() throws Exception {
        testMachine = new Machine();
        testMachine.setRegisters(new Registers());
        testReg = testMachine.getRegisters();
        testReg.setRegister(6,465);
        testReg.setRegister(8,215);
        testReg.setRegister(21,14);
        testMulInst = new MulInstruction("test0",21,465,215);
    }

    @Test
    public void testExecute() throws Exception {
        try {
            testMulInst.execute(testMachine);
            assertEquals(99975, testReg.getRegister(21));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("test0: mul 465 * 215 to 21",testMulInst.toString());
    }
}