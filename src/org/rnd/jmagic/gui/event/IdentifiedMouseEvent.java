package org.rnd.jmagic.gui.event;

import org.rnd.jmagic.sanitized.*;

public class IdentifiedMouseEvent extends GuiEvent
{
	public static final Object TYPE = "IdentifiedMouseEvent";

	private SanitizedIdentified identified;
	private java.awt.event.MouseEvent mouseEvent;

	public IdentifiedMouseEvent(SanitizedIdentified identified, java.awt.event.MouseEvent click)
	{
		super(TYPE);
		this.identified = identified;
		this.mouseEvent = click;
	}

	public SanitizedIdentified getIdentified()
	{
		return this.identified;
	}

	public java.awt.event.MouseEvent getMouseEvent()
	{
		return this.mouseEvent;
	}
}
