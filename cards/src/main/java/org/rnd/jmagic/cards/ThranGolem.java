package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thran Golem")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class ThranGolem extends Card
{
	public static final class ThranGolemAbility0 extends StaticAbility
	{
		public ThranGolemAbility0(GameState state)
		{
			super(state, "As long as Thran Golem is enchanted, it gets +2/+2 and has flying, first strike, and trample.");

			SetGenerator thisIsEnchanted = Intersect.instance(AttachedTo.instance(This.instance()), HasSubType.instance(SubType.AURA));
			this.canApply = Both.instance(this.canApply, thisIsEnchanted);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public ThranGolem(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// As long as Thran Golem is enchanted, it gets +2/+2 and has flying,
		// first strike, and trample.
		this.addAbility(new ThranGolemAbility0(state));
	}
}
