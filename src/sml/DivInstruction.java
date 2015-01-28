package sml;

/**
 * Created by andrewho on 26/01/15.
 */
public class DivInstruction extends Instruction {

    private int result;
    private int op1;
    private int op2;

    public DivInstruction(String label, String op) {
        super(label, op);
    }

    public DivInstruction(String label, int result, int op1, int op2) {
        this(label, "div");
        this.result = result;
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void execute(Machine m) {
        if(m == null) throw new NullPointerException("The machine object passed as a parameter was null!");
        if(m.getRegisters() == null) throw new NullPointerException("The registers object you tried to access was null!");
        try {
            int value1 = m.getRegisters().getRegister(op1);
            int value2 = m.getRegisters().getRegister(op2);
            if(value2 == 0){
                System.out.println("The value in register " + op2 + " is zero...Cannot divide by zero!");
            } else {
                m.getRegisters().setRegister(result, value1 / value2);
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + op1 + " / " + op2 + " to " + result;
    }
}
