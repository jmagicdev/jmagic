package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Knight of Dusk")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class KnightofDusk extends Card
{
	public static final class FirstBlood extends ActivatedAbility
	{
		public FirstBlood(GameState state)
		{
			super(state, "(B)(B): Destroy target creature blocking Knight of Dusk.");

			this.setManaCost(new ManaPool("BB"));

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), Blocking.instance(ABILITY_SOURCE_OF_THIS)), "target creature blocking Knight of Dusk");
			this.addEffect(destroy(targetedBy(target), "Destroy target creature blocking Knight of Dusk."));
		}
	}

	public KnightofDusk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new FirstBlood(state));
	}
}
