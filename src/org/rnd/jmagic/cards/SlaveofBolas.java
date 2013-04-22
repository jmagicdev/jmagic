package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slave of Bolas")
@Types({Type.SORCERY})
@ManaCost("3(U/R)B")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class SlaveofBolas extends Card
{
	public SlaveofBolas(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// Gain control of target creature.
		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

		EventType.ParameterMap controlParameters = new EventType.ParameterMap();
		controlParameters.put(EventType.Parameter.CAUSE, This.instance());
		controlParameters.put(EventType.Parameter.EFFECT, Identity.instance(controlPart));
		controlParameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		this.addEffect(new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, controlParameters, "Gain control of target creature."));

		// Untap that creature.
		this.addEffect(untap(targetedBy(target), "Untap that creature."));

		// It gains haste until end of turn.
		this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));

		// "It" in "Sacrifice it at..." is relative to the delayed trigger.
		SetGenerator thatCreature = ExtractTargets.instance(ChosenTargetsFor.instance(Identity.instance(target), ABILITY_SOURCE_OF_THIS));

		EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice that creature");
		sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrifice.parameters.put(EventType.Parameter.PLAYER, You.instance());
		sacrifice.parameters.put(EventType.Parameter.PERMANENT, thatCreature);

		// Sacrifice it at the beginning of the next end step.
		EventFactory trigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Sacrifice it at the beginning of the next end step.");
		trigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
		trigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		trigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
		this.addEffect(trigger);
	}
}
