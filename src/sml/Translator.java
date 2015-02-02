package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.lang.reflect.Constructor;
import java.lang.Class.*;
import java.lang.reflect.*;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private String line = "";
    private Labels labels; // The labels of the program being translated
    private ArrayList<Instruction> program; // The program to be created
    private String fileName; // source file of SML code

    private static final String SRC = "src";

    public Translator(String fileName) {
        this.fileName = SRC + "/" + fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"
    public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

        try (Scanner sc = new Scanner(new File(fileName))) {
            // Scanner attached to the file chosen by the user
            labels = lab;
            labels.reset();
            program = prog;
            program.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next line into line
            while (line != null) {
                // Store the label in label
                String label = scan();

                if (label.length() > 0) {
                    Instruction ins = reflectionInstanceGenerator(label);
                    //Instruction ins = getInstruction(label);
                    if (ins != null) {
                        labels.addLabel(label);
                        program.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.out.println("File: IO error " + ioE.getMessage());
            return false;
        }
        return true;
    }

    // line should consist of an MML instruction, with its label already
    // removed. Translate line into an instruction with label label
    // and return the instruction
//	public Instruction getInstruction(String label) {
//		int s1; // Possible operands of the instruction
//		int s2;
//		int r;
//		int x;
//
//		if (line.equals(""))
//			return null;
//
//		String ins = scan();
//
//		switch (ins) {
//		case "add":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new AddInstruction(label, r, s1, s2);
//		case "lin":
//			r = scanInt();
//			s1 = scanInt();
//			return new LinInstruction(label, r, s1);
//		case "sub":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new SubInstruction(label, r, s1, s2);
//		case "mul":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new MulInstruction(label, r, s1, s2);
//		case "div":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new DivInstruction(label, r, s1, s2);
//		case "out":
//			s1 = scanInt();
//			return new OutInstruction(label,s1);
//		case "bnz":
//			s1 = scanInt();
//			String nextLabel = scan();
//			return new BnzInstruction(label,s1,nextLabel);
//		}
//		return null;
//	}

    private Instruction reflectionInstanceGenerator(String label) {
        try {
            if (label != null) {
                //work out which type of instruction class to create
                if (line.equals("")) {
                    return null;
                }
                String ins = scan();
                String capitalIns = ins.substring(0, 1).toUpperCase() + ins.substring(1).toLowerCase();
                String instClassName = "sml." + capitalIns + "Instruction";
                Class instructionClass = Class.forName(instClassName);
                //define the parameters and their corresponding types given in the instruction
                List<Class<?>> operandTypes = new ArrayList<>();
                operandTypes.add(String.class);
                List<String> operandsToBeCast = new ArrayList<>();
                operandsToBeCast.add(label);
                while (line.length() != 0) {
                    String scannedWord = scan();
                    //is operand an Integer or a String?
                    if (Character.isDigit(scannedWord.charAt(0))) {
                        operandTypes.add(int.class);
                        if (scannedWord.length() == 0) {
                            operandsToBeCast.add("MAX_VALUE");
                        } else {
                            operandsToBeCast.add(scannedWord);
                        }
                    } else {
                        operandTypes.add(String.class);
                        operandsToBeCast.add(scannedWord);
                    }
                }
                // check that method is reading in correct instruction parameters
//                System.out.println("Operand Types: " + operandTypes.toString());
//                System.out.println("Operands: " + operandsToBeCast.toString());

                //find the matching constructor to use
                Constructor[] constructorList = instructionClass.getDeclaredConstructors();
                for (Constructor ctor : constructorList) {
                    Class[] paramTypes = ctor.getParameterTypes();
                    if (paramTypes.length == operandTypes.size()) {
                        boolean allTypesMatch = true;
                        for (int i = 0; i < paramTypes.length; i++) {
                            if (!paramTypes[i].equals(operandTypes.get(i))) {
                                allTypesMatch = false;
                            }
                        }
                        if (allTypesMatch) {
                            //generate instruction parameters for constructor
                            try {
                                ctor.setAccessible(true);
                                Object[] initArgs = new Object[paramTypes.length];
                                for (int i = 0; i < operandTypes.size(); i++) {
                                    if (operandTypes.get(i).getCanonicalName().equals("int")) {
                                        if (operandsToBeCast.get(i).equals("MAX_VALUE")) {
                                            initArgs[i] = Integer.MAX_VALUE;
                                        } else {
                                            initArgs[i] = Integer.parseInt(operandsToBeCast.get(i));
                                        }
                                    } else {
                                        initArgs[i] = (String)operandsToBeCast.get(i);
                                    }
                                }
                                for(Object o: initArgs){
//                                    System.out.println("initArgs parameters for " + ctor.getDeclaringClass() + " are: "+ o.toString() + " which is of type: " + o.getClass());
                                }
                                Instruction result = (Instruction) ctor.newInstance(initArgs);
                                return result;
                            } catch (InstantiationException ex) {
                                ex.printStackTrace();
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            } catch (InvocationTargetException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                return null;
            } else {
                System.out.println("The label you passed to the reflectionInstanceGenerator method was null");
                return null;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
    private String scan() {
        line = line.trim();
        if (line.length() == 0)
            return "";

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
//	private int scanInt() {
//		String word = scan();
//		if (word.length() == 0) {
//			return Integer.MAX_VALUE;
//		}
//
//		try {
//			return Integer.parseInt(word);
//		} catch (NumberFormatException e) {
//			return Integer.MAX_VALUE;
//		}
//	}
}