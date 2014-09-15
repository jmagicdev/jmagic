package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Felhide Brawler")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class FelhideBrawler extends Card
{
	public static final class FelhideBrawlerAbility0 extends StaticAbility
	{
		public FelhideBrawlerAbility0(GameState state)
		{
			super(state, "Felhide Brawler can't block unless you control another Minotaur.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Blocking.instance())));
			this.addEffectPart(part);

			SetGenerator anotherMinotaur = RelativeComplement.instance(HasSubType.instance(SubType.MINOTAUR), This.instance());
			SetGenerator youControlMinotaur = Intersect.instance(ControlledBy.instance(You.instance()), anotherMinotaur);
			this.canApply = Both.instance(this.canApply, Not.instance(youControlMinotaur));
		}
	}

	public FelhideBrawler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Felhide Brawler can't block unless you control another Minotaur.
		this.addAbility(new FelhideBrawlerAbility0(state));
	}
}
