package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Augury Owl")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AuguryOwl extends Card
{
	public static final class AuguryOwlAbility1 extends EventTriggeredAbility
	{
		public AuguryOwlAbility1(GameState state)
		{
			super(state, "When Augury Owl enters the battlefield, scry 3. ");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(3, "Scry 3."));
		}
	}

	public AuguryOwl(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Augury Owl enters the battlefield, scry 3. (To scry 3, look at
		// the top three cards of your library, then put any number of them on
		// the bottom of your library and the rest on top in any order.)
		this.addAbility(new AuguryOwlAbility1(state));
	}
}
