package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deathforge Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.OGRE})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class DeathforgeShaman extends Card
{
	public static final class DeathforgeShamanAbility1 extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public DeathforgeShamanAbility1(GameState state, CostCollection kickerCost)
		{
			super(state, "When Deathforge Shaman enters the battlefield, it deals damage to target player equal to twice the number of times it was kicked.");
			this.kickerCost = kickerCost;

			Target t = this.addTarget(Players.instance(), "target player");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator amount = Multiply.instance(ThisPermanentWasKicked.instance(kickerCost), numberGenerator(2));
			this.addEffect(permanentDealDamage(amount, targetedBy(t), "It deals damage to target player equal to twice the number of times it was kicked."));
		}

		@Override
		public DeathforgeShamanAbility1 create(Game game)
		{
			return new DeathforgeShamanAbility1(game.physicalState, this.kickerCost);
		}
	}

	public DeathforgeShaman(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Multikicker (R) (You may pay an additional (R) any number of times as
		// you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(R)");
		this.addAbility(kicker);

		// When Deathforge Shaman enters the battlefield, it deals damage to
		// target player equal to twice the number of times it was kicked.
		this.addAbility(new DeathforgeShamanAbility1(state, kicker.costCollections[0]));
	}
}
