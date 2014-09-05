package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Voyager Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class VoyagerDrake extends Card
{
	public static final class VoyagerDrakeAbility2 extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public VoyagerDrakeAbility2(GameState state, CostCollection kickerCost)
		{
			super(state, "When Voyager Drake enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times Voyager Drake was kicked.");
			this.kickerCost = kickerCost;

			Target t = this.addTarget(CreaturePermanents.instance(), "up to X target creatures");
			t.setRange(Between.instance(numberGenerator(0), ThisPermanentWasKicked.instance(kickerCost)));
			this.addPattern(whenThisEntersTheBattlefield());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(t));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Flying.class)));
			this.addEffect(createFloatingEffect("Up to X target creatures gain flying until end of turn, where X is the number of times Voyager Drake was kicked.", part));
		}

		@Override
		public VoyagerDrakeAbility2 create(Game game)
		{
			return new VoyagerDrakeAbility2(game.physicalState, this.kickerCost);
		}
	}

	public VoyagerDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Multikicker (U) (You may pay an additional (U) any number of times as
		// you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(U)");
		this.addAbility(kicker);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Voyager Drake enters the battlefield, up to X target creatures
		// gain flying until end of turn, where X is the number of times Voyager
		// Drake was kicked.
		this.addAbility(new VoyagerDrakeAbility2(state, kicker.costCollections[0]));
	}
}
