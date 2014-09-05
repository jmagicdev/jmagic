package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Make a Wish")
@Types({Type.SORCERY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MakeaWish extends Card
{
	public MakeaWish(GameState state)
	{
		super(state);

		SetGenerator yourYard = GraveyardOf.instance(You.instance());

		// Return two cards at random from your graveyard to your hand.
		EventFactory random = new EventFactory(RANDOM, "");
		random.parameters.put(EventType.Parameter.OBJECT, InZone.instance(yourYard));
		random.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		this.addEffect(random);

		this.addEffect(putIntoHand(EffectResult.instance(random), You.instance(), "Return two cards at random from your graveyard to your hand."));
	}
}
