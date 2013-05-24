package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vanguard's Shield")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({})
public final class VanguardsShield extends Card
{
	public static final class VanguardsShieldAbility0 extends StaticAbility
	{
		public VanguardsShieldAbility0(GameState state)
		{
			super(state, "Equipped creature gets +0/+3 and can block an additional creature.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +0, +3));

			ContinuousEffect.Part blockMore = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_AN_ADDITIONAL_CREATURE);
			blockMore.parameters.put(ContinuousEffectType.Parameter.OBJECT, equipped);
			this.addEffectPart(blockMore);
		}
	}

	public VanguardsShield(GameState state)
	{
		super(state);

		// Equipped creature gets +0/+3 and can block an additional creature.
		this.addAbility(new VanguardsShieldAbility0(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
