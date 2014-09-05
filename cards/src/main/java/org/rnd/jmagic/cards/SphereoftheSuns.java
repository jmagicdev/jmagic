package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sphere of the Suns")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SphereoftheSuns extends Card
{
	public static final class SphereoftheSunsAbility0 extends StaticAbility
	{
		public SphereoftheSunsAbility0(GameState state)
		{
			super(state, "Sphere of the Suns enters the battlefield tapped and with three charge counters on it.");

			ZoneChangeReplacementEffect tappedWithCounters = new ZoneChangeReplacementEffect(this.game, "Sphere of the Suns enters the battlefield tapped and with three charge counters on it");
			tappedWithCounters.addPattern(asThisEntersTheBattlefield());
			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(tappedWithCounters));

			tappedWithCounters.addEffect(tap(NewObjectOf.instance(tappedWithCounters.replacedByThis()), "Sphere of the Suns enters the battlefield tapped"));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, "Sphere of the Suns enters the battlefield with three charge counters on it");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.CHARGE));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			tappedWithCounters.addEffect(factory);

			this.addEffectPart(replacementEffectPart(tappedWithCounters));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class SphereoftheSunsAbility1 extends ActivatedAbility
	{
		public SphereoftheSunsAbility1(GameState state)
		{
			super(state, "(T), Remove a charge counter from Sphere of the Suns: Add one mana of any color to your mana pool.");
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Sphere of the Suns"));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)", "Add one mana of any color to your mana pool."));
		}
	}

	public SphereoftheSuns(GameState state)
	{
		super(state);

		// Sphere of the Suns enters the battlefield tapped and with three
		// charge counters on it.
		this.addAbility(new SphereoftheSunsAbility0(state));

		// (T), Remove a charge counter from Sphere of the Suns: Add one mana of
		// any color to your mana pool.
		this.addAbility(new SphereoftheSunsAbility1(state));
	}
}
