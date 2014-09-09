package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firebreathing")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Firebreathing extends Card
{
	public static final class FirebreathingAbility extends ActivatedAbility
	{
		public FirebreathingAbility(GameState state)
		{
			super(state, "(R): Enchanted creature gets +1/+0 until end of turn.");

			this.setManaCost(new ManaPool("R"));

			this.addEffect(ptChangeUntilEndOfTurn(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), (+1), (+0), "Enchanted creature gets +1/+0 until end of turn."));
		}
	}

	public Firebreathing(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		this.addAbility(new FirebreathingAbility(state));
	}
}
