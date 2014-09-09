package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kite Shield")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("0")
@ColorIdentity({})
public final class KiteShield extends Card
{
	public static final class KiteShieldAbility0 extends StaticAbility
	{
		public KiteShieldAbility0(GameState state)
		{
			super(state, "Equipped creature gets +0/+3.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +0, +3));
		}
	}

	public KiteShield(GameState state)
	{
		super(state);

		// Equipped creature gets +0/+3.
		this.addAbility(new KiteShieldAbility0(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
