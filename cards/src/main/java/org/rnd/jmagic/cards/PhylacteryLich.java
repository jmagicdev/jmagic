package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phylactery Lich")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("BBB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PhylacteryLich extends Card
{
	public static final class PhylacteryLichAbility0 extends StaticAbility
	{
		public PhylacteryLichAbility0(GameState state)
		{
			super(state, "As Phylactery Lich enters the battlefield, put a phylactery counter on an artifact you control.");

			EventFactory phylactate = new EventFactory(EventType.PUT_COUNTER_ON_CHOICE, "Put a phylactery counter on an artifact you control.");
			phylactate.parameters.put(EventType.Parameter.CAUSE, This.instance());
			phylactate.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())));
			phylactate.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PHYLACTERY));
			phylactate.parameters.put(EventType.Parameter.PLAYER, You.instance());

			ZoneChangeReplacementEffect replacePart = new ZoneChangeReplacementEffect(this.game, "Put a phylactery counter on an artifact you control");
			replacePart.addPattern(asThisEntersTheBattlefield());
			replacePart.addEffect(phylactate);

			this.addEffectPart(replacementEffectPart(replacePart));
			this.canApply = NonEmpty.instance();
		}
	}

	public static final class PhylacteryLichAbility2 extends StateTriggeredAbility
	{
		public PhylacteryLichAbility2(GameState state)
		{
			super(state, "When you control no permanents with phylactery counters on them, sacrifice Phylactery Lich.");

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator permanentsWithPhylacteryCounters = HasCounterOfType.instance(Counter.CounterType.PHYLACTERY);
			this.addCondition(Not.instance(Intersect.instance(youControl, permanentsWithPhylacteryCounters)));

			this.addEffect(sacrificeThis("Phylactery Lich"));
		}
	}

	public PhylacteryLich(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// As Phylactery Lich enters the battlefield, put a phylactery counter
		// on an artifact you control.
		this.addAbility(new PhylacteryLichAbility0(state));

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// When you control no permanents with phylactery counters on them,
		// sacrifice Phylactery Lich.
		this.addAbility(new PhylacteryLichAbility2(state));
	}
}
