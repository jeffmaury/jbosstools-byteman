package org.jboss.tools.byteman.ui.internal.editor;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.jboss.tools.byteman.core.BytemanCoreConstants;
import org.jboss.tools.byteman.core.parser.BytemanInstruction;
import org.jboss.tools.byteman.core.parser.RuleScriptParser;
import org.jboss.tools.byteman.core.parser.RulesScript;
import org.jboss.tools.byteman.core.parser.validator.BytemanInstructionValidator;
import org.jboss.tools.byteman.core.parser.validator.BytemanInstructionValidatorRegistry;
import org.jboss.tools.byteman.core.parser.validator.BytemanRuleInstructionValidator;
import org.jboss.tools.byteman.core.parser.validator.DefaultBytemanInstructionValidatorRegistry;
import org.jboss.tools.byteman.ui.internal.BytemanUIActivator;

public class BytemanValidator {

    private IDocument document;
    private IResource resource;
    private static final ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
    
    private static final BytemanInstructionValidatorRegistry registry = DefaultBytemanInstructionValidatorRegistry.get();
    
    private ScheduledFuture<?> scheduledFuture;

    public BytemanValidator(IDocument documentProvider, IResource resource) {
        this.document = documentProvider;
        this.resource = resource;
    }
    
    public void askForValidation() {
        if (scheduledFuture == null) {
            scheduledFuture = scheduledService.schedule(this::validate, 1, TimeUnit.SECONDS);
        }
    }
    
    private void validate() {
        scheduledFuture = null;
        System.out.println("Valdating");
        Reader reader = new StringReader(document.get());
        RuleScriptParser parser = new RuleScriptParser();
        try {
            resource.deleteMarkers(BytemanCoreConstants.MARKER_ID, false, IResource.DEPTH_ZERO);
            RulesScript rules = parser.processScripts(reader, resource.getName());
            for(BytemanInstruction instruction : rules.getInstructions()) {
                BytemanInstructionValidator<BytemanInstruction> validator = (BytemanInstructionValidator<BytemanInstruction>) registry.getValidator(instruction.getType());
                if (validator != null) {
                    validator.validate(registry, instruction, resource);
                }
            }
        } catch (IOException | CoreException e) {
            BytemanUIActivator.log(IStatus.ERROR, e.getLocalizedMessage(), e);
        }
    }
    
    public void dispose() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        }
    }
}
