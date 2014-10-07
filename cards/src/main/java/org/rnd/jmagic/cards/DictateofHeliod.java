package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Dictate of Heliod")
@Types({Type.ENCHANTMENT})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class DictateofHeliod extends Card
{
	public static final class DictateofHeliodAbility1 extends StaticAbility
	{
		public DictateofHeliodAbility1(GameState state)
		{
			super(state, "Creatures you control get +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +2, +2));
		}
	}

	public DictateofHeliod(GameState state)
	{
		super(state);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Creatures you control get +2/+2.
		this.addAbility(new DictateofHeliodAbility1(state));
	}
}
