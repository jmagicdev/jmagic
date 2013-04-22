package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pennon Blade")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PennonBlade extends Card
{
	public static final class PennonBladeAbility0 extends StaticAbility
	{
		public PennonBladeAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 for each creature you control.");

			SetGenerator amount = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), amount, amount));
		}
	}

	public PennonBlade(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1 for each creature you control.
		this.addAbility(new PennonBladeAbility0(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
