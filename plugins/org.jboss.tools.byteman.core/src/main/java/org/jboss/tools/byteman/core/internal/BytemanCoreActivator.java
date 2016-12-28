/**
 * 
 */
package org.jboss.tools.byteman.core.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanCoreActivator extends Plugin {

    private static BytemanCoreActivator PLUGIN;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        PLUGIN = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        PLUGIN = null;
        super.stop(context);
    }
    
    public static void log(int status, String message) {
        log(status, message, null);
    }

    public static void log(int status, String message, Throwable e) {
        PLUGIN.getLog().log(new Status(status, PLUGIN.getBundle().getSymbolicName(), message, e));
    }
    
    
}
