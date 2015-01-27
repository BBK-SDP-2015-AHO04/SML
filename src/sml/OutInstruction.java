package sml;

/**
 * Created by andrewho on 26/01/15.
 */
public class OutInstruction extends Instruction {

    private int op1;
    private int value;

    public OutInstruction(String label, String op) {
        super(label, op);
    }

    public OutInstruction(String label, int op1) {
        this(label, "out");
        this.op1 = op1;
        value = 0;
    }

    @Override
    public void execute(Machine m) {
        if(m == null) throw new NullPointerException("The machine object passed as a parameter was null!");
        if(m.getRegisters() == null) throw new NullPointerException("The registers object you tried to access was null!");
        try {
            value = m.getRegisters().getRegister(op1);
            System.out.println("The contents of register " + op1 + " is: " + value);
        } catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " - The contents of register " + op1 + " will be printed to screen.";
    }
}
