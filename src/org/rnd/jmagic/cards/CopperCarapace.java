package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Copper Carapace")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({})
public final class CopperCarapace extends Card
{
	public static final class CopperCarapaceAbility0 extends StaticAbility
	{
		public CopperCarapaceAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and can't block.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +2, +2));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), equippedCreature)));
			this.addEffectPart(part);
		}
	}

	public CopperCarapace(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and can't block.
		this.addAbility(new CopperCarapaceAbility0(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
