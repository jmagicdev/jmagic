package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Excruciator")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("6RR")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Excruciator extends Card
{
	// Damage that would be dealt by Excruciator can't be prevented.
	public static final class Excruciating extends StaticAbility
	{
		public Excruciating(GameState state)
		{
			super(state, "Damage that would be dealt by Excruciator can't be prevented.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public Excruciator(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.addAbility(new Excruciating(state));
	}
}
