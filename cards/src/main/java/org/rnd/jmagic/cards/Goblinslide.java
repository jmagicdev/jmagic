package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Goblinslide")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class Goblinslide extends Card
{
	public static final class GoblinslideAbility0 extends EventTriggeredAbility
	{
		public GoblinslideAbility0(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, you may pay (1). If you do, put a 1/1 red Goblin creature token with haste onto the battlefield.");

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, new NonTypePattern(Type.CREATURE));
			this.addPattern(cast);

			EventFactory mayPay = youMayPay("(1)");

			CreateTokensFactory goblin = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Goblin creature token with haste onto the battlefield.");
			goblin.setColors(Color.RED);
			goblin.setSubTypes(SubType.GOBLIN);

			this.addEffect(ifThen(mayPay, goblin.getEventFactory(), "You may pay (1). If you do, put a 1/1 red Goblin creature token with haste onto the battlefield."));
		}
	}

	public Goblinslide(GameState state)
	{
		super(state);

		// Whenever you cast a noncreature spell, you may pay (1). If you do,
		// put a 1/1 red Goblin creature token with haste onto the battlefield.
		this.addAbility(new GoblinslideAbility0(state));
	}
}
