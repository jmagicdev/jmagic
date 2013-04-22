package org.rnd.jmagic.gui.event;

import org.rnd.jmagic.sanitized.*;

public class ChooseActionEvent extends GuiEvent
{
	public static final Object TYPE = "ChooseActionEvent";

	private IdentifiedMouseEvent identifiedMouseEvent;
	private SanitizedPlayerAction action;

	public ChooseActionEvent(SanitizedPlayerAction action)
	{
		super(TYPE);
		this.identifiedMouseEvent = null;
		this.action = action;
	}

	public ChooseActionEvent(IdentifiedMouseEvent identifiedMouseEvent, SanitizedPlayerAction action)
	{
		super(TYPE);
		this.identifiedMouseEvent = identifiedMouseEvent;
		this.action = action;
	}

	public SanitizedPlayerAction getAction()
	{
		return this.action;
	}

	public IdentifiedMouseEvent getIdentifiedMouseEvent()
	{
		return this.identifiedMouseEvent;
	}
}
