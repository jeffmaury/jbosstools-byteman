/**
 * 
 */
package org.jboss.tools.byteman.core.parser;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanLocationInstruction extends BytemanInstruction {
    
    public enum BytemanLocationInstructionType {
        ENTRY, 
        LINE, 
        READ, 
        READ_COMPLETED, 
        WRITE, 
        WRITE_COMPLETED, 
        INVOKE, 
        INVOKE_COMPLETED, 
        SYNCHRONIZE, 
        SYNCHRONIZE_COMPLETED, 
        THROW, 
        EXIT, 
        EXCEPTION_EXIT;
    }

    private final BytemanLocationInstructionType locationType;
    
    private final String parameters;
    
    /**
     * @param line
     * @param column
     * @param type
     * @param text
     */
    public BytemanLocationInstruction(int line, int column, BytemanInstructionType type, String text, BytemanLocationInstructionType locationType, String parameters) {
        super(line, column, type, text);
        this.locationType = locationType;
        this.parameters = parameters;
    }
    /**
     * @return the locationType
     */
    public BytemanLocationInstructionType getLocationType() {
        return locationType;
    }
    /**
     * @return the parameters
     */
    public String getParameters() {
        return parameters;
    }
}
