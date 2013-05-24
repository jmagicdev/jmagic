package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Air Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class AirServant extends Card
{
	public static final class AirServantAbility1 extends ActivatedAbility
	{
		public AirServantAbility1(GameState state)
		{
			super(state, "(2)(U): Tap target creature with flying.");
			this.setManaCost(new ManaPool("(2)(U)"));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying"));
			this.addEffect(tap(target, "Tap target creature with flying."));
		}
	}

	public AirServant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (2)(U): Tap target creature with flying.
		this.addAbility(new AirServantAbility1(state));
	}
}
