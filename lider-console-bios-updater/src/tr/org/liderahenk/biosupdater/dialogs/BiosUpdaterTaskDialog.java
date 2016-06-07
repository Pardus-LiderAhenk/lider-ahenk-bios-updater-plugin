package tr.org.liderahenk.biosupdater.dialogs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.biosupdater.constants.BiosUpdaterConstants;
import tr.org.liderahenk.biosupdater.i18n.Messages;
import tr.org.liderahenk.liderconsole.core.dialogs.DefaultTaskDialog;
import tr.org.liderahenk.liderconsole.core.utils.SWTResourceManager;

/**
 * Task execution dialog for bios-updater plugin.
 * 
 */
public class BiosUpdaterTaskDialog extends DefaultTaskDialog {

	private static final Logger logger = LoggerFactory.getLogger(BiosUpdaterTaskDialog.class);

	public BiosUpdaterTaskDialog(Shell parentShell, String dn) {
		super(parentShell, dn);
	}

	@Override
	public String createTitle() {
		return Messages.getString("BIOS_UPDATER");
	}

	@Override
	public Control createTaskDialogArea(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label lblBiosInfo = new Label(composite, SWT.BOLD);
		lblBiosInfo.setFont(SWTResourceManager.getFont("Sans", 9, SWT.BOLD));
		lblBiosInfo.setText(Messages.getString("BIOS_INFO"));

		Composite innerComp = new Composite(composite, SWT.NONE);
		innerComp.setLayout(new GridLayout(2, false));
		innerComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Text txtBiosInfo = new Text(innerComp, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.heightHint = 100;
		txtBiosInfo.setLayoutData(gridData);
		// populate bios info - new task!
		Button btnGetBiosInfo = new Button(innerComp, SWT.PUSH);
		btnGetBiosInfo.setText(Messages.getString("GET_BIOS_INFO"));
		// TODO

		Label lblBiosUpdate = new Label(composite, SWT.BOLD);
		lblBiosUpdate.setFont(SWTResourceManager.getFont("Sans", 9, SWT.BOLD));
		lblBiosUpdate.setText(Messages.getString("UPDATE_BIOS"));
		
		innerComp = new Composite(composite, SWT.NONE);
		innerComp.setLayout(new GridLayout(2, false));
		innerComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label lblUpdateUrl = new Label(innerComp, SWT.NONE);
		lblUpdateUrl.setText(Messages.getString("BIOS_UPDATE_URL"));
		
		Text txtUpdateUrl = new Text(innerComp, SWT.BORDER);
		txtUpdateUrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Button btnBackupExisting = new Button(innerComp, SWT.CHECK);
		btnBackupExisting.setText(Messages.getString("BACKUP_EXISTING"));
		new Label(innerComp, SWT.NONE);
		
		Button btnBackupOnRemote = new Button(innerComp, SWT.CHECK);
		btnBackupOnRemote.setText(Messages.getString("BACKUP_ON_REMOTE"));
		new Label(innerComp, SWT.NONE);
		
		
		
		return composite;
	}

	@Override
	public boolean validateBeforeExecution() {
		return true;
	}

	@Override
	public Map<String, Object> getParameterMap() {
		return new HashMap<String, Object>();
	}

	@Override
	public String getCommandId() {
		return "UPDATE_BIOS";
	}

	@Override
	public String getPluginName() {
		return BiosUpdaterConstants.PLUGIN_NAME;
	}

	@Override
	public String getPluginVersion() {
		return BiosUpdaterConstants.PLUGIN_VERSION;
	}

}
