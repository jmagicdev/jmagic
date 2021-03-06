package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Concussive Bolt")
@Types({Type.SORCERY})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class ConcussiveBolt extends Card
{
	public ConcussiveBolt(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		// Concussive Bolt deals 4 damage to target player.
		this.addEffect(spellDealDamage(4, target, "Concussive Bolt deals 4 damage to target player."));

		// Metalcraft \u2014 If you control three or more artifacts, creatures
		// that player controls can't block this turn.
		SetGenerator thatPlayersCreatures = Intersect.instance(ControlledBy.instance(target), CreaturePermanents.instance());

		EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "\n\nMetalcraft \u2014 If you control three or more artifacts, creatures that player controls can't block this turn.");
		factory.parameters.put(EventType.Parameter.IF, Metalcraft.instance());
		factory.parameters.put(EventType.Parameter.THEN, Identity.instance(cantBlockThisTurn(thatPlayersCreatures, "Creatures that player controls can't block this turn.")));
		this.addEffect(factory);
	}
}
