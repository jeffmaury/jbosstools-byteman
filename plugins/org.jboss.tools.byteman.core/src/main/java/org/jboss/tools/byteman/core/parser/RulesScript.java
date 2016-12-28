package org.jboss.tools.byteman.core.parser;

import java.util.ArrayList;
import java.util.List;

public class RulesScript {

    private final List<BytemanInstruction> instructions = new ArrayList<>();
    
    public List<BytemanInstruction> getInstructions() {
        return instructions;
    }
    
    void addInstruction(BytemanInstruction instruction) {
        instructions.add(instruction);
    }
}
