/**
 * 
 */
package org.jboss.tools.byteman.ui.tabs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;
import org.jboss.tools.byteman.core.BytemanCoreConstants;
import org.jboss.tools.byteman.ui.internal.BytemanUIActivator;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

    private class PredicateSelectionAdapter extends SelectionAdapter {
        private Predicate<SelectionEvent> predicate;

        private PredicateSelectionAdapter(Predicate<SelectionEvent> consumer) {
            this.predicate = consumer;
        }

        private PredicateSelectionAdapter() {
            this(e -> true);
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            if (predicate.test(e)) {
                setDirty(true);
                updateLaunchConfigurationDialog();
            }
        }
    }
    
    private Button enableByteman;
    
    private Text scriptText;
    
    @Override
    public void createControl(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().applyTo(comp);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);
        enableByteman = createCheckButton(comp, "Enable Byteman");
        enableByteman.addSelectionListener(new PredicateSelectionAdapter());
        createSourceControls(comp);
        setControl(comp);
        
    }

    private void createSourceControls(Composite parent) {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Script");
        GridLayoutFactory.fillDefaults().numColumns(5).applyTo(group);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(group);
        Label label = new Label(group, SWT.NONE);
        label.setText("Byteman script");
        scriptText = new Text(group, SWT.BORDER);
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(scriptText);
        Button browseFileSystem = createPushButton(group, "File system...", null);
        browseFileSystem.addSelectionListener(new PredicateSelectionAdapter(e -> {
            boolean result = false;

            FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
            String filePath = getAsFileName(scriptText.getText());
            if (filePath != null) {
                dialog.setFileName(filePath);
            }
            filePath = dialog.open();
            if (filePath != null) {
                File f = new File(filePath);
                scriptText.setText(f.toURI().toString());
                result = true;
            }
            return result;
        }));
        Button browseWorkspace = createPushButton(group, "Workspace...", null);
        browseWorkspace.addSelectionListener(new PredicateSelectionAdapter(e -> {
            boolean result = false;

            ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(),
                    new WorkbenchContentProvider());
            dialog.setTitle("Selection workspace resource");
            dialog.setMessage("Selection workspace resource");
            dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
            dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
            // dialog.setDialogBoundsSettings(getDialogBoundsSettings(""),
            // Dialog.DIALOG_PERSISTSIZE);
            if (dialog.open() == IDialogConstants.OK_ID) {
                IResource resource = (IResource) dialog.getFirstResult();
                if (resource != null) {
                    URI arg = URI.createPlatformResourceURI(resource.getFullPath().toOSString(), true);
                    scriptText.setText(arg.toString());
                    result = true;
                }
            }
            return result;
        }));
    }

    private String getAsFileName(String text) {
        String result = null;
        
        if (!text.isEmpty()) {
          try {
              URL url = new URL(text);
              if (url.getProtocol().equals("file")) {
                  result = url.getFile();
              }
          } catch (MalformedURLException e) {
              
          }
        }
        return result;
    }
    

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            String bytemanLaunchScript = configuration.getAttribute(BytemanCoreConstants.BYTEMAN_LAUNCH_SCRIPT_ATTRIBUTE, (String)null);
            enableByteman.setSelection(bytemanLaunchScript != null);
            if (bytemanLaunchScript != null) {
                scriptText.setText(bytemanLaunchScript);
            }
        } catch (CoreException e) {
            BytemanUIActivator.log(IStatus.ERROR, e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        if (enableByteman.getSelection()) {
            configuration.setAttribute(BytemanCoreConstants.BYTEMAN_LAUNCH_SCRIPT_ATTRIBUTE, scriptText.getText());
        } else {
            configuration.removeAttribute(BytemanCoreConstants.BYTEMAN_LAUNCH_SCRIPT_ATTRIBUTE);
        }
    }

    @Override
    public String getName() {
        return "Byteman";
    }

}
