package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class VividLand extends Card
{
	public static final class Charged extends StaticAbility
	{
		private String thisName;

		public Charged(GameState state, String thisName)
		{
			super(state, thisName + " enters the battlefield tapped with two charge counters on it.");
			this.thisName = thisName;

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, thisName + " enters the battlefield tapped with two charge counters on it");
			SetGenerator newObject = NewObjectOf.instance(replacement.replacedByThis());
			replacement.addPattern(asThisEntersTheBattlefield());
			replacement.addEffect(tap(newObject, thisName + " enters the battlefield tapped"));
			replacement.addEffect(putCounters(2, Counter.CounterType.CHARGE, newObject, thisName + " enters the battlefield with two charge counters on it"));

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}

		@Override
		public Charged create(Game game)
		{
			return new Charged(game.physicalState, this.thisName);
		}
	}

	public final static class VividMana extends ActivatedAbility
	{
		private String thisName;

		public VividMana(GameState state, String thisName)
		{
			super(state, "(T), Remove a charge counter from " + thisName + ": Add one mana of any color to your mana pool.");

			this.costsTap = true;
			this.thisName = thisName;

			EventFactory counterFactory = new EventFactory(EventType.REMOVE_COUNTERS, "Remove a charge counter from " + thisName);
			counterFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			counterFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			counterFactory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.CHARGE));
			counterFactory.parameters.put(EventType.Parameter.OBJECT, AbilitySource.instance(This.instance()));
			this.addCost(counterFactory);

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}

		@Override
		public VividMana create(Game game)
		{
			return new VividMana(game.physicalState, this.thisName);
		}
	}

	public VividLand(GameState state, Color color)
	{
		super(state);

		this.addAbility(new Charged(state, this.getName()));

		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(" + color.getLetter() + ")"));

		this.addAbility(new VividMana(state, this.getName()));
	}
}
