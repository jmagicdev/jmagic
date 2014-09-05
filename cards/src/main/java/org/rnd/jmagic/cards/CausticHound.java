package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Caustic Hound")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CausticHound extends Card
{
	public static final class CausticHoundAbility0 extends EventTriggeredAbility
	{
		public CausticHoundAbility0(GameState state)
		{
			super(state, "When Caustic Hound dies, each player loses 4 life.");
			this.addPattern(whenThisDies());
			this.addEffect(loseLife(Players.instance(), 4, "Each player loses 4 life."));
		}
	}

	public CausticHound(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Caustic Hound is put into a graveyard from the battlefield, each
		// player loses 4 life.
		this.addAbility(new CausticHoundAbility0(state));
	}
}
