package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Riot Gear")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RiotGear extends Card
{
	public static final class RiotGearAbility0 extends StaticAbility
	{
		public RiotGearAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+2.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +2));
		}
	}

	public RiotGear(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+2.
		this.addAbility(new RiotGearAbility0(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
