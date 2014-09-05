package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Azure Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class AzureMage extends Card
{
	public static final class AzureMageAbility0 extends ActivatedAbility
	{
		public AzureMageAbility0(GameState state)
		{
			super(state, "(3)(U): Draw a card.");
			this.setManaCost(new ManaPool("(3)(U)"));
			this.addEffect(drawACard());
		}
	}

	public AzureMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (3)(U): Draw a card.
		this.addAbility(new AzureMageAbility0(state));
	}
}
