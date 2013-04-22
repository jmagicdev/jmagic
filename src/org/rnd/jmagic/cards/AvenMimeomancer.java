package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Aven Mimeomancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.BIRD})
@ManaCost("1WU")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AvenMimeomancer extends Card
{
	public static final class AvianFlu extends EventTriggeredAbility
	{
		public AvianFlu(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a feather counter on target creature. If you do, that creature is 3/1 and has flying for as long as it has a feather counter on it.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part1 = setPowerAndToughness(targetedBy(target), 3, 1);

			ContinuousEffect.Part part2 = addAbilityToObject(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class);

			EventFactory fceFactory = createFloatingEffect(Not.instance(CountersOn.instance(targetedBy(target), Counter.CounterType.FEATHER)), "That creature is 3/1 and has flying for as long as it has a feather counter on it.", part1, part2);

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may put a feather counter on target creature. If you do, that creature is 3/1 and has flying for as long as it has a feather counter on it.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(putCounters(1, Counter.CounterType.FEATHER, targetedBy(target), "Put a feather counter on target creature."), "You may put a feather counter on target creature.")));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(fceFactory));
			this.addEffect(factory);
		}
	}

	public AvenMimeomancer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new AvianFlu(state));
	}
}
