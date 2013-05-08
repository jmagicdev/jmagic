package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trusty Machete")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class TrustyMachete extends Card
{
	public static final class TheKeyToTrust extends StaticAbility
	{
		public TheKeyToTrust(GameState state)
		{
			super(state, "Equipped creature gets +2/+1.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, 2, 1));
		}
	}

	public TrustyMachete(GameState state)
	{
		super(state);

		this.addAbility(new TheKeyToTrust(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
