package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Servant of Tymaret")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class ServantofTymaret extends Card
{
	public static final class ServantofTymaretAbility0 extends EventTriggeredAbility
	{
		public ServantofTymaretAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Servant of Tymaret becomes untapped, each opponent loses 1 life. You gain life equal to the life lost this way.");
			this.addPattern(inspired());

			EventFactory loseLife = loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life.");
			this.addEffect(loseLife);

			SetGenerator lost = Sum.instance(EffectResult.instance(loseLife));
			this.addEffect(gainLife(You.instance(), lost, "You gain life equal to the life lost this way."));
		}
	}

	public ServantofTymaret(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Inspired \u2014 Whenever Servant of Tymaret becomes untapped, each
		// opponent loses 1 life. You gain life equal to the life lost this way.
		this.addAbility(new ServantofTymaretAbility0(state));

		// (2)(B): Regenerate Servant of Tymaret.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(2)(B)", this.getName()));
	}
}
