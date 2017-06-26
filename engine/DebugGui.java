package engine;

import gui.GuiScene;

/**
 * Created by mjmcc on 10/20/2016.
 */

public class DebugGui extends GuiScene
{
/*	private static final String DEBUG_XML_PATH = EngineFiles.GUI_PATH + "debugGui.xml";
	private static final int HIDE_KEY = Keyboard.KEY_SPACE;

	private MoveTransition mt;
	private OpacityTransition op;

	private ToggleBoolean toggleClose;

	// Control Panel Inputs
	public ButtonComponent b0;
	public ButtonComponent b1;
	public ButtonComponent b2;

	public ButtonComponent t0;
	public ButtonComponent t1;
	public ButtonComponent t2;
	public ButtonComponent t3;
	public ButtonComponent t4;

	public VSliderComponent s0;
	public VSliderComponent s1;
	public VSliderComponent s2;
	public VSliderComponent s3;

	// Output labels
	public LabelComponent l0;
	public LabelComponent l1;
	public LabelComponent l2;

	public DebugGui()
	{
		super(DEBUG_XML_PATH);
		this.show();
		this.build();

		b0 = (ButtonComponent) this.getChildById("b0");
		b1 = (ButtonComponent) this.getChildById("b1");
		b2 = (ButtonComponent) this.getChildById("b2");

		t0 = (ButtonComponent) this.getChildById("t0");
		t1 = (ButtonComponent) this.getChildById("t1");
		t2 = (ButtonComponent) this.getChildById("t2");
		t3 = (ButtonComponent) this.getChildById("t3");
		t4 = (ButtonComponent) this.getChildById("t4");

		s0 = (VSliderComponent) this.getChildById("s0");
		s1 = (VSliderComponent) this.getChildById("s1");
		s2 = (VSliderComponent) this.getChildById("s2");
		s3 = (VSliderComponent) this.getChildById("s3");

		l0 = (LabelComponent) this.getChildById("l0");
		l1 = (LabelComponent) this.getChildById("l1");
		l2 = (LabelComponent) this.getChildById("l2");

		mt = new MoveTransition(new Vector2f(-1.5f, 0), .05f);
		op = new OpacityTransition(0, .1f);
		toggleClose = new ToggleBoolean(false);

		this.build();
	}

	public void tick()
	{
		super.tick();
		applyTransition(mt);
		applyTransition(op);

		if (Engine.getInputManager().isKeyClicked(HIDE_KEY))
			toggleClose.toggle();

		if (toggleClose.turnedFalse())
			close();
		if (toggleClose.turnedTrue())
			open();
	}

	public void close()
	{
		mt.setToNewPosition(new Vector2f(-1.5f, 0));
		op.setNewOpacity(0);
	}

	public void open()
	{
		mt.setToNewPosition(new Vector2f(-1.0f + getRoot().absoluteSize.x() / 2f, 0));
		op.setNewOpacity(1);
	}

	public void setVariableLabel(LabelComponent l, String name, String value)
	{
		l.setText(name + " : " + value);
		build();
	}

	public boolean isOpen()
	{
		return toggleClose.isTrue();
	}*/
}
