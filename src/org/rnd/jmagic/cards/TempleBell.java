package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Temple Bell")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({})
public final class TempleBell extends Card
{
	public static final class TempleBellAbility0 extends ActivatedAbility
	{
		public TempleBellAbility0(GameState state)
		{
			super(state, "(T): Each player draws a card.");
			this.costsTap = true;

			this.addEffect(drawCards(Players.instance(), 1, "Each player draws a card."));
		}
	}

	public TempleBell(GameState state)
	{
		super(state);

		// (T): Each player draws a card.
		this.addAbility(new TempleBellAbility0(state));
	}
}
