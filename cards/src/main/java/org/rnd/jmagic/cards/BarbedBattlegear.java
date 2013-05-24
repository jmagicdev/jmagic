package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Barbed Battlegear")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class BarbedBattlegear extends Card
{
	public static final class BarbedBattlegearAbility0 extends StaticAbility
	{
		public BarbedBattlegearAbility0(GameState state)
		{
			super(state, "Equipped creature gets +4/-1.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +4, -1));
		}
	}

	public BarbedBattlegear(GameState state)
	{
		super(state);

		// Equipped creature gets +4/-1.
		this.addAbility(new BarbedBattlegearAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
