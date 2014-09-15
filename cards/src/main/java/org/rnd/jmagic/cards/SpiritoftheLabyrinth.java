package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.trackers.*;

@Name("Spirit of the Labyrinth")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SpiritoftheLabyrinth extends Card
{
	public static final class SpiritoftheLabyrinthAbility0 extends StaticAbility
	{
		public SpiritoftheLabyrinthAbility0(GameState state)
		{
			super(state, "Each player can't draw more than one card each turn.");

			state.ensureTracker(new DrawTracker());
			SetGenerator cantDraw = DrawnACardThisTurn.instance();

			SimpleEventPattern drawPattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			drawPattern.put(EventType.Parameter.PLAYER, cantDraw);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(drawPattern));
			this.addEffectPart(part);
		}
	}

	public SpiritoftheLabyrinth(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Each player can't draw more than one card each turn.
		this.addAbility(new SpiritoftheLabyrinthAbility0(state));
	}
}
