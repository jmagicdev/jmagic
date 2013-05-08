package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blessings of Nature")
@Types({Type.SORCERY})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BlessingsofNature extends Card
{
	public BlessingsofNature(GameState state)
	{
		super(state);

		// Distribute four +1/+1 counters among any number of target creatures.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to four target creatures");
		target.setNumber(1, 4);
		this.setDivision(Union.instance(numberGenerator(4), Identity.instance("+1/+1 counters")));

		EventFactory factory = new EventFactory(EventType.DISTRIBUTE_COUNTERS, "Distribute four +1/+1 counters among any number of target creatures.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
		this.addEffect(factory);

		// Miracle (G) (You may cast this card for its miracle cost when you
		// draw it if it's the first card you drew this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(G)"));
	}
}
