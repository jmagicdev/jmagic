package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Quenchable Fire")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class QuenchableFire extends Card
{
	public QuenchableFire(GameState state)
	{
		super(state);

		// Quenchable Fire deals 3 damage to target player.
		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Quenchable Fire deals 3 damage to target player."));

		// It deals an additional 3 damage to that player at the beginning of
		// your next upkeep step unless he or she pays (U) before that step.

		// These generators are relative to the delayed trigger.
		SetGenerator targetsOfThisCard = ExtractTargets.instance(ChosenTargetsFor.instance(Identity.instance(target), ABILITY_SOURCE_OF_THIS));

		EventFactory delayedTrigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "It deals an additional 3 damage to that player at the beginning of your next upkeep step unless he or she pays (U) before that step.");
		delayedTrigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
		delayedTrigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfYourUpkeep()));
		delayedTrigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(spellDealDamage(3, targetsOfThisCard, "Quenchable Fire deals an additional 3 damage to that player.")));
		delayedTrigger.parameters.put(EventType.Parameter.COST, Identity.instance(new CostCollection(CostCollection.TYPE_STOP_TRIGGER, "(U)")));
		delayedTrigger.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		this.addEffect(delayedTrigger);
	}
}
