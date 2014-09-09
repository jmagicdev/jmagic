package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kor Aeronaut")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class KorAeronaut extends Card
{
	public static final class ETBFlying extends EventTriggeredAbility
	{
		private CostCollection kickerCost;

		public ETBFlying(GameState state, CostCollection kickerCost)
		{
			super(state, "When Kor Aeronaut enters the battlefield, if it was kicked, target creature gains flying until end of turn.");
			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
		}

		@Override
		public ETBFlying create(Game game)
		{
			return new ETBFlying(game.physicalState, this.kickerCost);
		}
	}

	public KorAeronaut(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(1)(W)");
		this.addAbility(ability);

		// Kicker (1)(W) (You may pay an additional (1)(W) as you cast this
		// spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Kor Aeronaut enters the battlefield, if it was kicked, target
		// creature gains flying until end of turn.
		this.addAbility(new ETBFlying(state, kickerCost));
	}
}
