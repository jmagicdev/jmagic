package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Extinguish All Hope")
@Types({Type.SORCERY})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class ExtinguishAllHope extends Card
{
	public ExtinguishAllHope(GameState state)
	{
		super(state);

		// Destroy all nonenchantment creatures.
		SetGenerator nonenchantmentCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ENCHANTMENT));
		this.addEffect(destroy(nonenchantmentCreatures, "Destroy all nonenchantment creatures."));
	}
}
