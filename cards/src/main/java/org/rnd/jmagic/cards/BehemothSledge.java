package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Behemoth Sledge")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class BehemothSledge extends Card
{
	public static final class EquipBonuses extends StaticAbility
	{
		public EquipBonuses(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has lifelink and trample.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, 2, 2));

			this.addEffectPart(addAbilityToObject(equippedCreature, org.rnd.jmagic.abilities.keywords.Lifelink.class, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public BehemothSledge(GameState state)
	{
		super(state);

		this.addAbility(new EquipBonuses(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
