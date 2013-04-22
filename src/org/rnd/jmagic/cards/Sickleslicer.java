package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sickleslicer")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Sickleslicer extends Card
{
	public static final class SickleslicerAbility1 extends StaticAbility
	{
		public SickleslicerAbility1(GameState state)
		{
			super(state, "Equipped creature gets +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +2, +2));
		}
	}

	public Sickleslicer(GameState state)
	{
		super(state);

		// Living weapon (When this Equipment enters the battlefield, put a 0/0
		// black Germ creature token onto the battlefield, then attach this to
		// it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LivingWeapon(state));

		// Equipped creature gets +2/+2.
		this.addAbility(new SickleslicerAbility1(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
