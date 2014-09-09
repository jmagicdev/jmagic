package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hinterland Scourge")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@ColorIdentity({Color.RED})
public final class HinterlandScourge extends AlternateCard
{
	public static final class HinterlandScourgeAbility0 extends StaticAbility
	{
		public HinterlandScourgeAbility0(GameState state)
		{
			super(state, "Hinterland Scourge must be blocked if able.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, This.instance());
			this.addEffectPart(part);
		}
	}

	public HinterlandScourge(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.setColorIndicator(Color.RED);

		// Hinterland Scourge must be blocked if able.
		this.addAbility(new HinterlandScourgeAbility0(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Hinterland Scourge.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
