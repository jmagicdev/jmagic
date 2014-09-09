package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deglamer")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Deglamer extends Card
{
	public Deglamer(GameState state)
	{
		super(state);

		// Choose target artifact or enchantment. Its owner shuffles it into his
		// or her library.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));

		EventFactory effect = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Choose target artifact or enchantment. Its owner shuffles it into his or her library.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.OBJECT, Union.instance(target, OwnerOf.instance(target)));
		this.addEffect(effect);
	}
}
