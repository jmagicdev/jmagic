package org.rnd.jmagic.interfaceAdapters;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;
import org.rnd.util.*;

/**
 * An implementation of PlayerInterface intended to sit between interfaces.
 * Child classes will examine, manipulate, and/or automatically respond to
 * certain queries.
 */
public abstract class SimpleConfigurableInterface implements ConfigurableInterface
{
	private final java.util.SortedSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel> options;
	private final ConfigurableInterface adapt;

	public SimpleConfigurableInterface(ConfigurableInterface adapt)
	{
		this.adapt = adapt;
		this.options = adapt.getOptions();
		org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel optionPanel = this.getOptionPanel();
		if(optionPanel != null)
			this.options.add(optionPanel);
	}

	@Override
	public void alertChoice(int playerID, ChooseParameters<?> choice)
	{
		this.adapt.alertChoice(playerID, choice);
	}

	@Override
	public void alertError(ErrorParameters parameters)
	{
		this.adapt.alertError(parameters);
	}

	@Override
	public void alertEvent(SanitizedEvent event)
	{
		this.adapt.alertEvent(event);
	}

	@Override
	public void alertState(SanitizedGameState sanitizedGameState)
	{
		this.adapt.alertState(sanitizedGameState);
	}

	@Override
	public void alertStateReversion(PlayerInterface.ReversionParameters parameters)
	{
		this.adapt.alertStateReversion(parameters);
	}

	@Override
	public void alertWaiting(SanitizedPlayer who)
	{
		this.adapt.alertWaiting(who);
	}

	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		return this.adapt.choose(parameterObject);
	}

	@Override
	public int chooseNumber(NumberRange range, String description)
	{
		return this.adapt.chooseNumber(range, description);
	}

	@Override
	public void divide(int quantity, int minimum, int whatFrom, String beingDivided, java.util.List<SanitizedTarget> targets)
	{
		this.adapt.divide(quantity, minimum, whatFrom, beingDivided, targets);
	}

	@Override
	public Deck getDeck()
	{
		return this.adapt.getDeck();
	}

	@Override
	public org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel getOptionPanel()
	{
		return null;
	}

	@Override
	public java.util.SortedSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel> getOptions()
	{
		return this.options;
	}

	@Override
	public String getName()
	{
		return this.adapt.getName();
	}

	@Override
	public void setPlayerID(int playerID)
	{
		this.adapt.setPlayerID(playerID);
	}

	@Override
	public void setProperties(java.util.Properties properties)
	{
		this.adapt.setProperties(properties);
	}
}
