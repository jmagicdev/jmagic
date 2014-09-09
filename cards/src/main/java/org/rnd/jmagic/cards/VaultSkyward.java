package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vault Skyward")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class VaultSkyward extends Card
{
	public VaultSkyward(GameState state)
	{
		super(state);

		// Target creature gains flying until end of turn. Untap it.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(createFloatingEffect("Target creature gains flying until end of turn.", addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Flying.class)));
		this.addEffect(untap(target, "Untap it."));
	}
}
