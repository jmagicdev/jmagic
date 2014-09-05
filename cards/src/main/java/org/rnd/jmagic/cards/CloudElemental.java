package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cloud Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Visions.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CloudElemental extends Card
{
	public static final class StuckUp extends StaticAbility
	{
		public StuckUp(GameState state)
		{
			super(state, "Cloud Elemental can block only creatures with flying.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator blockingNonFlyer = RelativeComplement.instance(BlockedBy.instance(This.instance()), hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingNonFlyer));
			this.addEffectPart(part);
		}
	}

	public CloudElemental(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new StuckUp(state));
	}
}
