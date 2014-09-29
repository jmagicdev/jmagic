package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trap Essence")
@Types({Type.INSTANT})
@ManaCost("GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class TrapEssence extends Card
{
	public TrapEssence(GameState state)
	{
		super(state);

		// Counter target creature spell.
		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target targetSpell = this.addTarget(creatureSpells, "target creature spell");

		this.addEffect(Convenience.counter(targetedBy(targetSpell), "Counter target creature spell."));

		// Put two +1/+1 counters on up to one target creature.
		SetGenerator targetCreature = targetedBy(this.addTarget(CreaturePermanents.instance(), "up to one target creature").setNumber(0, 1));
		this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetCreature, "Put two +1/+1 counters on up to one target creature."));
	}
}
