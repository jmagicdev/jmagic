package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bitterblossom")
@Types({Type.ENCHANTMENT, Type.TRIBAL})
@SubTypes({SubType.FAERIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Bitterblossom extends Card
{
	public static final class StingyLittleBastards extends EventTriggeredAbility
	{
		public StingyLittleBastards(GameState state)
		{
			super(state, "At the beginning of your upkeep, you lose 1 life and put a 1/1 black Faerie Rogue creature token with flying onto the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(loseLife(You.instance(), 1, "You lose 1 life"));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "and put a 1/1 black Faerie Rogue creature token with flying onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.FAERIE, SubType.ROGUE);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public Bitterblossom(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you lose 1 life and put a 1/1 black
		// Faerie Rogue creature token with flying onto the battlefield.
		this.addAbility(new StingyLittleBastards(state));
	}
}
