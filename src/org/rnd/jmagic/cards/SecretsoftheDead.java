package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Secrets of the Dead")
@Types({Type.ENCHANTMENT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SecretsoftheDead extends Card
{
	public static final class SecretsoftheDeadAbility0 extends EventTriggeredAbility
	{
		public SecretsoftheDeadAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell from your graveyard, draw a card.");
			this.addPattern(whenYouCastASpellFromYourGraveyard());
			this.addEffect(drawACard());
		}
	}

	public SecretsoftheDead(GameState state)
	{
		super(state);

		// Whenever you cast a spell from your graveyard, draw a card.
		this.addAbility(new SecretsoftheDeadAbility0(state));
	}
}
