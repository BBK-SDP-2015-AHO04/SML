package smltests;

import org.junit.Before;
import org.junit.Test;
import sml.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BnzInstructionTest {

    private Machine testMachine;
    private Registers testReg;
    private Instruction testBnzInst;

    @Before
    public void setUp() throws Exception {
        testMachine = new Machine();
        testMachine.setRegisters(new Registers());
        testMachine.setPc(0);
        testReg = testMachine.getRegisters();
        Labels testLabels  = new Labels();
        ArrayList<Instruction> testInstr = new ArrayList<>();
        testInstr.add(new LinInstruction("test1",1,5));
        testLabels.addLabel("test1");
        testInstr.add(new LinInstruction("test2",2,1));
        testLabels.addLabel("test2");
        testInstr.add(new LinInstruction("test3",3,1));
        testLabels.addLabel("test3");
        testInstr.add(new MulInstruction("test4",2,1,2));
        testLabels.addLabel("test4");
        testInstr.add(new SubInstruction("test5",1,1,3));
        testLabels.addLabel("test5");
        testBnzInst = (new BnzInstruction("test6",1,"test4"));
        testInstr.add(testBnzInst);
        testLabels.addLabel("test6");
        testInstr.add(new OutInstruction("test7",2));
        testLabels.addLabel("test7");
        testMachine.setProg(testInstr);
        testMachine.setLabels(testLabels);
    }

    @Test
    public void testExecute() throws Exception {
        try {
            while(testMachine.getPc() < testMachine.getProg().size()){
                Instruction ins = testMachine.getProg().get(testMachine.getPc());
                testMachine.setPc(testMachine.getPc() + 1);
                ins.execute(testMachine);
            }
            assertEquals(120,testReg.getRegister(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("test6: bnz branching to instruction label test4",testBnzInst.toString());
    }
}