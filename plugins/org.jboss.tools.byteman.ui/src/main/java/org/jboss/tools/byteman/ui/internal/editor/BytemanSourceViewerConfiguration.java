package org.jboss.tools.byteman.ui.internal.editor;

import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class BytemanSourceViewerConfiguration extends SourceViewerConfiguration {
	private BytemanDoubleClickStrategy doubleClickStrategy;
	private BytemanScanner ruleScanner;
	private BytemanColorManager colorManager;

	public BytemanSourceViewerConfiguration(BytemanColorManager colorManager) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return BytemanPartitionScanner.partitions();
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new BytemanDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected BytemanScanner getRuleScanner() {
		if (ruleScanner == null) {
			ruleScanner = new BytemanScanner(colorManager);
			ruleScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IBytemanColorConstants.DEFAULT))));
		}
		return ruleScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getRuleScanner());
		reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_RULE);
		reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_RULE);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_ENDRULE);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_ENDRULE);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_CLASS);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_CLASS);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_INTERFACE);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_INTERFACE);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_METHOD);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_METHOD);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_AT);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_AT);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_BIND);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_BIND);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_IF);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_IF);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_DO);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_DO);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_HELPER);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_HELPER);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_AFTER);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_AFTER);
        reconciler.setDamager(dr, BytemanPartitionScanner.BYTEMAN_RETURN);
        reconciler.setRepairer(dr, BytemanPartitionScanner.BYTEMAN_RETURN);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(colorManager.getColor(IBytemanColorConstants.COMMENT)));
		reconciler.setDamager(ndr, BytemanPartitionScanner.BYTEMAN_COMMENT);
		reconciler.setRepairer(ndr, BytemanPartitionScanner.BYTEMAN_COMMENT);

		return reconciler;
	}

    @Override
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return new DefaultAnnotationHover(true);
    }

}