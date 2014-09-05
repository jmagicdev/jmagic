package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Flesh-Eater Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class FleshEaterImp extends Card
{
	public static final class FleshEaterImpAbility2 extends ActivatedAbility
	{
		public FleshEaterImpAbility2(GameState state)
		{
			super(state, "Sacrifice a creature: Flesh-Eater Imp gets +1/+1 until end of turn.");
			this.addCost(sacrificeACreature());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Flesh-Eater Imp gets +1/+1 until end of turn."));
		}
	}

	public FleshEaterImp(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Sacrifice a creature: Flesh-Eater Imp gets +1/+1 until end of turn.
		this.addAbility(new FleshEaterImpAbility2(state));
	}
}
