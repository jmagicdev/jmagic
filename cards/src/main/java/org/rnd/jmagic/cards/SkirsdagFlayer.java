package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skirsdag Flayer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class SkirsdagFlayer extends Card
{
	public static final class SkirsdagFlayerAbility0 extends ActivatedAbility
	{
		public SkirsdagFlayerAbility0(GameState state)
		{
			super(state, "(3)(B), (T), Sacrifice a Human: Destroy target creature.");
			this.setManaCost(new ManaPool("(3)(B)"));
			this.costsTap = true;
			// Sacrifice a Human
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.HUMAN), "Sacrifice a Human"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(destroy(target, "Destroy target creature."));
		}
	}

	public SkirsdagFlayer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (3)(B), (T), Sacrifice a Human: Destroy target creature.
		this.addAbility(new SkirsdagFlayerAbility0(state));
	}
}
