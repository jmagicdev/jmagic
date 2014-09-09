package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Inexorable Tide")
@Types({Type.ENCHANTMENT})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class InexorableTide extends Card
{
	public static final class InexorableTideAbility0 extends EventTriggeredAbility
	{
		public InexorableTideAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell, proliferate.");

			this.addPattern(whenYouCastASpell());

			this.addEffect(proliferate());
		}
	}

	public InexorableTide(GameState state)
	{
		super(state);

		// Whenever you cast a spell, proliferate. (You choose any number of
		// permanents and/or players with counters on them, then give each
		// another counter of a kind already there.)
		this.addAbility(new InexorableTideAbility0(state));
	}
}
