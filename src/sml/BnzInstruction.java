package sml;

/**
 * Created by andrewho on 27/01/15.
 */
public class BnzInstruction extends Instruction {
    private int register;
    private String nextInstructionLabel;

    public BnzInstruction(String label, String opcode) {
        super(label, opcode);
    }

    public BnzInstruction(String label, int register, String nextInstructionLabel) {
        super(label, "bnz");
        this.register = register;
        this.nextInstructionLabel = nextInstructionLabel;
    }

    @Override
    public void execute(Machine m) {
        if(m == null) throw new NullPointerException("The machine object passed as a parameter was null!");
        if(m.getRegisters() == null) throw new NullPointerException("The registers object you tried to access was null!");
        if(m.getLabels() == null) throw new NullPointerException("The labels object you tried to access was null!");
        try {
            if(m.getRegisters().getRegister(register) != 0){

                int branchIndex = m.getLabels(nextInstructionLabel);
                m.setPc(branchIndex);
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " branching to instruction label " + nextInstructionLabel;
    }
}
