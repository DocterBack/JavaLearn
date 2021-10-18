package swt_demo;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class MyShellClass {

	private Shell sShell = null;
	private Label textLabel = null;
	private Button myButton = null;
	
	private int  buttonPressedN = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls. 
		 * The dlls are located in the SWT plugin jar.  
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		MyShellClass thisClass = new MyShellClass();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		RowData rowData1 = new org.eclipse.swt.layout.RowData();
		rowData1.height = 30;
		rowData1.width = 200;
		RowData rowData = new org.eclipse.swt.layout.RowData();
		rowData.height = 30;
		rowData.width = 100;
		sShell = new Shell();
		sShell.setText("Это shell\\");
		sShell.setLocation(new Point(110, 185));
		sShell.setLayout(new RowLayout());
		sShell.setSize(new Point(300, 200));
		textLabel = new Label(sShell, SWT.CENTER | SWT.BORDER);
		textLabel.setText("Это текст");
		textLabel.setLayoutData(rowData1);
		myButton = new Button(sShell, SWT.NONE);
		myButton.setText("Это кнопка");
		myButton.setLayoutData(rowData);
		myButton.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					buttonPressedN++;
					textLabel.setText("Кнопка нажата " + buttonPressedN + " раз(а)."); 
				}
				System.out.println("keyPressed()");
			}
		});
		myButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				buttonPressedN++;
				textLabel.setText("Кнопка нажата " + buttonPressedN + " раз(а).");
				System.out.println("widgetSelected()");
			}
		});
	}

}
