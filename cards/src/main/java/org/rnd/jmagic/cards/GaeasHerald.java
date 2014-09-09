package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gaea's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class GaeasHerald extends Card
{
	public static final class Heraldry extends StaticAbility
	{
		public Heraldry(GameState state)
		{
			super(state, "Creature spells can't be countered.");

			SimpleEventPattern counterCreature = new SimpleEventPattern(EventType.COUNTER);
			counterCreature.put(EventType.Parameter.OBJECT, HasType.instance(Type.CREATURE));

			ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(counterCreature));
			this.addEffectPart(prohibition);
		}
	}

	public GaeasHerald(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Heraldry(state));
	}
}
