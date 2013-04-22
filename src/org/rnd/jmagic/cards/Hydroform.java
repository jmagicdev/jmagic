package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hydroform")
@Types({Type.INSTANT})
@ManaCost("GU")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class Hydroform extends Card
{
	public Hydroform(GameState state)
	{
		super(state);

		// Target land becomes a 3/3 Elemental creature with flying until end of
		// turn. It's still a land.
		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		Animator a = new Animator(target, 3, 3);
		a.addSubType(SubType.ELEMENTAL);
		a.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(createFloatingEffect("Target land becomes a 3/3 Elemental creature with flying until end of turn. It's still a land.", a.getParts()));
	}
}
