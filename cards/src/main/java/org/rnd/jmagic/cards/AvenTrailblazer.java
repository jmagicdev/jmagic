package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aven Trailblazer")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.BIRD})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class AvenTrailblazer extends Card
{
	public static final class DomainToughness extends CharacteristicDefiningAbility
	{
		public DomainToughness(GameState state)
		{
			super(state, "Domain \u2014 Aven Trailblazer's toughness is equal to the number of basic land types among lands you control.", Characteristics.Characteristic.TOUGHNESS);

			ContinuousEffect.Part part = setPowerAndToughness(This.instance(), null, Domain.instance(You.instance()));
			this.addEffectPart(part);
		}
	}

	public AvenTrailblazer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new DomainToughness(state));
	}
}
