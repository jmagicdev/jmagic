package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Endless Ranks of the Dead")
@Types({Type.ENCHANTMENT})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class EndlessRanksoftheDead extends Card
{
	public static final class EndlessRanksoftheDeadAbility0 extends EventTriggeredAbility
	{
		public EndlessRanksoftheDeadAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put X 2/2 black Zombie creature tokens onto the battlefield, where X is half the number of Zombies you control, rounded down.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator zombiesYouControl = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), ControlledBy.instance(You.instance()));
			SetGenerator amount = DivideBy.instance(zombiesYouControl, numberGenerator(2), false);
			CreateTokensFactory tokens = new CreateTokensFactory(amount, numberGenerator(2), numberGenerator(2), "Put X 2/2 black Zombie creature tokens onto the battlefield, where X is half the number of Zombies you control, rounded down.");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.ZOMBIE);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public EndlessRanksoftheDead(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, put X 2/2 black Zombie creature
		// tokens onto the battlefield, where X is half the number of Zombies
		// you control, rounded down.
		this.addAbility(new EndlessRanksoftheDeadAbility0(state));
	}
}
