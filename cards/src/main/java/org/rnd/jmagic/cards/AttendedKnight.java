package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Attended Knight")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AttendedKnight extends Card
{
	public static final class AttendedKnightAbility1 extends EventTriggeredAbility
	{
		public AttendedKnightAbility1(GameState state)
		{
			super(state, "When Attended Knight enters the battlefield, put a 1/1 white Soldier creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(token.getEventFactory());
		}
	}

	public AttendedKnight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// When Attended Knight enters the battlefield, put a 1/1 white Soldier
		// creature token onto the battlefield.
		this.addAbility(new AttendedKnightAbility1(state));
	}
}
