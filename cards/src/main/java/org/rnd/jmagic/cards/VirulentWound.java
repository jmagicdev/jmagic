package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Virulent Wound")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VirulentWound extends Card
{
	public VirulentWound(GameState state)
	{
		super(state);
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// Put a -1/-1 counter on target creature. When that creature is put
		// into a graveyard this turn, its controller gets a poison counter.

		this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, targetedBy(target), "Put a -1/-1 counter on target creature."));

		// "This" will refer to the created delayed trigger, not Virulent Wound
		SetGenerator thatCreature = ExtractTargets.instance(ChosenTargetsFor.instance(Identity.instance(target), AbilitySource.instance(This.instance())));
		ZoneChangePattern whenThatCreatureIsPutIntoAGraveyard = new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), thatCreature, true);

		SetGenerator untilEndOfTurn = Intersect.instance(CurrentStep.instance(), CleanupStepOf.instance(Players.instance()));

		// "This" will refer to the created delayed trigger, not Virulent Wound
		ControllerOf itsController = ControllerOf.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance())));
		EventFactory poison = putCounters(1, Counter.CounterType.POISON, itsController, "Its controller gets a poison counter");

		EventFactory trigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When that creature is put into a graveyard this turn, its controller gets a poison counter.");
		trigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
		trigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(poison));
		trigger.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(untilEndOfTurn));
		trigger.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(whenThatCreatureIsPutIntoAGraveyard));
		this.addEffect(trigger);
	}
}
