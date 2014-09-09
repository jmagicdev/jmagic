package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Abyssal Persecutor")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class AbyssalPersecutor extends Card
{
	public static final class CantLoseCantWin extends StaticAbility
	{
		public CantLoseCantWin(GameState state)
		{
			super(state, "You can't win the game and your opponents can't lose the game.");
			SetGenerator controller = ControllerOf.instance(This.instance());

			SimpleEventPattern loseEvent = new SimpleEventPattern(EventType.WIN_GAME);
			loseEvent.put(EventType.Parameter.PLAYER, controller);

			SimpleEventPattern winEvent = new SimpleEventPattern(EventType.LOSE_GAME);
			winEvent.put(EventType.Parameter.PLAYER, OpponentsOf.instance(controller));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(winEvent, loseEvent));
			this.addEffectPart(part);
		}
	}

	public AbyssalPersecutor(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// You can't win the game and your opponents can't lose the game.
		this.addAbility(new CantLoseCantWin(state));
	}
}
