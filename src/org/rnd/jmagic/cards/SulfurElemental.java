package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sulfur Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class SulfurElemental extends Card
{
	public SulfurElemental(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Split second (As long as this spell is on the stack, players can't
		// cast spells or activate abilities that aren't mana abilities.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.SplitSecond(state));

		// White creatures get +1/-1.
		SetGenerator whiteCreatures = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, whiteCreatures, "White creatures", +1, -1, true));
	}
}
