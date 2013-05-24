package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gnathosaur")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Gnathosaur extends Card
{
	public static final class GnathosaurAbility0 extends ActivatedAbility
	{
		public GnathosaurAbility0(GameState state)
		{
			super(state, "Sacrifice an artifact: Gnathosaur gains trample until end of turn.");
			// Sacrifice an artifact
			this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT), "Sacrifice an artifact"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Trample.class, "Gnathosaur gains trample until end of turn."));
		}
	}

	public Gnathosaur(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Sacrifice an artifact: Gnathosaur gains trample until end of turn.
		this.addAbility(new GnathosaurAbility0(state));
	}
}
