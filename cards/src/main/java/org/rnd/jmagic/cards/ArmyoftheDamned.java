package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Army of the Damned")
@Types({Type.SORCERY})
@ManaCost("5BBB")
@ColorIdentity({Color.BLACK})
public final class ArmyoftheDamned extends Card
{
	public ArmyoftheDamned(GameState state)
	{
		super(state);

		// Put thirteen 2/2 black Zombie creature tokens onto the battlefield
		// tapped.
		CreateTokensFactory tokens = new CreateTokensFactory(13, 2, 2, "Put thirteen 2/2 black Zombie creature tokens onto the battlefield tapped.");
		tokens.setColors(Color.BLACK);
		tokens.setSubTypes(SubType.ZOMBIE);
		tokens.setTapped();
		this.addEffect(tokens.getEventFactory());

		// Flashback (7)(B)(B)(B) (You may cast this card from your graveyard
		// for its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(7)(B)(B)(B)"));
	}
}
