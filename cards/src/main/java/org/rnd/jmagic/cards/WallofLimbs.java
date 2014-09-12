package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Wall of Limbs")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WALL})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class WallofLimbs extends Card
{
	public static final class WallofLimbsAbility1 extends EventTriggeredAbility
	{
		public WallofLimbsAbility1(GameState state)
		{
			super(state, "Whenever you gain life, put a +1/+1 counter on Wall of Limbs.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.GAIN_LIFE);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Wall of Limbs."));
		}
	}

	public static final class WallofLimbsAbility2 extends ActivatedAbility
	{
		public WallofLimbsAbility2(GameState state)
		{
			super(state, "(5)(B)(B), Sacrifice Wall of Limbs: Target player loses X life, where X is Wall of Limbs's power.");
			this.setManaCost(new ManaPool("(5)(B)(B)"));
			this.addCost(sacrificeThis("Wall of Limbs"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, PowerOf.instance(ABILITY_SOURCE_OF_THIS), "Target player loses X life, where X is Wall of Limbs's power."));
		}
	}

	public WallofLimbs(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Whenever you gain life, put a +1/+1 counter on Wall of Limbs.
		this.addAbility(new WallofLimbsAbility1(state));

		// (5)(B)(B), Sacrifice Wall of Limbs: Target player loses X life, where
		// X is Wall of Limbs's power.
		this.addAbility(new WallofLimbsAbility2(state));
	}
}
