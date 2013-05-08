package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Civic Saber")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CivicSaber extends Card
{
	public static final class CivicSaberAbility0 extends StaticAbility
	{
		public CivicSaberAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+0 for each of its colors.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			SetGenerator num = Count.instance(ColorsOf.instance(equippedCreature));
			this.addEffectPart(modifyPowerAndToughness(equippedCreature, num, numberGenerator(0)));
		}
	}

	public CivicSaber(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0 for each of its colors.
		this.addAbility(new CivicSaberAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
