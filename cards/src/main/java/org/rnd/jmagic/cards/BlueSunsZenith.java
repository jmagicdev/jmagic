package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blue Sun's Zenith")
@Types({Type.INSTANT})
@ManaCost("XUUU")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class BlueSunsZenith extends Card
{
	public BlueSunsZenith(GameState state)
	{
		super(state);

		// Target player draws X cards.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		this.addEffect(drawCards(target, ValueOfX.instance(This.instance()), "Target player draws X cards."));

		// Shuffle Blue Sun's Zenith into its owner's library.
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle Blue Sun's Zenith into its owner's library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), You.instance()));
		this.addEffect(shuffle);
	}
}
