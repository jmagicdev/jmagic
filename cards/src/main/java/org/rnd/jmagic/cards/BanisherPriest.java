package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Banisher Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class BanisherPriest extends Card
{
	public static class ETBExile extends EventTriggeredAbility
	{
		public ETBExile(GameState state)
		{
			super(state, "When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target target = this.addTarget(opponentsCreatures, "target creature an opponent controls");
			this.addEffect(exileUntilThisLeavesTheBattlefield(state, targetedBy(target), "Exile target creature an opponent controls until Banisher Priest leaves the battlefield."));
		}
	}

	public BanisherPriest(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new ETBExile(state));
	}
}
