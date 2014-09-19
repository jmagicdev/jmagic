package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Steel Golem")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("3")
@ColorIdentity({})
public final class SteelGolem extends Card
{
	public static final class ProhibitCreatures extends StaticAbility
	{
		public ProhibitCreatures(GameState state)
		{
			super(state, "You can't cast creature spells.");

			PlayProhibition castCreature = new PlayProhibition(You.instance(), (c -> c.types.contains(Type.CREATURE)));

			ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castCreature));
			this.addEffectPart(prohibition);
		}
	}

	public SteelGolem(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new ProhibitCreatures(state));
	}
}
