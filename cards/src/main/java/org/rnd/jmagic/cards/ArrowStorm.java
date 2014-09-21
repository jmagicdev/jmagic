package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arrow Storm")
@Types({Type.SORCERY})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class ArrowStorm extends Card
{
	public ArrowStorm(GameState state)
	{
		super(state);

		// Arrow Storm deals 4 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		EventFactory damage = spellDealDamage(4, target, "Arrow Storm deals 4 damage to target creature or player.");

		// Raid \u2014 If you attacked with a creature this turn, instead Arrow
		// Storm deals 5 damage to that creature or player and the damage can't
		// be prevented.
		EventFactory damagePlus = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Arrow Storm deals 5 damage to that creature or player and the damage can't be prevented.");
		damagePlus.parameters.put(EventType.Parameter.SOURCE, This.instance());
		damagePlus.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
		damagePlus.parameters.put(EventType.Parameter.TAKER, target);
		damagePlus.parameters.put(EventType.Parameter.PREVENT, Empty.instance());

		state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
		this.addEffect(ifThenElse(Raid.instance(), damagePlus, damage, "Arrow Storm deals 4 damage to target creature or player.\n\nIf you attacked with a creature this turn, instead Arrow Storm deals 5 damage to that creature or player and the damage can't be prevented."));
	}
}
