package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chain Lightning")
@Types({Type.SORCERY})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class ChainLightning extends Card
{
	public ChainLightning(GameState state)
	{
		super(state);

		// Chain Lightning deals 3 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(3, target, "Chain Lightning deals 3 damage to target creature or player."));

		// Then that player or that creature's controller may pay (R)(R).
		SetGenerator thatPlayer = Union.instance(target, ControllerOf.instance(target));

		EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "That player or that creature's controller may pay (R)(R)");
		mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("RR")));
		mayPay.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

		// If the player does, he or she may copy this spell and may choose a
		// new target for that copy.
		EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "That player copies this spell and may choose a new target for that copy");
		copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
		copy.parameters.put(EventType.Parameter.OBJECT, This.instance());
		copy.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

		EventFactory mayCopy = playerMay(thatPlayer, copy, "He or she may copy this spell and may choose a new target for that copy");

		EventFactory copyIfPay = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Then that player or that creature's controller may pay (R)(R). If the player does, he or she may copy this spell and may choose a new target for that copy.");
		copyIfPay.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
		copyIfPay.parameters.put(EventType.Parameter.THEN, Identity.instance(mayCopy));
		this.addEffect(copyIfPay);
	}
}
