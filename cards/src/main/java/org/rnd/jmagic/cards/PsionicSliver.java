package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Psionic Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PsionicSliver extends Card
{
	@Name("\"(T): This creature deals 2 damage to target creature or player and 3 damage to itself.\"")
	public static final class PsionicSuicide extends ActivatedAbility
	{
		public PsionicSuicide(GameState state)
		{
			super(state, "(T): This creature deals 2 damage to target creature or player and 3 damage to itself.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			// TODO : Simultaneousness.
			this.addEffect(permanentDealDamage(2, targetedBy(target), "This creature deals 2 damage to target creature or player"));
			this.addEffect(permanentDealDamage(3, ABILITY_SOURCE_OF_THIS, "and 3 damage to itself."));
		}
	}

	public PsionicSliver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// All Sliver creatures have
		// "(T): This creature deals 2 damage to target creature or player and 3 damage to itself."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, PsionicSuicide.class, "All Sliver creatures have \"(T): This creature deals 2 damage to target creature or player and 3 damage to itself.\""));
	}
}
