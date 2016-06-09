package tr.org.liderahenk.biosupdater.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import tr.org.liderahenk.biosupdater.utils.PropertyNames;
import tr.org.liderahenk.liderconsole.core.constants.LiderConstants;
import tr.org.liderahenk.liderconsole.core.dialogs.DefaultTaskDialog;
import tr.org.liderahenk.liderconsole.core.ldap.enums.DNType;
import tr.org.liderahenk.liderconsole.core.rest.requests.TaskRequest;
import tr.org.liderahenk.liderconsole.core.rest.responses.IResponse;
import tr.org.liderahenk.liderconsole.core.rest.utils.TaskUtils;
import tr.org.liderahenk.liderconsole.core.utils.SWTResourceManager;
import tr.org.liderahenk.liderconsole.core.widgets.Notifier;

/**
 * Task execution dialog for bios-updater plugin.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 * 
 */
public class BiosUpdaterTaskDialog extends DefaultTaskDialog {

	private static final Logger logger = LoggerFactory.getLogger(BiosUpdaterTaskDialog.class);

	private Text txtBiosVendor;
	private Text txtBiosVersion;
	private Text txtBiosReleaseDate;
	private Text txtBoardManufacturer;
	private Text txtBoardProductName;
	private Text txtBoardVersion;
	private Text txtBoardSerialNumber;
	private Text txtBoardAssetTag;
	private String dn;

	public BiosUpdaterTaskDialog(Shell parentShell, String dn) {
		super(parentShell, dn);
		this.dn = dn;
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

		Label lblBiosVendor = new Label(innerComp, SWT.NONE);
		lblBiosVendor.setText(Messages.getString("BIOS_VENDOR"));

		txtBiosVendor = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBiosVendor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBiosVersion = new Label(innerComp, SWT.NONE);
		lblBiosVersion.setText(Messages.getString("BIOS_VERSION"));

		txtBiosVersion = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBiosVersion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBiosReleaseDate = new Label(innerComp, SWT.NONE);
		lblBiosReleaseDate.setText(Messages.getString("BIOS_RELEASE_DATE"));

		txtBiosReleaseDate = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBiosReleaseDate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBoardManufacturer = new Label(innerComp, SWT.NONE);
		lblBoardManufacturer.setText(Messages.getString("BASEBOARD_MANUFACTURER"));

		txtBoardManufacturer = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBoardManufacturer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBoardProductName = new Label(innerComp, SWT.NONE);
		lblBoardProductName.setText(Messages.getString("BASEBOARD_PRODUCT_NAME"));

		txtBoardProductName = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBoardProductName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBoardVersion = new Label(innerComp, SWT.NONE);
		lblBoardVersion.setText(Messages.getString("BASEBOARD_VERSION"));

		txtBoardVersion = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBoardVersion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBoardSerialNumber = new Label(innerComp, SWT.NONE);
		lblBoardSerialNumber.setText(Messages.getString("BASEBOARD_SERIAL_NUMBER"));

		txtBoardSerialNumber = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBoardSerialNumber.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label lblBoardAssetTag = new Label(innerComp, SWT.NONE);
		lblBoardAssetTag.setText(Messages.getString("BASEBOARD_ASSET_TAG"));

		txtBoardAssetTag = new Text(innerComp, SWT.BORDER | SWT.READ_ONLY);
		txtBoardAssetTag.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		new Label(innerComp, SWT.NONE);
		Button btnGetBiosInfo = new Button(innerComp, SWT.PUSH);
		btnGetBiosInfo.setText(Messages.getString("GET_BIOS_INFO"));
		btnGetBiosInfo.setImage(
				SWTResourceManager.getImage(LiderConstants.PLUGIN_IDS.LIDER_CONSOLE_CORE, "icons/16/refresh.png"));
		btnGetBiosInfo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				readBiosInfo();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		btnGetBiosInfo.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

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

		readBiosInfo();
		return composite;
	}

	private void readBiosInfo() {
		IResponse response = null;
		try {
			TaskRequest task = new TaskRequest();
			task.setCommandId("READ_BIOS_INFO");
			ArrayList<String> dnList = new ArrayList<String>();
			dnList.add(this.dn);
			task.setDnList(dnList);
			task.setDnType(DNType.AHENK);
			task.setPluginName("bios-updater");
			task.setPluginVersion("1.0.0");
			task.setParameterMap(new HashMap<String, Object>());
			response = TaskUtils.execute(task);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Notifier.error(null, Messages.getString("ERROR_ON_LIST"));
		}
		if (response != null) {
			Map<String, Object> data = response.getResultMap();
			if (data != null && !data.isEmpty()) {
				Object val = data.get(PropertyNames.BIOS_VENDOR);
				txtBiosVendor.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BIOS_VERSION);
				txtBiosVersion.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BIOS_RELEASE_DATE);
				txtBiosReleaseDate.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BOARD_MANUFACTURER);
				txtBoardManufacturer.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BOARD_PRODUCT_NAME);
				txtBoardProductName.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BOARD_VERSION);
				txtBoardVersion.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BOARD_SERIAL_NUMBER);
				txtBoardSerialNumber.setText(val != null ? val.toString() : "");
				val = data.get(PropertyNames.BOARD_ASSET_TAG);
				txtBoardAssetTag.setText(val != null ? val.toString() : "");
			}
		}
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
