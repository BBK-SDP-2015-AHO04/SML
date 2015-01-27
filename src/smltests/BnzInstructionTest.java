package smltests;

import org.junit.Before;
import org.junit.Test;
import sml.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BnzInstructionTest {

    private Machine testMachine;
    private Registers testReg;
    Instruction testBnzInst;

    @Before
    public void setUp() throws Exception {
        testMachine = new Machine();
        testMachine.setRegisters(new Registers());
        testReg = testMachine.getRegisters();
        testReg.setRegister(6,465);
        testReg.setRegister(8,215);
        testReg.setRegister(21,14);
//        ArrayList<Instruction> testInstr = new ArrayList<>();
//        testInstr.add(new AddInstruction("test1",14,6,21));
//        testInstr.add(new SubInstruction("test2",17,8,21));
//        testBnzInst = new BnzInstruction("test3",14,"test5");
//        testInstr.add(testBnzInst);
//        testInstr.add(new MulInstruction("test4",3,,21));
//        testInstr.add(new DivInstruction("test5",14,6,21));
//        testInstr.add(new OutInstruction("test6",14));
//        testMachine.setProg(testInstr);

    }

    @Test
    public void testExecuteWhenRegisterIsZero() throws Exception {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExecuteWhenRegisterIsNotZero() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        assertEquals("test0: bnz 6 / 8 to 21",testBnzInst.toString());
    }
}