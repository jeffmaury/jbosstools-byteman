/**
 * 
 */
package org.jboss.tools.byteman.core.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanRule extends BytemanNamedInstruction {

    private final List<BytemanInstruction> instructions = new ArrayList<>();
    
    /**
     * @param line
     * @param column
     * @param type
     * @param text
     */
    public BytemanRule(int line, int column, String text, String name) {
        super(line, column, BytemanInstructionType.RULE, text, name);
    }
    
    public List<BytemanInstruction> getInstructions() {
        return instructions;
    }
    
    public BytemanRule addInstruction(BytemanInstruction instruction) {
        instructions.add(instruction);
        return this;
    }
}
