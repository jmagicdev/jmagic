package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Ringleader")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinRingleader extends Card
{
	public static final class GoblinRingleaderAbility1 extends EventTriggeredAbility
	{
		public GoblinRingleaderAbility1(GameState state)
		{
			super(state, "When Goblin Ringleader enters the battlefield, reveal the top four cards of your library. Put all Goblin cards revealed this way into your hand and the rest on the bottom of your library in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			EventFactory reveal = reveal(TopCards.instance(4, yourLibrary), "Reveal the top four cards of your library.");
			this.addEffect(reveal);

			SetGenerator revealed = EffectResult.instance(reveal);
			SetGenerator revealedGoblins = Intersect.instance(revealed, HasSubType.instance(SubType.GOBLIN));

			this.addEffect(putIntoHand(revealedGoblins, You.instance(), "Put all Goblin cards revealed this way into your hand"));

			EventFactory factory = new EventFactory(EventType.PUT_INTO_LIBRARY, "and the rest on the bottom of your library in any order.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			factory.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(revealed, revealedGoblins));
			this.addEffect(factory);
		}
	}

	public GoblinRingleader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Goblin Ringleader enters the battlefield, reveal the top four
		// cards of your library. Put all Goblin cards revealed this way into
		// your hand and the rest on the bottom of your library in any order.
		this.addAbility(new GoblinRingleaderAbility1(state));
	}
}
