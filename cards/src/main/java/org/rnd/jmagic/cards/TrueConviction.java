package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("True Conviction")
@Types({Type.ENCHANTMENT})
@ManaCost("3WWW")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class TrueConviction extends Card
{
	public static final class TrueConvictionAbility0 extends StaticAbility
	{
		public TrueConvictionAbility0(GameState state)
		{
			super(state, "Creatures you control have double strike and lifelink.");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.DoubleStrike.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public TrueConviction(GameState state)
	{
		super(state);

		// Creatures you control have double strike and lifelink.
		this.addAbility(new TrueConvictionAbility0(state));
	}
}
