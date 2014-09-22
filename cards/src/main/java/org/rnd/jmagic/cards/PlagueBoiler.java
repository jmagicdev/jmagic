package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Plague Boiler")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class PlagueBoiler extends Card
{
	public static final class Tick extends EventTriggeredAbility
	{
		public Tick(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a plague counter on Plague Boiler.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			this.addEffect(putCounters(1, Counter.CounterType.PLAGUE, thisCard, "Put a plague counter on Plague Boiler."));
		}
	}

	public static final class Tock extends ActivatedAbility
	{
		public Tock(GameState state)
		{
			super(state, "(1)(B)(G): Put a plague counter on Plague Boiler or remove a plague counter from it.");
			this.setManaCost(new ManaPool("1BG"));

			EventFactory put = putCountersOnThis(1, Counter.CounterType.PLAGUE, "Put a plague counter on Plague Boiler");
			EventFactory remove = removeCountersFromThis(1, Counter.CounterType.PLAGUE, "Remove a plague counter from Plague Boiler");

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.EVENT, Identity.instance(put, remove));
			this.addEffect(new EventFactory(EventType.CHOOSE_AND_PERFORM, parameters, "Put a plague counter on Plague Boiler or remove a plague counter from it"));
		}
	}

	public static final class Boom extends StateTriggeredAbility
	{
		public Boom(GameState state)
		{
			super(state, "When Plague Boiler has three or more plague counters on it, sacrifice it. If you do, destroy all nonland permanents.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SetGenerator plagueCountersOnThis = CountersOn.instance(thisCard, Counter.CounterType.PLAGUE);
			SetGenerator numPlagueCountersOnThis = Count.instance(plagueCountersOnThis);
			SetGenerator threeOrMore = Identity.instance(new org.rnd.util.NumberRange(3, null));
			SetGenerator triggerCondition = Intersect.instance(threeOrMore, numPlagueCountersOnThis);
			this.addCondition(triggerCondition);

			EventFactory sacrifice = sacrificeThis("Plague Boiler");

			SetGenerator permanents = InZone.instance(Battlefield.instance());
			SetGenerator lands = HasType.instance(Type.LAND);
			SetGenerator nonLandPermanents = RelativeComplement.instance(permanents, lands);
			EventFactory destroy = destroy(nonLandPermanents, "Destroy all nonland permanents");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Sacrifice Plague Boiler.  If you do, destroy all nonland permanents.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(sacrifice));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(destroy));
			this.addEffect(effect);
		}
	}

	public PlagueBoiler(GameState state)
	{
		super(state);

		this.addAbility(new Tick(state));
		this.addAbility(new Tock(state));
		this.addAbility(new Boom(state));
	}
}
