package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mirri's Guile")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MirrisGuile extends Card
{
	public static final class MirrisGuileAbility0 extends EventTriggeredAbility
	{
		public MirrisGuileAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may look at the top three cards of your library, then put them back in any order.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory look = new EventFactory(EventType.LOOK_AND_PUT_BACK, "Look at the top three cards of your library, then put them back in any order");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			look.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(youMay(look, "You may look at the top three cards of your library, then put them back in any order."));
		}
	}

	public MirrisGuile(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may look at the top three cards
		// of your library, then put them back in any order.
		this.addAbility(new MirrisGuileAbility0(state));
	}
}
