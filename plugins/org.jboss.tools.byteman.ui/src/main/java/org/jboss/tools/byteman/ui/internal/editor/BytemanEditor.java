package org.jboss.tools.byteman.ui.internal.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;

public class BytemanEditor extends TextEditor {

	private BytemanColorManager colorManager;

	private BytemanValidator validator;
	
	public BytemanEditor() {
		super();
		colorManager = new BytemanColorManager();
		setSourceViewerConfiguration(new BytemanSourceViewerConfiguration(colorManager));
		setDocumentProvider(new BytemanDocumentProvider());
	}

    @Override
    protected void doSetInput(IEditorInput input) throws CoreException {
        super.doSetInput(input);
        if (input instanceof IFileEditorInput) {
            validator = new BytemanValidator(getDocumentProvider().getDocument(input), ((IFileEditorInput)input).getFile());
            validator.askForValidation();
        }
    }
    
    @Override
    protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
        ISourceViewer sourceViewer = super.createSourceViewer(parent, ruler, styles);
        sourceViewer.addTextListener(this::onTextChanged);
        return sourceViewer;
    }

    private void onTextChanged(TextEvent event) {
        if ((event.getDocumentEvent() != null) && (validator != null)) {
            validator.askForValidation();
        }
    }

    @Override
	public void dispose() {
		colorManager.dispose();
		getSourceViewer().removeTextListener(this::onTextChanged);
		super.dispose();
	}

}
