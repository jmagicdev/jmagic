package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dictate of Erebos")
@Types({Type.ENCHANTMENT})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class DictateofErebos extends Card
{
	public static final class DictateofErebosAbility1 extends EventTriggeredAbility
	{
		public DictateofErebosAbility1(GameState state)
		{
			super(state, "Whenever a creature you control dies, each opponent sacrifices a creature.");
			this.addPattern(whenXDies(CREATURES_YOU_CONTROL));
			this.addEffect(sacrifice(OpponentsOf.instance(You.instance()), 1, CreaturePermanents.instance(), "Each opponent sacrifices a creature."));
		}
	}

	public DictateofErebos(GameState state)
	{
		super(state);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Whenever a creature you control dies, each opponent sacrifices a
		// creature.
		this.addAbility(new DictateofErebosAbility1(state));
	}
}
