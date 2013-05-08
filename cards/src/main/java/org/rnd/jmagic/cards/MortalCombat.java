package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mortal Combat")
@Types({Type.ENCHANTMENT})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TORMENT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class MortalCombat extends Card
{
	public static final class FinishHim extends EventTriggeredAbility
	{
		public FinishHim(GameState state)
		{
			super(state, "At the beginning of your upkeep, if twenty or more creature cards are in your graveyard, you win the game.");

			SetGenerator numCreatures = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS)))));

			this.addPattern(atTheBeginningOfYourUpkeep());

			this.interveningIf = Intersect.instance(numCreatures, Between.instance(20, null));

			this.addEffect(youWinTheGame());
		}
	}

	public MortalCombat(GameState state)
	{
		super(state);

		this.addAbility(new FinishHim(state));
	}
}
