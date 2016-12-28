/**
 * 
 */
package org.jboss.tools.byteman.core.parser;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanInstruction {
    private final int line;
    
    private final int column;
    
    private final BytemanInstructionType type;
    
    private final String text;
    
    BytemanInstruction(int line, int column, BytemanInstructionType type, String text) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.text = text;
    }

    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the type
     */
    public BytemanInstructionType getType() {
        return type;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

}
