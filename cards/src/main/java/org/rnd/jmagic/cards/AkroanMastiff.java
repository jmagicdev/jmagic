package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Akroan Mastiff")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class AkroanMastiff extends Card
{
	public static final class AkroanMastiffAbility0 extends ActivatedAbility
	{
		public AkroanMastiffAbility0(GameState state)
		{
			super(state, "(W), (T): Tap target creature.");
			this.setManaCost(new ManaPool("(W)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public AkroanMastiff(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (W), (T): Tap target creature.
		this.addAbility(new AkroanMastiffAbility0(state));
	}
}
