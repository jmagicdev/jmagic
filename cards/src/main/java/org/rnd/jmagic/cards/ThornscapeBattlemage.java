package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thornscape Battlemage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ELF})
@ManaCost("2G")
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class ThornscapeBattlemage extends Card
{
	public static final class ElectricBoots extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public ElectricBoots(GameState state, CostCollection kickerCost)
		{
			super(state, "When Thornscape Battlemage enters the battlefield, if it was kicked with its (R) kicker, it deals 2 damage to target creature or player.");

			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(2, targetedBy(target), "Thornscape Battlemage deals 2 damage to target creature or player."));
		}

		@Override
		public ElectricBoots create(Game game)
		{
			return new ElectricBoots(game.physicalState, this.kickerCost);
		}
	}

	public static final class KickedCantripShatter extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public KickedCantripShatter(GameState state, CostCollection kickerCost)
		{
			super(state, "When Thornscape Battlemage enters the battlefield, if it was kicked with its (W) kicker, destroy target artifact.");

			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact."));
		}

		@Override
		public KickedCantripShatter create(Game game)
		{
			return new KickedCantripShatter(game.physicalState, this.kickerCost);
		}
	}

	public ThornscapeBattlemage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		CostCollection redKickerCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Kicker.COST_TYPE, "R");
		CostCollection whiteKickerCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Kicker.COST_TYPE, "W");
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Kicker(state, redKickerCost, whiteKickerCost));

		this.addAbility(new ElectricBoots(state, redKickerCost));

		this.addAbility(new KickedCantripShatter(state, whiteKickerCost));
	}
}
