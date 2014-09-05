package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Revoke Existence")
@Types({Type.SORCERY})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RevokeExistence extends Card
{
	public RevokeExistence(GameState state)
	{
		super(state);

		// Exile target artifact or enchantment.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
		this.addEffect(exile(target, "Exile target artifact or enchantment."));
	}
}
