package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Acolyte of Xathrid")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class AcolyteofXathrid extends Card
{
	public static final class LifeLoss extends ActivatedAbility
	{
		public LifeLoss(GameState state)
		{
			super(state, "(1)(B), (T): Target player loses 1 life.");

			this.setManaCost(new ManaPool("1B"));
			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(loseLife(targetedBy(target), 1, "Target player loses 1 life."));
		}
	}

	public AcolyteofXathrid(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (1)(B), (T): Target player loses 1 life.
		this.addAbility(new LifeLoss(state));
	}
}
