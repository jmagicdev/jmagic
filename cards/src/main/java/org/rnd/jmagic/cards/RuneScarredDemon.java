package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rune-Scarred Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class RuneScarredDemon extends Card
{
	public static final class RuneScarredDemonAbility1 extends EventTriggeredAbility
	{
		public RuneScarredDemonAbility1(GameState state)
		{
			super(state, "When Rune-Scarred Demon enters the battlefield, search your library for a card, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for a card and put that card into your hand. Then shuffle your library."));
		}
	}

	public RuneScarredDemon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Rune-Scarred Demon enters the battlefield, search your library
		// for a card, put it into your hand, then shuffle your library.
		this.addAbility(new RuneScarredDemonAbility1(state));
	}
}
