package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scroll of Avacyn")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class ScrollofAvacyn extends Card
{
	public static final class ScrollofAvacynAbility0 extends ActivatedAbility
	{
		public ScrollofAvacynAbility0(GameState state)
		{
			super(state, "(1), Sacrifice Scroll of Avacyn: Draw a card. If you control an Angel, you gain 5 life.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Scroll of Avacyn"));

			SetGenerator youControlAnAngel = Intersect.instance(HasSubType.instance(SubType.ANGEL), ControlledBy.instance(You.instance()));
			this.addEffect(drawACard());
			this.addEffect(ifThen(youControlAnAngel, gainLife(You.instance(), 5, "You gain 5 life."), "If you control an Angel, you gain 5 life."));
		}
	}

	public ScrollofAvacyn(GameState state)
	{
		super(state);

		// (1), Sacrifice Scroll of Avacyn: Draw a card. If you control an
		// Angel, you gain 5 life.
		this.addAbility(new ScrollofAvacynAbility0(state));
	}
}
