package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Elgaud Inquisitor")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ElgaudInquisitor extends Card
{
	public static final class ElgaudInquisitorAbility1 extends EventTriggeredAbility
	{
		public ElgaudInquisitorAbility1(GameState state)
		{
			super(state, "When Elgaud Inquisitor dies, put a 1/1 white Spirit creature token with flying onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory makeSpirit = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield");
			makeSpirit.setColors(Color.WHITE);
			makeSpirit.setSubTypes(SubType.SPIRIT);
			makeSpirit.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(makeSpirit.getEventFactory());
		}
	}

	public ElgaudInquisitor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// When Elgaud Inquisitor dies, put a 1/1 white Spirit creature token
		// with flying onto the battlefield.
		this.addAbility(new ElgaudInquisitorAbility1(state));
	}
}
