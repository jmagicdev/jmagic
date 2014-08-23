package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Rootborn Defenses")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RootbornDefenses extends Card
{
	public RootbornDefenses(GameState state)
	{
		super(state);

		// Populate.
		this.addEffect(populate());

		// Creatures you control are indestructible this turn.
		this.addEffect(createFloatingEffect("Creatures you control gain indestructible until end of turn.", addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Indestructible.class)));
	}
}
