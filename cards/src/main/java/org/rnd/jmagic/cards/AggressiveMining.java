package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Aggressive Mining")
@Types({Type.ENCHANTMENT})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class AggressiveMining extends Card
{
	public static final class AggressiveMiningAbility0 extends StaticAbility
	{
		public AggressiveMiningAbility0(GameState state)
		{
			super(state, "You can't play lands.");

			SimpleEventPattern playLand = new SimpleEventPattern(EventType.PLAY_LAND);
			playLand.put(EventType.Parameter.PLAYER, You.instance());

			ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(playLand));
			this.addEffectPart(prohibition);
		}
	}

	public static final class AggressiveMiningAbility1 extends ActivatedAbility
	{
		public AggressiveMiningAbility1(GameState state)
		{
			super(state, "Sacrifice a land: Draw two cards. Activate this ability only once each turn.");
			this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));
			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
			this.perTurnLimit(1);
		}
	}

	public AggressiveMining(GameState state)
	{
		super(state);

		// You can't play lands.
		this.addAbility(new AggressiveMiningAbility0(state));

		// Sacrifice a land: Draw two cards. Activate this ability only once
		// each turn.
		this.addAbility(new AggressiveMiningAbility1(state));
	}
}
