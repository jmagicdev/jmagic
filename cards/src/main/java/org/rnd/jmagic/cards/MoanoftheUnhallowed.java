package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Moan of the Unhallowed")
@Types({Type.SORCERY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class MoanoftheUnhallowed extends Card
{
	public MoanoftheUnhallowed(GameState state)
	{
		super(state);

		// Put two 2/2 black Zombie creature tokens onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(2, 2, 2, "Put two 2/2 black Zombie creature tokens onto the battlefield.");
		factory.setColors(Color.BLACK);
		factory.setSubTypes(SubType.ZOMBIE);
		this.addEffect(factory.getEventFactory());

		// Flashback (5)(B)(B) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(B)(B)"));
	}
}
