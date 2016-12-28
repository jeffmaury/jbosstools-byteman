package org.jboss.tools.byteman.ui.internal.editor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

public class BytemanScanner extends RuleBasedScanner {

	public BytemanScanner(BytemanColorManager manager) {
		IToken rule = new Token(new TextAttribute(manager.getColor(IBytemanColorConstants.KEYWORD)));

		IRule[] rules = new IRule[19];

		// Add rule for double quotes
		rules[0] = new StringRule("RULE", rule);
		rules[1] = new StringRule("ENDRULE", rule);
		rules[2] = new StringRule("CLASS", rule);
		rules[3] = new StringRule("INTERFACE", rule);
		rules[4] = new StringRule("METHOD", rule);
		rules[5] = new StringRule("AT", rule);
		rules[6] = new StringRule("ENTRY", rule);
		rules[7] = new StringRule("EXIT", rule);
		rules[8] = new StringRule("LINE", rule);
		rules[9] = new StringRule("READ", rule);
		rules[10] = new StringRule("ALL", rule);
        rules[11] = new StringRule("WRITE", rule);
        rules[12] = new StringRule("AFTER", rule);
		rules[13] = new StringRule("IF", rule);
		rules[14] = new StringRule("NOT", rule);
		rules[15] = new StringRule("DO", rule);
		rules[16] = new StringRule("HELPER", rule);
		rules[17] = new StringRule("BIND", rule);
		rules[18] = new StringRule("RETURN", rule);
		setRules(rules);
	}
}
