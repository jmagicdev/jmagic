package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Due Respect")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class DueRespect extends Card
{
	public DueRespect(GameState state)
	{
		super(state);

		// Permanents enter the battlefield tapped this turn.

		ZoneChangeReplacementEffect gatekeeping = new ZoneChangeReplacementEffect(this.game, "Permanents enter the battlefield tapped");
		gatekeeping.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), null, false));
		gatekeeping.addEffect(tap(NewObjectOf.instance(gatekeeping.replacedByThis()), "A permanent enters the battlefield tapped."));

		this.addEffect(createFloatingEffect("Permanents enter the battlefield tapped this turn.", replacementEffectPart(gatekeeping)));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
