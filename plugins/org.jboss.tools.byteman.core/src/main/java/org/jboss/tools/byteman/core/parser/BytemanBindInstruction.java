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
public class BytemanBindInstruction extends BytemanInstruction {

    private List<String> lines = new ArrayList<>();
    
    /**
     * @param line
     * @param column
     * @param type
     * @param text
     */
    public BytemanBindInstruction(int line, int column, String text) {
        super(line, column, BytemanInstructionType.BIND, null);
        lines.add(text);
    }

    public void add(String line) {
        lines.add(line);
    }

    /* (non-Javadoc)
     * @see org.jboss.tools.byteman.core.parser.BytemanInstruction#getText()
     */
    @Override
    public String getText() {
        return lines.stream().reduce(null, (a,b) -> (a==null)?b:a + "\n" + b);
    }

    
}
