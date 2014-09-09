package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gnaw to the Bone")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class GnawtotheBone extends Card
{
	public GnawtotheBone(GameState state)
	{
		super(state);

		// You gain 2 life for each creature card in your graveyard.
		SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator creatureCardsInYourGraveyard = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), inYourGraveyard);
		this.addEffect(gainLife(You.instance(), Multiply.instance(Count.instance(creatureCardsInYourGraveyard), numberGenerator(2)), "You gain 2 life for each creature card in your graveyard."));

		// Flashback (2)(G) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(G)"));
	}
}
