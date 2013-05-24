package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Liliana's Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LilianasShade extends Card
{
	public static final class LilianasShadeAbility0 extends EventTriggeredAbility
	{
		public LilianasShadeAbility0(GameState state)
		{
			super(state, "When Liliana's Shade enters the battlefield, you may search your library for a Swamp card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Swamp card, reveal it, put it into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.SWAMP)));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(youMay(search));
		}
	}

	public LilianasShade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Liliana's Shade enters the battlefield, you may search your
		// library for a Swamp card, reveal it, put it into your hand, then
		// shuffle your library.
		this.addAbility(new LilianasShadeAbility0(state));

		// (B): Liliana's Shade gets +1/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.ShadePump(state, this.getName()));
	}
}
