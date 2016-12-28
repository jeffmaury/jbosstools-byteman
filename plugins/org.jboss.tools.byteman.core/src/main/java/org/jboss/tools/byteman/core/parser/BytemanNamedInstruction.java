/**
 * 
 */
package org.jboss.tools.byteman.core.parser;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanNamedInstruction extends BytemanInstruction {

    private final String name;
    /**
     * @param line
     * @param column
     * @param type
     * @param text
     */
    public BytemanNamedInstruction(int line, int column, BytemanInstructionType type, String text, String name) {
        super(line, column, type, text);
        this.name = name;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}
