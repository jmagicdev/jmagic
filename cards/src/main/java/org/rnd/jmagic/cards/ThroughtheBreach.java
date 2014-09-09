package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Through the Breach")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class ThroughtheBreach extends Card
{
	public ThroughtheBreach(GameState state)
	{
		super(state);

		// You may put a creature card from your hand onto the battlefield.
		SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
		SetGenerator choices = Intersect.instance(inYourHand, HasType.instance(Type.CREATURE));

		EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield");
		putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, choices);
		this.addEffect(youMay(putOntoBattlefield, "You may put a creature card from your hand onto the battlefield."));

		// That creature gains haste.
		SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(putOntoBattlefield));
		this.addEffect(createFloatingEffect(Empty.instance(), "That creature gains haste.", addAbilityToObject(thatCreature, org.rnd.jmagic.abilities.keywords.Haste.class)));

		// Sacrifice that creature at the beginning of the next end step.
		EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice that creature.");
		sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrifice.parameters.put(EventType.Parameter.PLAYER, You.instance());
		sacrifice.parameters.put(EventType.Parameter.PERMANENT, delayedTriggerContext(thatCreature));

		EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Sacrifice that creature at the beginning of the next end step.");
		exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
		this.addEffect(exileLater);

		// Splice onto Arcane (2)(R)(R)
		this.addAbility(org.rnd.jmagic.abilities.keywords.Splice.ontoArcane(state, "(2)(R)(R)"));
	}
}
