package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sword of Vengeance")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({})
public final class SwordofVengeance extends Card
{
	public final static class SwordofVengeanceAbility0 extends StaticAbility
	{
		public SwordofVengeanceAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+0 and has first strike, vigilance, trample, and haste.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +2, 0));
			this.addEffectPart(addAbilityToObject(equippedCreature, FirstStrike.class, Vigilance.class, Trample.class, Haste.class));
		}
	}

	public SwordofVengeance(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+0 and has first strike, vigilance,
		// trample, and haste.
		this.addAbility(new SwordofVengeanceAbility0(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
