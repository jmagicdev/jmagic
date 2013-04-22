package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tormentor's Trident")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class TormentorsTrident extends Card
{
	public static final class TormentorsTridentAbility0 extends StaticAbility
	{
		public TormentorsTridentAbility0(GameState state)
		{
			super(state, "Equipped creature gets +3/+0 and attacks each turn if able.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +3, +0));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, equipped);
			this.addEffectPart(part);
		}
	}

	public TormentorsTrident(GameState state)
	{
		super(state);

		// Equipped creature gets +3/+0 and attacks each turn if able.
		this.addAbility(new TormentorsTridentAbility0(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
