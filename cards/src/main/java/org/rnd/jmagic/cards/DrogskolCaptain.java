package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drogskol Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.SOLDIER})
@ManaCost("1WU")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class DrogskolCaptain extends Card
{
	public DrogskolCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other Spirit creatures you control get +1/+1 and have hexproof. (They
		// can't be the targets of spells or abilities your opponents control.)
		SetGenerator otherSpiritCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.SPIRIT)), This.instance());
		SetGenerator yourSpirits = Intersect.instance(otherSpiritCreatures, ControlledBy.instance(You.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, yourSpirits, "Other Spirit creatures you control", +1, +1, org.rnd.jmagic.abilities.keywords.Hexproof.class, true));
	}
}
