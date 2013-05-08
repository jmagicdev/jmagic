package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("\u00C6ther Figment")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class AEtherFigment extends Card
{
	public static final class ImaginaryViolence extends StaticAbility
	{
		private CostCollection kickerCost;

		public ImaginaryViolence(GameState state, CostCollection kickerCost)
		{
			super(state, "If \u00C6ther Figment was kicked, it enters the battlefield with two +1/+1 counters on it.");

			this.kickerCost = kickerCost;

			this.canApply = Both.instance(this.canApply, ThisPermanentWasKicked.instance(kickerCost));

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));
		}

		@Override
		public ImaginaryViolence create(Game game)
		{
			return new ImaginaryViolence(game.physicalState, this.kickerCost);
		}
	}

	public AEtherFigment(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "3");
		this.addAbility(ability);
		CostCollection kickerCost = ability.costCollections[0];

		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));

		this.addAbility(new ImaginaryViolence(state, kickerCost));
	}
}
