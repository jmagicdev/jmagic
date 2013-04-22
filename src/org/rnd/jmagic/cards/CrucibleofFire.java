package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crucible of Fire")
@Types({Type.ENCHANTMENT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CrucibleofFire extends Card
{
	public static final class DragonPump extends StaticAbility
	{
		public DragonPump(GameState state)
		{
			super(state, "Dragon creatures you control get +3/+3.");

			SetGenerator dragonsYouControl = Intersect.instance(HasSubType.instance(SubType.DRAGON), CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(dragonsYouControl, +3, +3));
		}
	}

	public CrucibleofFire(GameState state)
	{
		super(state);

		this.addAbility(new DragonPump(state));
	}
}
