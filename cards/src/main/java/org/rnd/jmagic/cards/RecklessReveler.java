package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reckless Reveler")
@Types({Type.CREATURE})
@SubTypes({SubType.SATYR})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class RecklessReveler extends Card
{
	public static final class RecklessRevelerAbility0 extends ActivatedAbility
	{
		public RecklessRevelerAbility0(GameState state)
		{
			super(state, "(R), Sacrifice Reckless Reveler: Destroy target artifact.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Reckless Reveler"));
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
		}
	}

	public RecklessReveler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (R), Sacrifice Reckless Reveler: Destroy target artifact.
		this.addAbility(new RecklessRevelerAbility0(state));
	}
}
