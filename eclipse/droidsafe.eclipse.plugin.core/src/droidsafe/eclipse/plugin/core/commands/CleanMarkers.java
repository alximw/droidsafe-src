package droidsafe.eclipse.plugin.core.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import droidsafe.eclipse.plugin.core.util.DroidsafePluginUtilities;

public class CleanMarkers extends AbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CleanMarkers.class);

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    ISelection selection =
        HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
    if (selection != null & selection instanceof IStructuredSelection) {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;

      Object selectedObject = structuredSelection.getFirstElement();
      if (selectedObject instanceof IAdaptable) {
        IResource res = (IResource) ((IAdaptable) selectedObject).getAdapter(IResource.class);
        IProject project = res.getProject();
        if (project != null) {
          logger.debug("Project found: " + project.getName());
          DroidsafePluginUtilities.removeAllDroidsafeMarkers(project);
        }
      }
    }
    return null;

  }

}
