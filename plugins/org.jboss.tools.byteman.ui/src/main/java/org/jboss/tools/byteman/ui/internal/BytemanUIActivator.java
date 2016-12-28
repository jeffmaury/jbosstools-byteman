/**
 * 
 */
package org.jboss.tools.byteman.ui.internal;

import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanUIActivator extends AbstractUIPlugin {

    private static BytemanUIActivator plugin;

    public static BytemanUIActivator getDefault() {
        return plugin;
    }
    
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }
    
    public static void log(int status, String message) {
        log(status, message, null);
    }

    public static void log(int status, String message, Throwable e) {
        BytemanUIActivator instance = getDefault();
        instance.getLog().log(new Status(status, instance.getBundle().getSymbolicName(), message, e));
    }
}
