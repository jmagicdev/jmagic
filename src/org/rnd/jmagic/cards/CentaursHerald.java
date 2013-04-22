package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Centaur's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CentaursHerald extends Card
{
	public static final class CentaursHeraldAbility0 extends ActivatedAbility
	{
		public CentaursHeraldAbility0(GameState state)
		{
			super(state, "(2)(G), Sacrifice Centaur's Herald: Put a 3/3 green Centaur creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(2)(G)"));
			this.addCost(sacrificeThis("Centaur's Herald"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Centaur creature token onto the battlefield.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.CENTAUR);
			this.addEffect(factory.getEventFactory());
		}
	}

	public CentaursHerald(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (2)(G), Sacrifice Centaur's Herald: Put a 3/3 green Centaur creature
		// token onto the battlefield.
		this.addAbility(new CentaursHeraldAbility0(state));
	}
}
