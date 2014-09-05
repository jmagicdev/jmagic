package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lair Delve")
@Types({Type.SORCERY})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LairDelve extends Card
{
	public LairDelve(GameState state)
	{
		super(state);

		// Reveal the top two cards of your library. Put all creature and land
		// cards revealed this way into your hand and the rest on the bottom of
		// your library in any order.
		EventFactory reveal = reveal(TopCards.instance(2, LibraryOf.instance(You.instance())), "Reveal the top two cards of your library.");
		this.addEffect(reveal);

		SetGenerator revealed = EffectResult.instance(reveal);
		SetGenerator creaturesAndLands = Intersect.instance(revealed, HasType.instance(Type.CREATURE, Type.LAND));
		this.addEffect(putIntoHand(creaturesAndLands, You.instance(), "Put all creature and land cards revealed this way into your hand"));

		this.addEffect(putOnBottomOfLibrary(RelativeComplement.instance(revealed, HasType.instance(Type.CREATURE, Type.LAND)), "and the rest on the bottom of your library in any order."));
	}
}
