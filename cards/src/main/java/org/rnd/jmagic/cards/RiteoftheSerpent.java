package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rite of the Serpent")
@Types({Type.SORCERY})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class RiteoftheSerpent extends Card
{
	public RiteoftheSerpent(GameState state)
	{
		super(state);

		// Destroy target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));

		// If that creature had a +1/+1 counter on it, put a 1/1 green Snake
		// creature token onto the battlefield.
		SetGenerator hadCounter = CountersOn.instance(target, Counter.CounterType.PLUS_ONE_PLUS_ONE);

		CreateTokensFactory snake = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Snake creature token onto the battlefield.");
		snake.setColors(Color.GREEN);
		snake.setSubTypes(SubType.SNAKE);

		this.addEffect(ifThen(hadCounter, snake.getEventFactory(), "If that creature had a +1/+1 counter on it, put a 1/1 green Snake creature token onto the battlefield."));
	}
}
