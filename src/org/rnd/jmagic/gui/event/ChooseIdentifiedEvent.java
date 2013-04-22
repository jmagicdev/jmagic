package org.rnd.jmagic.gui.event;

public class ChooseIdentifiedEvent extends GuiEvent
{
	public static final Object TYPE = "ChooseIdentifiedEvent";

	private IdentifiedMouseEvent identifiedMouseEvent;

	public ChooseIdentifiedEvent(IdentifiedMouseEvent identifiedMouseEvent)
	{
		super(TYPE);
		this.identifiedMouseEvent = identifiedMouseEvent;
	}

	public IdentifiedMouseEvent getIdentifiedMouseEvent()
	{
		return this.identifiedMouseEvent;
	}
}
