package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Crimson Muckwader")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class CrimsonMuckwader extends Card
{
	public static final class CrimsonMuckwaderAbility0 extends StaticAbility
	{
		public CrimsonMuckwaderAbility0(GameState state)
		{
			super(state, "Crimson Muckwader gets +1/+1 as long as you control a Swamp.");
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));
			this.canApply = Both.instance(this.canApply, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.SWAMP)));
		}
	}

	public static final class CrimsonMuckwaderAbility1 extends ActivatedAbility
	{
		public CrimsonMuckwaderAbility1(GameState state)
		{
			super(state, "(2)(B): Regenerate Crimson Muckwader.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Crimson Muckwader."));
		}
	}

	public CrimsonMuckwader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Crimson Muckwader gets +1/+1 as long as you control a Swamp.
		this.addAbility(new CrimsonMuckwaderAbility0(state));

		// (2)(B): Regenerate Crimson Muckwader. (The next time this creature
		// would be destroyed this turn, it isn't. Instead tap it, remove all
		// damage from it, and remove it from combat.)
		this.addAbility(new CrimsonMuckwaderAbility1(state));
	}
}
