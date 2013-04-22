package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodhusk Ritualist")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BloodhuskRitualist extends Card
{
	public static final class BloodhuskRitualistAbility1 extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public BloodhuskRitualistAbility1(GameState state, CostCollection kickerCost)
		{
			super(state, "When Bloodhusk Ritualist enters the battlefield, target opponent discards a card for each time it was kicked.");
			this.kickerCost = kickerCost;

			Target t = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");
			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(discardCards(targetedBy(t), ThisPermanentWasKicked.instance(kickerCost), "Target opponent discards a card for each time it was kicked."));
		}

		@Override
		public BloodhuskRitualistAbility1 create(Game game)
		{
			return new BloodhuskRitualistAbility1(game.physicalState, this.kickerCost);
		}
	}

	public BloodhuskRitualist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Multikicker (B) (You may pay an additional (B) any number of times as
		// you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(B)");
		this.addAbility(kicker);

		// When Bloodhusk Ritualist enters the battlefield, target opponent
		// discards a card for each time it was kicked.
		this.addAbility(new BloodhuskRitualistAbility1(state, kicker.costCollections[0]));
	}
}
