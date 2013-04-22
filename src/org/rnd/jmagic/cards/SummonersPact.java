package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Summoner's Pact")
@ManaCost("0")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SummonersPact extends org.rnd.jmagic.cardTemplates.Pact
{
	public SummonersPact(GameState state)
	{
		super(state);
	}

	@Override
	public void addEffects()
	{
		EventType.ParameterMap searchParameters = new EventType.ParameterMap();
		searchParameters.put(EventType.Parameter.CAUSE, This.instance());
		searchParameters.put(EventType.Parameter.PLAYER, You.instance());
		searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		searchParameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasColor.instance(Color.GREEN), HasType.instance(Type.CREATURE))));
		searchParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a green creature card, reveal it, and put it into your hand. Then shuffle your library."));
	}

	@Override
	public Color getColor()
	{
		return Color.GREEN;
	}

	@Override
	public String getUpkeepCost()
	{
		return "(2)(G)(G)";
	}
}
