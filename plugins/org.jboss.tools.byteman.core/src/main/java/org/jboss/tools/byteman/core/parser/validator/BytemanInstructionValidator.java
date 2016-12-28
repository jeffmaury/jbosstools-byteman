/**
 * 
 */
package org.jboss.tools.byteman.core.parser.validator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.jboss.tools.byteman.core.internal.BytemanCoreActivator;
import org.jboss.tools.byteman.core.parser.BytemanInstruction;

import static org.jboss.tools.byteman.core.BytemanCoreConstants.MARKER_ID;
/**
 * @author Jeff MAURY
 *
 */
public interface BytemanInstructionValidator<T extends BytemanInstruction> {
    void validate(BytemanInstructionValidatorRegistry registry, T instruction, IResource resource);
    
    default void createMarker(IResource resource, String message, int line, int severity) {
        try {
            IMarker marker = resource.createMarker(MARKER_ID);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.LINE_NUMBER, line);
            marker.setAttribute(IMarker.SEVERITY, severity);
        } catch (CoreException e) {
            BytemanCoreActivator.log(IStatus.ERROR, e.getLocalizedMessage(), e);
        }
    }
    
    

}
