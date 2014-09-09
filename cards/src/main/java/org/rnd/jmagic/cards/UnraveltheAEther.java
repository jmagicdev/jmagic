package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unravel the \u00C6ther")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class UnraveltheAEther extends Card
{
	public UnraveltheAEther(GameState state)
	{
		super(state);

		// Choose target artifact or enchantment.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));

		// Its owner shuffles it into his or her library.
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Choose target artifact or enchantment. Its owner shuffles it into his or her library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(shuffle);
	}
}
