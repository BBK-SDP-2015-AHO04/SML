package sml;

/**
 * This class ....
 * 
 * @author someone
 */

public class LinInstruction extends Instruction {
	private int register;
	private int value;

	public LinInstruction(String label, String opcode) {
		super(label, opcode);
	}

	public LinInstruction(String label, int register, int value) {
		super(label, "lin");
		this.register = register;
		this.value = value;

	}

	@Override
	public void execute(Machine m) {
		if(m == null) throw new NullPointerException("The machine object passed as a parameter was null!");
		if(m.getRegisters() == null) throw new NullPointerException("The registers object you tried to access was null!");
		try {
			m.getRegisters().setRegister(register, value);
		} catch (ArrayIndexOutOfBoundsException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return super.toString() + " register " + register + " value is " + value;
	}
}
