package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Highway Robber")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN, SubType.MERCENARY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class HighwayRobber extends Card
{
	public static final class Mug extends EventTriggeredAbility
	{
		public Mug(GameState state)
		{
			super(state, "When Highway Robber enters the battlefield, target opponent loses 2 life and you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

			this.addEffect(loseLife(targetedBy(target), 2, "Target opponent loses 2 life"));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public HighwayRobber(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Mug(state));
	}
}
