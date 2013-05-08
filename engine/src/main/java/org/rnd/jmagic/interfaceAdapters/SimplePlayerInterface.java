package org.rnd.jmagic.interfaceAdapters;

import java.io.*;
import java.util.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;
import org.rnd.util.*;

public class SimplePlayerInterface implements PlayerInterface
{
	private final PlayerInterface adapt;

	public SimplePlayerInterface(PlayerInterface adapt)
	{
		this.adapt = adapt;
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
	public void alertStateReversion(ReversionParameters parameters)
	{
		this.adapt.alertStateReversion(parameters);
	}

	@Override
	public void alertWaiting(SanitizedPlayer who)
	{
		this.adapt.alertWaiting(who);
	}

	@Override
	public <T extends Serializable> List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		return this.adapt.choose(parameterObject);
	}

	@Override
	public int chooseNumber(NumberRange range, String description)
	{
		return this.adapt.chooseNumber(range, description);
	}

	@Override
	public void divide(int quantity, int minimum, int whatFrom, String beingDivided, List<SanitizedTarget> targets)
	{
		this.adapt.divide(quantity, minimum, whatFrom, beingDivided, targets);
	}

	@Override
	public Deck getDeck()
	{
		return this.adapt.getDeck();
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
}
