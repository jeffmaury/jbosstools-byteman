package org.jboss.tools.byteman.ui.internal.editor;

import org.eclipse.jface.text.rules.*;

public class BytemanPartitionScanner extends RuleBasedPartitionScanner {
	public final static String BYTEMAN_RULE = "__byteman_rule";
	public final static String BYTEMAN_ENDRULE = "__byteman_endrule";
	public final static String BYTEMAN_COMMENT = "__byteman_comment";
	public final static String BYTEMAN_CLASS = "__byteman_class";
	public final static String BYTEMAN_INTERFACE = "__byteman_interface";
	public final static String BYTEMAN_METHOD = "__byteman_method";
	public final static String BYTEMAN_AT = "__byteman_at";
	public final static String BYTEMAN_BIND = "__byteman_bind";
	public final static String BYTEMAN_IF = "__byteman_if";
	public final static String BYTEMAN_DO = "__byteman_do";
	public final static String BYTEMAN_AFTER = "__byteman_after";
	public final static String BYTEMAN_HELPER = "__byteman_helper";
	public final static String BYTEMAN_RETURN = "__byteman_return";

    public static String[] partitions() {
        return new String[] { BYTEMAN_RULE,
                              BYTEMAN_ENDRULE,
                              BYTEMAN_COMMENT,
                              BYTEMAN_CLASS,
                              BYTEMAN_INTERFACE,
                              BYTEMAN_METHOD,
                              BYTEMAN_AT,
                              BYTEMAN_BIND,
                              BYTEMAN_IF,
                              BYTEMAN_DO,
                              BYTEMAN_AFTER,
                              BYTEMAN_HELPER,
                              BYTEMAN_RETURN
        };
    }

    public BytemanPartitionScanner() {

		IPredicateRule[] rules = new IPredicateRule[13];

		rules[0] = new SingleLineRule("RULE ", null, new Token(BYTEMAN_RULE));
		rules[1] = new SingleLineRule("ENDRULE", null, new Token(BYTEMAN_ENDRULE));
		rules[2] = new SingleLineRule("#", null, new Token(BYTEMAN_COMMENT));
		rules[3] = new SingleLineRule("CLASS", null, new Token(BYTEMAN_CLASS));
		rules[4] = new SingleLineRule("INTERFACE", null, new Token(BYTEMAN_INTERFACE));
		rules[5] = new SingleLineRule("METHOD", null, new Token(BYTEMAN_METHOD));
		rules[6] = new SingleLineRule("AT", null, new Token(BYTEMAN_AT));
		rules[7] = new SingleLineRule("BIND", null, new Token(BYTEMAN_BIND));
		rules[8] = new SingleLineRule("IF", null, new Token(BYTEMAN_IF));
		rules[9] = new SingleLineRule("DO", null, new Token(BYTEMAN_DO));
		rules[10] = new SingleLineRule("AFTER", null, new Token(BYTEMAN_AFTER));
		rules[11] = new SingleLineRule("HELPER", null, new Token(BYTEMAN_HELPER));
		rules[12] = new SingleLineRule("RETURN", null, new Token(BYTEMAN_RETURN));
		setPredicateRules(rules);
	}

}
