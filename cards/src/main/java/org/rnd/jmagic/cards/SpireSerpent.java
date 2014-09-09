package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spire Serpent")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class SpireSerpent extends Card
{
	public static final class SpireSerpentAbility1 extends StaticAbility
	{
		public SpireSerpentAbility1(GameState state)
		{
			super(state, "As long as you control three or more artifacts, Spire Serpent gets +2/+2 and can attack as though it didn't have defender.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			ContinuousEffect.Part canAttack = new ContinuousEffect.Part(ContinuousEffectType.ATTACK_AS_THOUGH_DOESNT_HAVE_DEFENDER);
			canAttack.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(canAttack);

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public SpireSerpent(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Metalcraft \u2014 As long as you control three or more artifacts,
		// Spire Serpent gets +2/+2 and can attack as though it didn't have
		// defender.
		this.addAbility(new SpireSerpentAbility1(state));
	}
}
