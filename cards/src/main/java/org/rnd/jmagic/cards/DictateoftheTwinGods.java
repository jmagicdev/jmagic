package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Dictate of the Twin Gods")
@Types({Type.ENCHANTMENT})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class DictateoftheTwinGods extends Card
{
	public static final class DictateoftheTwinGodsAbility1 extends StaticAbility
	{
		public DictateoftheTwinGodsAbility1(GameState state)
		{
			super(state, "If a source would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.");
			this.addEffectPart(replacementEffectPart(new FurnaceofRath.DoubleDamageEffect(this.game)));
		}
	}

	public DictateoftheTwinGods(GameState state)
	{
		super(state);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// If a source would deal damage to a permanent or player, it deals
		// double that damage to that permanent or player instead.
		this.addAbility(new DictateoftheTwinGodsAbility1(state));
	}
}
