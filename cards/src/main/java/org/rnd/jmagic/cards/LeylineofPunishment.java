package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Leyline of Punishment")
@Types({Type.ENCHANTMENT})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class LeylineofPunishment extends Card
{
	public static final class ProhibitLifeGain extends StaticAbility
	{
		public ProhibitLifeGain(GameState state)
		{
			super(state, "Players can't gain life.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new SimpleEventPattern(EventType.GAIN_LIFE)));
			this.addEffectPart(part);
		}
	}

	public static final class ProhibitPreventingDamage extends StaticAbility
	{
		public ProhibitPreventingDamage(GameState state)
		{
			super(state, "Damage can't be prevented.");

			this.addEffectPart(new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED));
		}
	}

	public LeylineofPunishment(GameState state)
	{
		super(state);

		// If Leyline of Punishment is in your opening hand, you may begin the
		// game with it on the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.LeylineAbility(state, "Leyline of Punishment"));

		// Players can't gain life.
		this.addAbility(new ProhibitLifeGain(state));

		// Damage can't be prevented.
		this.addAbility(new ProhibitPreventingDamage(state));
	}
}
