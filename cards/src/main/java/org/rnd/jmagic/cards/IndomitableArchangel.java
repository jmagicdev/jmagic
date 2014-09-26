package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Indomitable Archangel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class IndomitableArchangel extends Card
{
	public static final class IndomitableArchangelAbility1 extends StaticAbility
	{
		public IndomitableArchangelAbility1(GameState state)
		{
			super(state, "Metalcraft \u2014 Artifacts you control have shroud as long as you control three or more artifacts.");

			this.addEffectPart(addAbilityToObject(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())), Shroud.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public IndomitableArchangel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Metalcraft \u2014 Artifacts you control have shroud as long as you
		// control three or more artifacts.
		this.addAbility(new IndomitableArchangelAbility1(state));
	}
}
