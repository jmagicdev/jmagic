package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Fervor")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class Fervor extends Card
{
	public static final class FervorAbility0 extends StaticAbility
	{
		public FervorAbility0(GameState state)
		{
			super(state, "Creatures you control have haste.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public Fervor(GameState state)
	{
		super(state);

		// Creatures you control have haste. (They can attack and (T) as soon as
		// they come under your control.)
		this.addAbility(new FervorAbility0(state));
	}
}
