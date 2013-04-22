package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Melira's Keepers")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MelirasKeepers extends Card
{
	public static final class MelirasKeepersAbility0 extends StaticAbility
	{
		public MelirasKeepersAbility0(GameState state)
		{
			super(state, "Melira's Keepers can't have counters placed on it.");

			EventPattern pattern = new CounterPlacedPattern(null, This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public MelirasKeepers(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Melira's Keepers can't have counters placed on it.
		this.addAbility(new MelirasKeepersAbility0(state));
	}
}
