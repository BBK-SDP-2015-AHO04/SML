package smltests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sml.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class OutInstructionTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private Machine testMachine;
    private Registers testReg;
    Instruction testOutInst;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr((new PrintStream(errContent)));

        testMachine = new Machine();
        testMachine.setRegisters(new Registers());
        testReg = testMachine.getRegisters();
        testReg.setRegister(6,465);
        testReg.setRegister(8,215);
        testReg.setRegister(21,14);
        testOutInst = new OutInstruction("test0",6);
    }

    @After
    public void cleanup() throws Exception {
        System.setOut(null);
        System.setErr((null));
    }

    @Test
    public void testExecute() throws Exception {
        try {
            testOutInst.execute(testMachine);
            assertEquals(465, outContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("test0: out - The contents of register 6 is: 465",testOutInst.toString());
    }
}