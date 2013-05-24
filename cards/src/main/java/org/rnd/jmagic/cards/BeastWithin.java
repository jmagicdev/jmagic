package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beast Within")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BeastWithin extends Card
{
	public BeastWithin(GameState state)
	{
		super(state);

		// Destroy target permanent. Its controller puts a 3/3 green Beast
		// creature token onto the battlefield.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
		this.addEffect(destroy(target, "Destroy target permanent."));

		CreateTokensFactory c = new CreateTokensFactory(1, 3, 3, "Its controller puts a 3/3 green Beast creature token onto the battlefield.");
		c.setColors(Color.GREEN);
		c.setSubTypes(SubType.BEAST);
		c.setController(ControllerOf.instance(target));
		this.addEffect(c.getEventFactory());
	}
}
