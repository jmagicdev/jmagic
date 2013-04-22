package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class HarbingerAbility extends EventTriggeredAbility
{
	private final String cardName;
	private final SubType[] whichType;

	private HarbingerAbility(GameState state, String cardName, String subTypeString, SubType... whichType)
	{
		super(state, "When " + cardName + " enters the battlefield, you may search your library for a " + subTypeString + " card, reveal it, then shuffle your library and put that card on top of it.");
		this.cardName = cardName;
		this.whichType = whichType;
		this.addPattern(whenThisEntersTheBattlefield());

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(whichType)));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, parameters, "Search your library for a " + subTypeString + " card, then shuffle your library and put that card on top of it."));
	}

	public HarbingerAbility(GameState state, String cardName, SubType... whichType)
	{
		this(state, cardName, org.rnd.util.SeparatedList.get(" ", "or", (Object[])whichType).toString(), whichType);
	}

	@Override
	public HarbingerAbility create(Game game)
	{
		return new HarbingerAbility(game.physicalState, this.cardName, this.whichType);
	}
}