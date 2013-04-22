package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Faerie Tauntings")
@Types({Type.ENCHANTMENT, Type.TRIBAL})
@SubTypes({SubType.FAERIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class FaerieTauntings extends Card
{
	public static final class LoseLifeTrickery extends org.rnd.jmagic.abilityTemplates.WhenYouCastASpellDuringOpponentsTurn
	{
		public LoseLifeTrickery(GameState state)
		{
			super(state, "you may have each opponent lose 1 life.");
			EventFactory loseLife = loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life");
			this.addEffect(youMay(loseLife, "You may have each opponent lose 1 life."));
		}
	}

	public FaerieTauntings(GameState state)
	{
		super(state);

		// Whenever you cast a spell during an opponent's turn, you may have
		// each opponent lose 1 life.
		this.addAbility(new LoseLifeTrickery(state));
	}
}
