package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Steel Overseer")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("2")
@ColorIdentity({})
public final class SteelOverseer extends Card
{
	public static final class SteelOverseerAbility0 extends ActivatedAbility
	{
		public SteelOverseerAbility0(GameState state)
		{
			super(state, "(T): Put a +1/+1 counter on each artifact creature you control.");
			this.costsTap = true;

			SetGenerator yourArtifactCreatures = Intersect.instance(ArtifactPermanents.instance(), CREATURES_YOU_CONTROL);
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, yourArtifactCreatures, "Put a +1/+1 counter on each artifact creature you control."));
		}
	}

	public SteelOverseer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Put a +1/+1 counter on each artifact creature you control.
		this.addAbility(new SteelOverseerAbility0(state));
	}
}
