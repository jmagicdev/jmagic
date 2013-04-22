package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kessig Wolf Run")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class KessigWolfRun extends Card
{
	public static final class KessigWolfRunAbility1 extends ActivatedAbility
	{
		public KessigWolfRunAbility1(GameState state)
		{
			super(state, "(X)(R)(G), (T): Target creature gets +X/+0 and gains trample until end of turn.");
			this.setManaCost(new ManaPool("(X)(R)(G)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature gets +X/+0 and gains trample until end of turn.", modifyPowerAndToughness(target, ValueOfX.instance(This.instance()), numberGenerator(0)), addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Trample.class)));
		}
	}

	public KessigWolfRun(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (X)(R)(G), (T): Target creature gets +X/+0 and gains trample until
		// end of turn.
		this.addAbility(new KessigWolfRunAbility1(state));
	}
}
