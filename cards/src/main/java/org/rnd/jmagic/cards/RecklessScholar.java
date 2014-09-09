package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reckless Scholar")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class RecklessScholar extends Card
{
	public static final class TargetedLooting extends ActivatedAbility
	{
		public TargetedLooting(GameState state)
		{
			super(state, "(T): Target player draws a card, then discards a card.");
			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(drawCards(targetedBy(target), 1, "Target player draws a card,"));
			this.addEffect(discardCards(targetedBy(target), 1, "then discards a card."));
		}
	}

	public RecklessScholar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (T): Target player draws a card, then discards a card.
		this.addAbility(new TargetedLooting(state));
	}
}
