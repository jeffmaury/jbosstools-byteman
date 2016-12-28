package org.jboss.tools.byteman.core;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.jboss.tools.byteman.core.messages"; //$NON-NLS-1$
    public static String BytemanRuleNoNameErrorMessage;
    public static String BytemanClassNoNameErrorMessage;
    public static String BytemanInterfaceNoNameErrorMessage;
    public static String BytemanMethodNoNameErrorMessage;
    public static String BytemanHelperNoNameErrorMessage;
    public static String BytemanImportNoNameErrorMessage;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
