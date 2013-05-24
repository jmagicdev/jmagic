package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merfolk Seastalkers")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.MERFOLK})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MerfolkSeastalkers extends Card
{
	public static final class TapStuff extends ActivatedAbility
	{
		public TapStuff(GameState state)
		{
			super(state, "(2)(U): Tap target creature without flying.");
			this.setManaCost(new ManaPool("2U"));
			Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature without flying");
			this.addEffect(tap(targetedBy(target), "Tap target creature without flying."));
		}
	}

	public MerfolkSeastalkers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Islandwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// (2)(U): Tap target creature without flying.
		this.addAbility(new TapStuff(state));
	}
}
