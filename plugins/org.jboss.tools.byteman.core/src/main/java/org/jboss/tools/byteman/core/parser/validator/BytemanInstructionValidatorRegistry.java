/**
 * 
 */
package org.jboss.tools.byteman.core.parser.validator;

import org.jboss.tools.byteman.core.parser.BytemanInstruction;
import org.jboss.tools.byteman.core.parser.BytemanInstructionType;

/**
 * @author Jeff MAURY
 *
 */
public interface BytemanInstructionValidatorRegistry {
    BytemanInstructionValidator<? extends BytemanInstruction> getValidator(BytemanInstructionType type);

}
