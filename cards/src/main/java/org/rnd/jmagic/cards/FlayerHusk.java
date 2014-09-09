package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flayer Husk")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class FlayerHusk extends Card
{
	public static final class FlayerHuskAbility1 extends StaticAbility
	{
		public FlayerHuskAbility1(GameState state)
		{
			super(state, "Equipped creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +1));
		}
	}

	public FlayerHusk(GameState state)
	{
		super(state);

		// Living weapon (When this Equipment enters the battlefield, put a 0/0
		// black Germ creature token onto the battlefield, then attach this to
		// it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LivingWeapon(state));

		// Equipped creature gets +1/+1.
		this.addAbility(new FlayerHuskAbility1(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
