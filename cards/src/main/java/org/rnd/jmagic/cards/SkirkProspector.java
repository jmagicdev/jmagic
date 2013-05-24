package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skirk Prospector")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SkirkProspector extends Card
{
	public static final class SkirkProspectorAbility0 extends ActivatedAbility
	{
		public SkirkProspectorAbility0(GameState state)
		{
			super(state, "Sacrifice a Goblin: Add (R) to your mana pool.");
			// Sacrifice a Goblin

			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.GOBLIN), "Sacrifice a Goblin"));

			this.addEffect(addManaToYourManaPoolFromAbility("(R)"));
		}
	}

	public SkirkProspector(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice a Goblin: Add (R) to your mana pool.
		this.addAbility(new SkirkProspectorAbility0(state));
	}
}
