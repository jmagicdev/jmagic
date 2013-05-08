package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Malignus")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.ELEMENTAL})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class Malignus extends Card
{
	public static final class MalignusAbility0 extends StaticAbility
	{
		public MalignusAbility0(GameState state)
		{
			super(state, "Malignus's power and toughness are each equal to half the highest life total among your opponents, rounded up.");

			SetGenerator number = DivideBy.instance(Maximum.instance(LifeTotalOf.instance(OpponentsOf.instance(You.instance()))), numberGenerator(2), true);
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class MalignusAbility1 extends StaticAbility
	{
		public MalignusAbility1(GameState state)
		{
			super(state, "Damage that would be dealt by Malignus can't be prevented.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public Malignus(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Malignus's power and toughness are each equal to half the highest
		// life total among your opponents, rounded up.
		this.addAbility(new MalignusAbility0(state));

		// Damage that would be dealt by Malignus can't be prevented.
		this.addAbility(new MalignusAbility1(state));
	}
}
