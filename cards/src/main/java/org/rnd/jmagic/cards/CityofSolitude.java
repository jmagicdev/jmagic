package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("City of Solitude")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class CityofSolitude extends Card
{
	public static final class CityofSolitudeAbility0 extends StaticAbility
	{
		public CityofSolitudeAbility0(GameState state)
		{
			super(state, "Players can cast spells and activate abilities only during their own turns.");

			SetGenerator otherPlayers = RelativeComplement.instance(Players.instance(), OwnerOf.instance(CurrentTurn.instance()));

			SimpleEventPattern playStuff = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			playStuff.put(EventType.Parameter.PLAYER, otherPlayers);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(playStuff));
			this.addEffectPart(part);
		}
	}

	public CityofSolitude(GameState state)
	{
		super(state);

		// Players can cast spells and activate abilities only during their own
		// turns.
		this.addAbility(new CityofSolitudeAbility0(state));
	}
}
