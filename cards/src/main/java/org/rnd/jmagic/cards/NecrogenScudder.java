package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Necrogen Scudder")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class NecrogenScudder extends Card
{
	public static final class NecrogenScudderAbility1 extends EventTriggeredAbility
	{
		public NecrogenScudderAbility1(GameState state)
		{
			super(state, "When Necrogen Scudder enters the battlefield, you lose 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(loseLife(You.instance(), 3, "You lose 3 life."));
		}
	}

	public NecrogenScudder(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Necrogen Scudder enters the battlefield, you lose 3 life.
		this.addAbility(new NecrogenScudderAbility1(state));
	}
}
