package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Artful Dodge")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ArtfulDodge extends Card
{
	public ArtfulDodge(GameState state)
	{
		super(state);

		// Target creature is unblockable this turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(createFloatingEffect("Target creature can't be blocked this turn.", unblockable(target)));

		// Flashback (U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(U)"));
	}
}
