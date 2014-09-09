package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Searing Blood")
@Types({Type.INSTANT})
@ManaCost("RR")
@ColorIdentity({Color.RED})
public final class SearingBlood extends Card
{
	public SearingBlood(GameState state)
	{
		super(state);

		// Searing Blood deals 2 damage to target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(2, target, "Searing Blood deals 2 damage to target creature."));

		// When that creature dies this turn, Searing Blood deals 3 damage to
		// the creature's controller.
		ZoneChangePattern thatCreatureDies = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), target, true);

		EventFactory damageController = spellDealDamage(3, ControllerOf.instance(target), "Searing Blood deals 3 damage to the creature's controller.");

		EventFactory damageControllerLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When that creature dies this turn, Searing Blood deals 3 damage to the creature's controller.");
		damageControllerLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		damageControllerLater.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(thatCreatureDies));
		damageControllerLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(damageController));
		damageControllerLater.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(EndMostFloatingEffects.instance()));
		this.addEffect(damageControllerLater);
	}
}
