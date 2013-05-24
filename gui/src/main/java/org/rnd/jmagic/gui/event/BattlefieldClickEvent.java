package org.rnd.jmagic.gui.event;

public class BattlefieldClickEvent extends GuiEvent
{
	public static final Object TYPE = "BattlefieldClickEvent";

	private java.awt.event.MouseEvent click;

	public BattlefieldClickEvent(java.awt.event.MouseEvent click)
	{
		super(TYPE);
		this.click = click;
	}

	public java.awt.event.MouseEvent getClick()
	{
		return this.click;
	}
}
