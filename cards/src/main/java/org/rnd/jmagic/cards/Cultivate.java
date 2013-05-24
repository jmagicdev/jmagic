package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cultivate")
@Types({Type.SORCERY})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Cultivate extends Card
{
	public Cultivate(GameState state)
	{
		super(state);

		// Search your library for up to two basic land cards, reveal those
		// cards, and put one onto the battlefield tapped and the other into
		// your hand. Then shuffle your library.
		SetGenerator basicLandCards = Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND));
		SetGenerator basicLandsInYourLibrary = Intersect.instance(InZone.instance(LibraryOf.instance(You.instance())), basicLandCards);

		// TODO : I realize I'm using a custom event type from another card
		// here. The right thing to do is to make a new, more complicated
		// MOVE_OBJECTS_TO_DIFFERENT_ZONES event that takes a map from where to
		// put things to what to put there. There are a couple possible
		// implementations of this though (mainly, how to handle ordered zones'
		// indices), and rather than just picking one I'd rather discuss it. In
		// the mean time I'm doing the "wrong thing" and just getting the card
		// done.
		// TODO : Above todo is moot now that we have the 'simultaneous'
		// convenience event. Use it. Here and on Kodama's Reach.
		EventFactory effect = new EventFactory(KodamasReach.KODAMAS_REACH_EVENT, "Search your library for two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Then shuffle your library.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.CHOICE, Identity.instance(basicLandsInYourLibrary));
		this.addEffect(effect);
	}
}
