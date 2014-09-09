package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Banishment Decree")
@Types({Type.INSTANT})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class BanishmentDecree extends Card
{
	public BanishmentDecree(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), EnchantmentPermanents.instance()), "target artifact, creature, or enchantment"));

		// Put target artifact, creature, or enchantment on top of its owner's
		// library.
		EventFactory factory = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target artifact, creature, or enchantment on top of its owner's library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);
	}
}
