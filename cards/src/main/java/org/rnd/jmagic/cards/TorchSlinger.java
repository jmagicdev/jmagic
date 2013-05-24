package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Torch Slinger")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TorchSlinger extends Card
{
	public static final class ETBShock extends EventTriggeredAbility
	{
		private CostCollection kickerCost;

		public ETBShock(GameState state, CostCollection kickerCost)
		{
			super(state, "When Torch Slinger enters the battlefield, if it was kicked, it deals 2 damage to target creature.");
			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Torch Slinger deals 2 damage to target creature."));
		}

		@Override
		public ETBShock create(Game game)
		{
			return new ETBShock(game.physicalState, this.kickerCost);
		}
	}

	public TorchSlinger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Kicker (1)(R) (You may pay an additional (1)(R) as you cast this
		// spell.)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(1)(R)");
		this.addAbility(ability);
		CostCollection kickerCost = ability.costCollections[0];

		// When Torch Slinger enters the battlefield, if it was kicked, it deals
		// 2 damage to target creature.
		this.addAbility(new ETBShock(state, kickerCost));
	}
}
