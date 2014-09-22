package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dark Heart of the Wood")
@Types({Type.ENCHANTMENT})
@ManaCost("BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class DarkHeartoftheWood extends Card
{
	public static final class SacForLife extends ActivatedAbility
	{
		public SacForLife(GameState state)
		{
			super(state, "Sacrifice a Forest: Gain 3 Life.");

			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.FOREST), "Sacrifice a Forest"));

			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public DarkHeartoftheWood(GameState state)
	{
		super(state);

		this.addAbility(new SacForLife(state));
	}
}
