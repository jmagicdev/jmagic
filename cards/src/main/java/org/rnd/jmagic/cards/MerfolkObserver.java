package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Merfolk Observer")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.MERFOLK})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MerfolkObserver extends Card
{
	public static final class Peek extends EventTriggeredAbility
	{
		public Peek(GameState state)
		{
			super(state, "When Merfolk Observer enters the battlefield, look at the top card of target player's library.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory factory = new EventFactory(EventType.LOOK, "Look at the top card of target player's library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(targetedBy(target))));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public MerfolkObserver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Merfolk Observer enters the battlefield, look at the top card of
		// target player's library.
		this.addAbility(new Peek(state));
	}
}
