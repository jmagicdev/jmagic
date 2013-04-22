package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Black Sun's Zenith")
@Types({Type.SORCERY})
@ManaCost("XBB")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BlackSunsZenith extends Card
{
	public BlackSunsZenith(GameState state)
	{
		super(state);

		// Put X -1/-1 counters on each creature.
		this.addEffect(putCounters(ValueOfX.instance(This.instance()), Counter.CounterType.MINUS_ONE_MINUS_ONE, CreaturePermanents.instance(), "Put X -1/-1 counters on each creature."));

		// Shuffle Black Sun's Zenith into its owner's library.
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle Black Sun's Zenith into its owner's library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), You.instance()));
		this.addEffect(shuffle);
	}
}
