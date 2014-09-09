package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Biovisionary")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class Biovisionary extends Card
{
	public static final class BiovisionaryAbility0 extends EventTriggeredAbility
	{
		public BiovisionaryAbility0(GameState state)
		{
			super(state, "At the beginning of the end step, if you control four or more creatures named Biovisionary, you win the game.");
			this.addPattern(atTheBeginningOfTheEndStep());

			this.interveningIf = Intersect.instance(Count.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasName.instance("Biovisionary"))), Between.instance(4, null));

			this.addEffect(youWinTheGame());
		}
	}

	public Biovisionary(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// At the beginning of the end step, if you control four or more
		// creatures named Biovisionary, you win the game.
		this.addAbility(new BiovisionaryAbility0(state));
	}
}
