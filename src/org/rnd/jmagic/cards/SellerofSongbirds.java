package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Seller of Songbirds")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SellerofSongbirds extends Card
{
	public static final class SellerofSongbirdsAbility0 extends EventTriggeredAbility
	{
		public SellerofSongbirdsAbility0(GameState state)
		{
			super(state, "When Seller of Songbirds enters the battlefield, put a 1/1 white Bird creature token with flying onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Bird creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.BIRD);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public SellerofSongbirds(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// When Seller of Songbirds enters the battlefield, put a 1/1 white Bird
		// creature token with flying onto the battlefield.
		this.addAbility(new SellerofSongbirdsAbility0(state));
	}
}
