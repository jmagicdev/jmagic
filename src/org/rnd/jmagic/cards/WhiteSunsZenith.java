package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("White Sun's Zenith")
@Types({Type.INSTANT})
@ManaCost("XWWW")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class WhiteSunsZenith extends Card
{
	public WhiteSunsZenith(GameState state)
	{
		super(state);

		// Put X 2/2 white Cat creature tokens onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(ValueOfX.instance(This.instance()), "Put X 2/2 white Cat creature tokens onto the battlefield.");
		factory.addCreature(2, 2);
		factory.setColors(Color.WHITE);
		factory.setSubTypes(SubType.CAT);
		this.addEffect(factory.getEventFactory());

		// Shuffle White Sun's Zenith into its owner's library.
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle White Sun's Zenith into its owner's library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), You.instance()));
		this.addEffect(shuffle);
	}
}
