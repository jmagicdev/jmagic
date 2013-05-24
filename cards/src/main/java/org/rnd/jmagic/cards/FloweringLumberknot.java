package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flowering Lumberknot")
@Types({Type.CREATURE})
@SubTypes({SubType.TREEFOLK})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class FloweringLumberknot extends Card
{
	public static final class FloweringLumberknotAbility0 extends StaticAbility
	{
		public FloweringLumberknotAbility0(GameState state)
		{
			super(state, "Flowering Lumberknot can't attack or block unless it's paired with a creature with soulbond.");

			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Attacking.instance())));
			this.addEffectPart(restrictions);

			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Blocking.instance())));
			this.addEffectPart(block);

			SetGenerator pairedWithSoulbond = Intersect.instance(PairedWith.instance(This.instance()), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Soulbond.class));
			this.canApply = Both.instance(this.canApply, Not.instance(pairedWithSoulbond));
		}
	}

	public FloweringLumberknot(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flowering Lumberknot can't attack or block unless it's paired with a
		// creature with soulbond.
		this.addAbility(new FloweringLumberknotAbility0(state));
	}
}
