package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("High Ground")
@Types({Type.ENCHANTMENT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class HighGround extends Card
{
	// Each creature you control can block an additional creature.
	public static final class BlockMoreCreatures extends StaticAbility
	{
		public BlockMoreCreatures(GameState state)
		{
			super(state, "Each creature you control can block an additional creature.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_AN_ADDITIONAL_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addEffectPart(part);
		}
	}

	public HighGround(GameState state)
	{
		super(state);

		this.addAbility(new BlockMoreCreatures(state));
	}
}
