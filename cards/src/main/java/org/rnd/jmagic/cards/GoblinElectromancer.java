package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Electromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.GOBLIN})
@ManaCost("UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class GoblinElectromancer extends Card
{
	public GoblinElectromancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Instant and sorcery spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasType.instance(Type.INSTANT, Type.SORCERY), "(1)", "Instant and sorcery spells you cast cost (1) less to cast."));
	}
}
