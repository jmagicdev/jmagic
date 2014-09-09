package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duct Crawler")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class DuctCrawler extends Card
{
	public static final class Evade extends ActivatedAbility
	{
		public Evade(GameState state)
		{
			super(state, "(1)(R): Target creature can't block Duct Crawler this turn.");

			this.setManaCost(new ManaPool("1R"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			SetGenerator restriction = Intersect.instance(Blocking.instance(ABILITY_SOURCE_OF_THIS), targetedBy(target));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));

			this.addEffect(createFloatingEffect("Target creature can't block Duct Crawler this turn", part));
		}
	}

	public DuctCrawler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Evade(state));
	}
}
