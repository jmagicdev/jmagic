package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cremate")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Cremate extends Card
{
	public Cremate(GameState state)
	{
		super(state);

		// Exile target card from a graveyard.
		SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));
		this.addEffect(exile(target, "Exile target card from a graveyard."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
