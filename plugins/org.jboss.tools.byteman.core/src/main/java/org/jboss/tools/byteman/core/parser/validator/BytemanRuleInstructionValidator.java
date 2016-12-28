/**
 * 
 */
package org.jboss.tools.byteman.core.parser.validator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.jboss.tools.byteman.core.Messages;
import org.jboss.tools.byteman.core.parser.BytemanInstruction;
import org.jboss.tools.byteman.core.parser.BytemanRule;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanRuleInstructionValidator implements BytemanInstructionValidator<BytemanRule> {

    /* (non-Javadoc)
     * @see org.jboss.tools.byteman.core.parser.validator.BytemanInstructionValidator#validate(org.jboss.tools.byteman.core.parser.validator.BytemanInstructionValidatorRegistry, org.jboss.tools.byteman.core.parser.BytemanInstruction, org.eclipse.core.resources.IResource)
     */
    @Override
    public void validate(BytemanInstructionValidatorRegistry registry, BytemanRule instruction,
            IResource resource) {
        String name = instruction.getName();
        if (StringUtils.isBlank(name)) {
            createMarker(resource, Messages.BytemanRuleNoNameErrorMessage, instruction.getLine(), IMarker.SEVERITY_ERROR);
        }
        for (BytemanInstruction subInstruction : instruction.getInstructions()) {
            BytemanInstructionValidator<BytemanInstruction> validator = (BytemanInstructionValidator<BytemanInstruction>) registry.getValidator(subInstruction.getType());
            if (validator != null) {
                validator.validate(registry, subInstruction, resource);
            }
        }
    }
}
