package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Orchard Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class OrchardSpirit extends Card
{
	public static final class OrchardSpiritAbility0 extends StaticAbility
	{
		public OrchardSpiritAbility0(GameState state)
		{
			super(state, "Orchard Spirit can't be blocked except by creatures with flying or reach.");

			SetGenerator hasFlyingOrReach = Union.instance(HasKeywordAbility.instance(Flying.class), HasKeywordAbility.instance(Reach.class));
			SetGenerator notBlockingWithFlyingOrReach = RelativeComplement.instance(Blocking.instance(This.instance()), hasFlyingOrReach);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(notBlockingWithFlyingOrReach));
			this.addEffectPart(part);
		}
	}

	public OrchardSpirit(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Orchard Spirit can't be blocked except by creatures with flying or
		// reach.
		this.addAbility(new OrchardSpiritAbility0(state));
	}
}
