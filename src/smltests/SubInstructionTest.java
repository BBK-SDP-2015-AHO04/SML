package smltests;

import org.junit.Before;
import org.junit.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.SubInstruction;

import static org.junit.Assert.*;

public class SubInstructionTest {

    private Machine testMachine;
    private Registers testReg;
    Instruction testSubInst;

    @Before
    public void setUp() throws Exception {
        testMachine = new Machine();
        testMachine.setRegisters(new Registers());
        testReg = testMachine.getRegisters();
        testReg.setRegister(6,465);
        testReg.setRegister(8,215);
        testReg.setRegister(21,14);
        testSubInst = new SubInstruction("test0",21,465,215);
    }

    @Test
    public void testExecute() throws Exception {
        try {
            testSubInst.execute(testMachine);
            assertEquals(250, testReg.getRegister(21));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("test0: sub 465 - 215 to 21",testSubInst.toString());
    }
}