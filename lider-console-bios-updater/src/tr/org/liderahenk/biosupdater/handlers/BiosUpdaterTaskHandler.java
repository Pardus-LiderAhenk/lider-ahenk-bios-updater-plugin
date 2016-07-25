package tr.org.liderahenk.biosupdater.handlers;

import org.eclipse.swt.widgets.Display;

import tr.org.liderahenk.biosupdater.dialogs.BiosUpdaterTaskDialog;
import tr.org.liderahenk.liderconsole.core.handlers.SingleSelectionHandler;

public class BiosUpdaterTaskHandler extends SingleSelectionHandler {

	@Override
	public void executeWithDn(String dn) {
		BiosUpdaterTaskDialog dialog = new BiosUpdaterTaskDialog(Display.getDefault().getActiveShell(), dn);
		dialog.create();
		dialog.openWithEventBroker();
	}

}
