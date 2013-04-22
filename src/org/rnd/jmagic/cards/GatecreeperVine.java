package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gatecreeper Vine")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GatecreeperVine extends Card
{
	public static final class GatecreeperVineAbility1 extends EventTriggeredAbility
	{
		public GatecreeperVineAbility1(GameState state)
		{
			super(state, "When Gatecreeper Vine enters the battlefield, you may search your library for a basic land card or a Gate card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card or a Gate card, reveal it, put it into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Union.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND)), HasSubType.instance(SubType.GATE))));
			this.addEffect(youMay(search, "You may search your library for a basic land card or a Gate card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public GatecreeperVine(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// When Gatecreeper Vine enters the battlefield, you may search your
		// library for a basic land card or a Gate card, reveal it, put it into
		// your hand, then shuffle your library.
		this.addAbility(new GatecreeperVineAbility1(state));
	}
}
