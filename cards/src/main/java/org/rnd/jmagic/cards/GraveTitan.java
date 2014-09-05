package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Grave Titan")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Magic2011.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class GraveTitan extends Card
{
	public static final class GraveTitanAbility1 extends EventTriggeredAbility
	{
		public GraveTitanAbility1(GameState state)
		{
			super(state, "Whenever Grave Titan enters the battlefield or attacks, put two 2/2 black Zombie creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisAttacks());
			CreateTokensFactory tokens = new CreateTokensFactory(2, 2, 2, "Put two 2/2 black Zombie creature tokens onto the battlefield.");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.ZOMBIE);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public GraveTitan(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Whenever Grave Titan enters the battlefield or attacks, put two 2/2
		// black Zombie creature tokens onto the battlefield.
		this.addAbility(new GraveTitanAbility1(state));
	}
}
