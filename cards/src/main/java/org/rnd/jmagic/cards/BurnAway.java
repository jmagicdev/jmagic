package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burn Away")
@Types({Type.INSTANT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class BurnAway extends Card
{
	public BurnAway(GameState state)
	{
		super(state);

		// Burn Away deals 6 damage to target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(6, target, "Burn Away deals 6 damage to target creature."));

		// When that creature dies this turn, exile all cards from its
		// controller's graveyard.
		ZoneChangePattern thatCreatureDies = whenXDies(delayedTriggerContext(target));

		SetGenerator controller = ControllerOf.instance(delayedTriggerContext(target));
		EventFactory exile = exile(InZone.instance(GraveyardOf.instance(controller)), "Exile all cards from that creature's controller's graveyard.");

		EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When that creature dies this turn, exile all cards from its controller's graveyard.");
		exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exileLater.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(thatCreatureDies));
		exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
		exileLater.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(EndMostFloatingEffects.instance()));
		this.addEffect(exileLater);
	}
}
