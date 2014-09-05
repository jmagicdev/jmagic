package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Bestial Menace")
@Types({Type.SORCERY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BestialMenace extends Card
{
	public BestialMenace(GameState state)
	{
		super(state);

		// Put a 1/1 green Snake creature token, a 2/2 green Wolf creature
		// token, and a 3/3 green Elephant creature token onto the battlefield.
		CreateTokensFactory snake = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Snake creature token,");
		snake.setColors(Color.GREEN);
		snake.setSubTypes(SubType.SNAKE);

		CreateTokensFactory wolf = new CreateTokensFactory(1, 2, 2, "a 2/2 green Wolf creature token,");
		wolf.setColors(Color.GREEN);
		wolf.setSubTypes(SubType.WOLF);

		CreateTokensFactory elephant = new CreateTokensFactory(1, 3, 3, "and a 3/3 green Elephant creature token onto the battlefield.");
		elephant.setColors(Color.GREEN);
		elephant.setSubTypes(SubType.ELEPHANT);
		this.addEffect(simultaneous(snake.getEventFactory(), wolf.getEventFactory(), elephant.getEventFactory()));
	}
}
