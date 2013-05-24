package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Platinum Emperion")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("8")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class PlatinumEmperion extends Card
{
	public static final class PlatinumEmperionAbility0 extends StaticAbility
	{
		public PlatinumEmperionAbility0(GameState state)
		{
			super(state, "Your life total can't change.");

			SimpleEventPattern gainLife = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
			gainLife.put(EventType.Parameter.PLAYER, You.instance());
			SimpleEventPattern loseLife = new SimpleEventPattern(EventType.LOSE_LIFE_ONE_PLAYER);
			loseLife.put(EventType.Parameter.PLAYER, You.instance());

			ContinuousEffect.Part prohibit = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibit.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(gainLife, loseLife));
			this.addEffectPart(prohibit);
		}
	}

	public PlatinumEmperion(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Your life total can't change.
		this.addAbility(new PlatinumEmperionAbility0(state));
	}
}
