package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Llanowar Sentinel")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LlanowarSentinel extends Card
{
	public static final class Mate extends EventTriggeredAbility
	{
		public Mate(GameState state)
		{
			super(state, "When Llanowar Sentinel enters the battlefield, you may pay (1)(G). If you do, search your library for a card named Llanowar Sentinel and put that card onto the battlefield. Then shuffle your library.");

			this.addPattern(whenThisEntersTheBattlefield());

			EventType.ParameterMap mayParameters = new EventType.ParameterMap();
			mayParameters.put(EventType.Parameter.CAUSE, This.instance());
			mayParameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("1G")));
			mayParameters.put(EventType.Parameter.PLAYER, You.instance());

			EventType.ParameterMap searchParameters = new EventType.ParameterMap();
			searchParameters.put(EventType.Parameter.CAUSE, This.instance());
			searchParameters.put(EventType.Parameter.CONTROLLER, You.instance());
			searchParameters.put(EventType.Parameter.PLAYER, You.instance());
			searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchParameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance("Llanowar Sentinel")));
			searchParameters.put(EventType.Parameter.TO, Battlefield.instance());

			EventType.ParameterMap ifParameters = new EventType.ParameterMap();
			ifParameters.put(EventType.Parameter.IF, Identity.instance(new EventFactory(EventType.PLAYER_MAY_PAY_MANA, mayParameters, "You may pay (1)(G).")));
			ifParameters.put(EventType.Parameter.THEN, Identity.instance(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a card named Llanowar Sentinel and put that card onto the battlefield. Then shuffle your library.")));
			this.addEffect(new EventFactory(EventType.IF_EVENT_THEN_ELSE, ifParameters, "You may pay (1)(G). If you do, search your library for a card named Llanowar Sentinel and put that card onto the battlefield. Then shuffle your library."));
		}
	}

	public LlanowarSentinel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new Mate(state));
	}
}
