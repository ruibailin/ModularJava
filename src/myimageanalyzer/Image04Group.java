package myimageanalyzer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class Image04Group extends Image03Menu {

	// Add a composite to contain some control widgets across the top.
	Composite groupPanel;
			
	Group backgroundGroup, saveGroup;
	Group scaleXGroup,scaleYGroup, alphaGroup;
	Group displayGroup,animateGroup;
	int maxGroupHigh = 0;
	
	public Image04Group() {

	}
	/*************************************************************/	
	//Static functions area
	
	/*************************************************************/
	//Override functions area
	
	/*************************************************************/
	//Framework
	
	
	/*************************************************************/
	//Private functions area
	private void iniShellLayout(){
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 5;
		layout.numColumns = 2;
		shell.setLayout(layout);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		shell.setLayoutData(gridData);
	}

	private void iniGroupPanel(){
		groupPanel = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.marginTop = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 8;
		rowLayout.fill = true;	//same height
		groupPanel.setLayout(rowLayout);
		GridData rowData = new GridData();
		rowData.horizontalSpan = 2;
		groupPanel.setLayoutData(rowData);

	}
		
	/*************************************************************/
	private void iniBackgroundGroup(){
		// Combo to change the background.
		backgroundGroup = new Group(groupPanel, SWT.NONE);
		backgroundGroup.setLayout(new RowLayout());
		backgroundGroup.setText(bundle.getString("Background"));
		
		Rectangle size = backgroundGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Background H is " + size.height + "\n");
	}
	private void iniSaveGroup(){
		// Combo to change the compression ratio.
		saveGroup = new Group(groupPanel, SWT.NONE);
		saveGroup.setLayout(new GridLayout(3, true));
		saveGroup.setText(bundle.getString("Save_group"));
		
		Rectangle size = saveGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Save H is " + size.height + "\n");
	}
	private void iniScaleXGroup(){
		// Combo to change the x scale.
		scaleXGroup = new Group(groupPanel, SWT.NONE);
		scaleXGroup.setLayout(new RowLayout());
		scaleXGroup.setText(bundle.getString("X_scale"));
		
		Rectangle size = scaleXGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Scale X H is" + size.height + "\n");
	}
	private void iniScaleYGroup(){
		// Combo to change the y scale.
		scaleYGroup = new Group(groupPanel, SWT.NONE);
		scaleYGroup.setLayout(new RowLayout());
		scaleYGroup.setText(bundle.getString("Y_scale"));
		
		Rectangle size = scaleYGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Scale Y H" + size.height + "\n");
	}
	private void iniAlphaGroup(){
		// Combo to change the alpha value.
		alphaGroup = new Group(groupPanel, SWT.NONE);
		alphaGroup.setLayout(new RowLayout());
		alphaGroup.setText(bundle.getString("Alpha_K"));
		
		Rectangle size = alphaGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Alpha H is " + size.height + "\n");
	}
	private void iniDisplayGroup(){
		// Check box to request incremental display.
		displayGroup = new Group(groupPanel, SWT.NONE);
		displayGroup.setLayout(new RowLayout());
		displayGroup.setText(bundle.getString("Display"));
		
		Rectangle size = displayGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Display H is " + size.height + "\n");
	}
	private void iniAnimateGroup(){
		// Group the animation buttons.
		animateGroup = new Group(groupPanel, SWT.NONE);
		animateGroup.setLayout(new RowLayout());
		animateGroup.setText(bundle.getString("Animation"));
		
		Rectangle size = animateGroup.getClientArea();
		maxGroupHigh = (size.height>maxGroupHigh)?size.height:maxGroupHigh;
		System.out.print("Animate H is " + size.height + "\n");
	}
	
	/*************************************************************/
	private void iniGroupItem(){
		iniBackgroundGroup();
		iniSaveGroup();
		iniScaleXGroup();
		iniScaleYGroup();
		iniAlphaGroup();
		iniDisplayGroup();
		iniAnimateGroup();
	}
	
	/*************************************************************/
	//functions called by main function
	public void initializeGroup(){
		iniShellLayout();
		iniGroupPanel();
		iniGroupItem();
	}
}
