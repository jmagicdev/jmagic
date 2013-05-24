package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stolen Identity")
@Types({Type.SORCERY})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class StolenIdentity extends Card
{
	public StolenIdentity(GameState state)
	{
		super(state);

		// Put a token onto the battlefield that's a copy of target artifact or
		// creature.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact or creature"));
		EventFactory token = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of target artifact or creature.");
		token.parameters.put(EventType.Parameter.CAUSE, This.instance());
		token.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		token.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(token);

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}
