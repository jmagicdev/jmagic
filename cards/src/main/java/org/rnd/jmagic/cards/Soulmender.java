package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soulmender")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class Soulmender extends Card
{
	public static final class SoulmenderAbility0 extends ActivatedAbility
	{
		public SoulmenderAbility0(GameState state)
		{
			super(state, "(T): You gain 1 life.");
			this.costsTap = true;
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public Soulmender(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): You gain 1 life.
		this.addAbility(new SoulmenderAbility0(state));
	}
}
