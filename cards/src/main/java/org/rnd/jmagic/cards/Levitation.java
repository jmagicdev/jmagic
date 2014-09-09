package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Levitation")
@Types({Type.ENCHANTMENT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class Levitation extends Card
{
	public static final class GrantFlying extends StaticAbility
	{
		public GrantFlying(GameState state)
		{
			super(state, "Creatures you control have flying.");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public Levitation(GameState state)
	{
		super(state);

		this.addAbility(new GrantFlying(state));
	}
}
