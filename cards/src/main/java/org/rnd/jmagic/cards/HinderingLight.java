package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hindering Light")
@Types({Type.INSTANT})
@ManaCost("WU")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class HinderingLight extends Card
{
	public HinderingLight(GameState state)
	{
		super(state);

		// Counter target spell that targets you or a permanent you control.
		SetGenerator targetsYouOrYourStuff = HasTarget.instance(Union.instance(You.instance(), ControlledBy.instance(You.instance())));
		Target target = this.addTarget(Intersect.instance(Spells.instance(), targetsYouOrYourStuff), "target spell that targets you or a permanent you control");
		this.addEffect(counter(targetedBy(target), "Counter target spell that targets you or a permanent you control."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
