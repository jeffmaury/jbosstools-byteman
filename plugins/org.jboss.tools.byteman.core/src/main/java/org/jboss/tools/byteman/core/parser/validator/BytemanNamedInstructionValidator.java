/**
 * 
 */
package org.jboss.tools.byteman.core.parser.validator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.jboss.tools.byteman.core.parser.BytemanNamedInstruction;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanNamedInstructionValidator implements BytemanInstructionValidator<BytemanNamedInstruction> {

    private String message;

    public BytemanNamedInstructionValidator(String message) {
        this.message = message;
    }
    
    @Override
    public void validate(BytemanInstructionValidatorRegistry registry, BytemanNamedInstruction instruction,
            IResource resource) {
        String name = instruction.getName();
        if (StringUtils.isBlank(name)) {
            createMarker(resource, message, instruction.getLine(), IMarker.SEVERITY_ERROR);
        }
    }
}
