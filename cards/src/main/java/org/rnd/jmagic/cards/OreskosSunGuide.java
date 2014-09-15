package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oreskos Sun Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.MONK})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class OreskosSunGuide extends Card
{
	public static final class OreskosSunGuideAbility0 extends EventTriggeredAbility
	{
		public OreskosSunGuideAbility0(GameState state)
		{
			super(state, "Whenever Oreskos Sun Guide becomes untapped, you gain 2 life.");
			this.addPattern(inspired());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public OreskosSunGuide(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Inspired \u2014 Whenever Oreskos Sun Guide becomes untapped, you gain
		// 2 life.
		this.addAbility(new OreskosSunGuideAbility0(state));
	}
}
