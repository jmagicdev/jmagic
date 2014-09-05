package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vector Asp")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SNAKE})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VectorAsp extends Card
{
	public static final class VectorAspAbility0 extends ActivatedAbility
	{
		public VectorAspAbility0(GameState state)
		{
			super(state, "(B): Vector Asp gains infect until end of turn. ");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(createFloatingEffect("Vector Asp gains infect until end of turn.", addAbilityToObject(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Infect.class)));
		}
	}

	public VectorAsp(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (B): Vector Asp gains infect until end of turn. (It deals damage to
		// creatures in the form of -1/-1 counters and to players in the form of
		// poison counters.)
		this.addAbility(new VectorAspAbility0(state));
	}
}
