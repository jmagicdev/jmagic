package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Accorder's Shield")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class AccordersShield extends Card
{
	public static final class AccordersShieldAbility0 extends StaticAbility
	{
		public AccordersShieldAbility0(GameState state)
		{
			super(state, "Equipped creature gets +0/+3 and has vigilance.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +0, +3));

			this.addEffectPart(addAbilityToObject(equippedCreature, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public AccordersShield(GameState state)
	{
		super(state);

		// Equipped creature gets +0/+3 and has vigilance.
		this.addAbility(new AccordersShieldAbility0(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
