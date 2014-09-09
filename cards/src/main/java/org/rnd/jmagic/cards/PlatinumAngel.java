package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Platinum Angel")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("7")
@ColorIdentity({})
public final class PlatinumAngel extends Card
{
	public static final class CantLoseCantWin extends StaticAbility
	{
		public CantLoseCantWin(GameState state)
		{
			super(state, "You can't lose the game and your opponents can't win the game.");
			SimpleEventPattern loseEvent = new SimpleEventPattern(EventType.LOSE_GAME);
			loseEvent.put(EventType.Parameter.PLAYER, You.instance());

			SimpleEventPattern winEvent = new SimpleEventPattern(EventType.WIN_GAME);
			winEvent.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(winEvent, loseEvent));
			this.addEffectPart(part);
		}
	}

	public PlatinumAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new CantLoseCantWin(state));
	}
}
