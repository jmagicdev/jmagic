package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wolfbriar Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class WolfbriarElemental extends Card
{
	public static final class MultiWolves extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public MultiWolves(GameState state, CostCollection kickerCost)
		{
			super(state, "When Wolfbriar Elemental enters the battlefield, put a 2/2 green Wolf creature token onto the battlefield for each time it was kicked.");
			this.kickerCost = kickerCost;
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator forEachTimeItWasKicked = ThisPermanentWasKicked.instance(kickerCost);
			String effectName = "Put a 2/2 green Wolf creature token onto the battlefield for each time it was kicked.";
			CreateTokensFactory tokens = new CreateTokensFactory(forEachTimeItWasKicked, numberGenerator(2), numberGenerator(2), effectName);
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.WOLF);
			this.addEffect(tokens.getEventFactory());
		}

		@Override
		public MultiWolves create(Game game)
		{
			return new MultiWolves(game.physicalState, this.kickerCost);
		}
	}

	public WolfbriarElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Multikicker (G)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(G)");
		this.addAbility(ability);

		// When Wolfbriar Elemental enters the battlefield, put a 2/2 green Wolf
		// creature token onto the battlefield for each time it was kicked.
		this.addAbility(new MultiWolves(state, ability.costCollections[0]));
	}
}
