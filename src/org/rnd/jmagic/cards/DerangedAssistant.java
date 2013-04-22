package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deranged Assistant")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class DerangedAssistant extends Card
{
	public static final class DerangedAssistantAbility0 extends ActivatedAbility
	{
		public DerangedAssistantAbility0(GameState state)
		{
			super(state, "(T), Put the top card of your library into your graveyard: Add (1) to your mana pool.");
			this.costsTap = true;

			// Put the top card of your library into your graveyard
			this.addCost(millCards(You.instance(), 1, "Put the top card of your library into your graveyard"));
			this.addEffect(addManaToYourManaPoolFromAbility("(1)"));
		}
	}

	public DerangedAssistant(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T), Put the top card of your library into your graveyard: Add (1) to
		// your mana pool.
		this.addAbility(new DerangedAssistantAbility0(state));
	}
}
