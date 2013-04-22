package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Mogg War Marshal")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MoggWarMarshal extends Card
{
	public static final class MoggWarMarshalAbility1 extends EventTriggeredAbility
	{
		public MoggWarMarshalAbility1(GameState state)
		{
			super(state, "When Mogg War Marshal enters the battlefield or dies, put a 1/1 red Goblin creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisDies());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Goblin creature token onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.GOBLIN);
			this.addEffect(token.getEventFactory());
		}
	}

	public MoggWarMarshal(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Echo (1)(R) (At the beginning of your upkeep, if this came under your
		// control since the beginning of your last upkeep, sacrifice it unless
		// you pay its echo cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Echo(state, "(1)(R)"));

		// When Mogg War Marshal enters the battlefield or dies, put a 1/1 red
		// Goblin creature token onto the battlefield.
		this.addAbility(new MoggWarMarshalAbility1(state));
	}
}
