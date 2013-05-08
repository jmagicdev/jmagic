package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heartstabber Mosquito")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class HeartstabberMosquito extends Card
{
	public static final class ETBKill extends EventTriggeredAbility
	{
		private CostCollection kickerCost;

		public ETBKill(GameState state, CostCollection kickerCost)
		{
			super(state, "When Heartstabber Mosquito enters the battlefield, if it was kicked, destroy target creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.kickerCost = kickerCost;

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(destroy(targetedBy(target), "Destroy target creature."));
		}

		@Override
		public ETBKill create(Game game)
		{
			return new ETBKill(game.physicalState, this.kickerCost);
		}
	}

	public HeartstabberMosquito(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(2)(B)");
		this.addAbility(ability);

		// Kicker (2)(B) (You may pay an additional (2)(B) as you cast this
		// spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Heartstabber Mosquito enters the battlefield, if it was kicked,
		// destroy target creature.
		this.addAbility(new ETBKill(state, kickerCost));
	}
}
