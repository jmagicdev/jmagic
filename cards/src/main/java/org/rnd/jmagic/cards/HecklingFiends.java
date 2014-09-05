package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Heckling Fiends")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class HecklingFiends extends Card
{
	public static final class HecklingFiendsAbility0 extends ActivatedAbility
	{
		public HecklingFiendsAbility0(GameState state)
		{
			super(state, "(2)(R): Target creature attacks this turn if able.");
			this.setManaCost(new ManaPool("(2)(R)"));

			// Target creature an opponent controls attacks you this turn if
			// able.
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);

			this.addEffect(createFloatingEffect("Target creature attacks this turn if able.", part));
		}
	}

	public HecklingFiends(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(R): Target creature attacks this turn if able.
		this.addAbility(new HecklingFiendsAbility0(state));
	}
}
