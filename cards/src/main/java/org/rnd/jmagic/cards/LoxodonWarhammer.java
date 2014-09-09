package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Loxodon Warhammer")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class LoxodonWarhammer extends Card
{
	public static final class EquipBonuses extends StaticAbility
	{
		public EquipBonuses(GameState state)
		{
			super(state, "Equipped creature gets +3/+0 and has lifelink and trample.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, 3, 0));

			this.addEffectPart(addAbilityToObject(equippedCreature, org.rnd.jmagic.abilities.keywords.Lifelink.class, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public LoxodonWarhammer(GameState state)
	{
		super(state);

		this.addAbility(new EquipBonuses(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
