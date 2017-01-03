/**
 * 
 */
package org.jboss.tools.byteman.core.parser.validator;

import java.util.HashMap;
import java.util.Map;

import org.jboss.tools.byteman.core.Messages;
import org.jboss.tools.byteman.core.parser.BytemanInstructionType;

/**
 * @author Jeff MAURY
 *
 */
public class DefaultBytemanInstructionValidatorRegistry implements BytemanInstructionValidatorRegistry {

    private static final BytemanInstructionValidatorRegistry INSTANCE = new DefaultBytemanInstructionValidatorRegistry();
    
    public static BytemanInstructionValidatorRegistry get() {
        return INSTANCE;
    }
    
    private Map<BytemanInstructionType, BytemanInstructionValidator> validators = new HashMap<>();
    
    private DefaultBytemanInstructionValidatorRegistry() {
        validators.put(BytemanInstructionType.RULE, new BytemanRuleInstructionValidator());
        validators.put(BytemanInstructionType.CLASS, new BytemanNamedInstructionValidator(Messages.BytemanClassNoNameErrorMessage));
        validators.put(BytemanInstructionType.INTERFACE, new BytemanNamedInstructionValidator(Messages.BytemanInterfaceNoNameErrorMessage));
        validators.put(BytemanInstructionType.HELPER, new BytemanNamedInstructionValidator(Messages.BytemanHelperNoNameErrorMessage));
        validators.put(BytemanInstructionType.METHOD, new BytemanNamedInstructionValidator(Messages.BytemanMethodNoNameErrorMessage));
        validators.put(BytemanInstructionType.IMPORT, new BytemanNamedInstructionValidator(Messages.BytemanImportNoNameErrorMessage));
        validators.put(BytemanInstructionType.BIND, new BytemanBindInstructionValidator());
    }
    
    @Override
    public BytemanInstructionValidator getValidator(BytemanInstructionType type) {
        return validators.get(type);
    }

}
