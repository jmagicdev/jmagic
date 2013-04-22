package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tel-Jilad Defiance")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TelJiladDefiance extends Card
{
	public TelJiladDefiance(GameState state)
	{
		super(state);

		// Target creature gains protection from artifacts until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(createFloatingEffect("Target creature gains protection from artifacts until end of turn.", addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Protection.FromArtifacts.class)));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
