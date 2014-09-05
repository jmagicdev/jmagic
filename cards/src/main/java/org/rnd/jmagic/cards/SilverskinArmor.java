package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Silverskin Armor")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SilverskinArmor extends Card
{
	public static final class SilverskinArmorAbility0 extends StaticAbility
	{
		public SilverskinArmorAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 and is an artifact in addition to its other types.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +1, +1));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, equippedCreature);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT));
			this.addEffectPart(part);
		}
	}

	public SilverskinArmor(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1 and is an artifact in addition to its
		// other types.
		this.addAbility(new SilverskinArmorAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
