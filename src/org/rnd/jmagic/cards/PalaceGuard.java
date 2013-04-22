package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Palace Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class PalaceGuard extends Card
{
	public static final class CanBlockAnyNumber extends StaticAbility
	{
		public CanBlockAnyNumber(GameState state)
		{
			super(state, "Palace Guard can block any number of creatures.");

			// Palace Guard can block any number of creatures.
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_ANY_NUMBER_OF_CREATURES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public PalaceGuard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		this.addAbility(new CanBlockAnyNumber(state));
	}
}
