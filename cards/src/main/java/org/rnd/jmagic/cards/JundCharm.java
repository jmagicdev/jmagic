package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jund Charm")
@Types({Type.INSTANT})
@ManaCost("BRG")
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class JundCharm extends Card
{
	public JundCharm(GameState state)
	{
		super(state);

		// Choose one \u2014
		{
			// Exile all cards from target player's graveyard; or
			Target target = this.addTarget(1, Players.instance(), "target player");
			this.addEffect(1, exile(InZone.instance(GraveyardOf.instance(targetedBy(target))), "Exile all cards from target player's graveyard."));
		}

		{
			// Jund Charm deals 2 damage to each creature;
			this.addEffect(2, spellDealDamage(2, CreaturePermanents.instance(), "Jund Charm deals 2 damage to each creature."));
		}

		{
			// or put two +1/+1 counters on target creature.
			Target target = this.addTarget(3, CreaturePermanents.instance(), "target creature");
			this.addEffect(3, putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put two +1/+1 counters on target creature."));
		}
	}
}
