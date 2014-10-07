package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Cyclops of Eternal Fury")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.CYCLOPS})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class CyclopsofEternalFury extends Card
{
	public static final class CyclopsofEternalFuryAbility0 extends StaticAbility
	{
		public CyclopsofEternalFuryAbility0(GameState state)
		{
			super(state, "Creatures you control have haste.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public CyclopsofEternalFury(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Creatures you control have haste.
		this.addAbility(new CyclopsofEternalFuryAbility0(state));
	}
}
