package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Brave the Sands")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class BravetheSands extends Card
{
	public static final class BravetheSandsAbility0 extends StaticAbility
	{
		public BravetheSandsAbility0(GameState state)
		{
			super(state, "Creatures you control have vigilance.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public static final class BravetheSandsAbility1 extends StaticAbility
	{
		public BravetheSandsAbility1(GameState state)
		{
			super(state, "Each creature you control can block an additional creature.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_AN_ADDITIONAL_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addEffectPart(part);
		}
	}

	public BravetheSands(GameState state)
	{
		super(state);

		// Creatures you control have vigilance.
		this.addAbility(new BravetheSandsAbility0(state));

		// Each creature you control can block an additional creature.
		this.addAbility(new BravetheSandsAbility1(state));
	}
}
