package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necropouncer")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Necropouncer extends Card
{
	public static final class NecropouncerAbility1 extends StaticAbility
	{
		public NecropouncerAbility1(GameState state)
		{
			super(state, "Equipped creature gets +3/+1 and has haste.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +3, +1));
			this.addEffectPart(addAbilityToObject(equippedCreature, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public Necropouncer(GameState state)
	{
		super(state);

		// Living weapon (When this Equipment enters the battlefield, put a 0/0
		// black Germ creature token onto the battlefield, then attach this to
		// it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LivingWeapon(state));

		// Equipped creature gets +3/+1 and has haste.
		this.addAbility(new NecropouncerAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
