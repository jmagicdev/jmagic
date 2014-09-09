package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Griffin Rider")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class GriffinRider extends Card
{
	public static final class GriffinRiderAbility0 extends StaticAbility
	{
		public GriffinRiderAbility0(GameState state)
		{
			super(state, "As long as you control a Griffin creature, Griffin Rider gets +3/+3 and has flying.");

			SetGenerator youControlGriffin = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.GRIFFIN));
			this.canApply = Both.instance(this.canApply, youControlGriffin);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +3, +3));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public GriffinRider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// As long as you control a Griffin creature, Griffin Rider gets +3/+3
		// and has flying.
		this.addAbility(new GriffinRiderAbility0(state));
	}
}
