package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Chieftain")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class GoblinChieftain extends Card
{
	public GoblinChieftain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Other Goblin creatures you control get +1/+1 and have haste.
		SetGenerator otherGoblinCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.GOBLIN)), This.instance());
		SetGenerator yourGoblins = Intersect.instance(otherGoblinCreatures, ControlledBy.instance(You.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, yourGoblins, "Other Goblin creatures you control", +1, +1, org.rnd.jmagic.abilities.keywords.Haste.class, true));
	}
}
