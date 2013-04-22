package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Bushwhacker")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinBushwhacker extends Card
{
	public static final class WhackSomeBushes extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public WhackSomeBushes(GameState state, CostCollection kickerCost)
		{
			super(state, "When Goblin Bushwhacker enters the battlefield, if it was kicked, creatures you control get +1/+0 and gain haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.kickerCost = kickerCost;

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			SetGenerator yourGuys = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()));

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(yourGuys, +1, +0, "Creatures you control get +1/+0 and gain haste until end of turn.", org.rnd.jmagic.abilities.keywords.Haste.class));
		}

		@Override
		public WhackSomeBushes create(Game game)
		{
			return new WhackSomeBushes(game.physicalState, this.kickerCost);
		}
	}

	public GoblinBushwhacker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "R");
		this.addAbility(ability);

		// Kicker (R) (You may pay an additional (R) as you cast this spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// When Goblin Bushwhacker enters the battlefield, if it was kicked,
		// creatures you control get +1/+0 and gain haste until end of turn.
		this.addAbility(new WhackSomeBushes(state, kickerCost));
	}
}
