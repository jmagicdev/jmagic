package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Moorland Haunt")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class MoorlandHaunt extends Card
{
	public static final class MoorlandHauntAbility1 extends ActivatedAbility
	{
		public MoorlandHauntAbility1(GameState state)
		{
			super(state, "(W)(U), (T), Exile a creature card from your graveyard: Put a 1/1 white Spirit creature token with flying onto the battlefield.");
			this.setManaCost(new ManaPool("(W)(U)"));
			this.costsTap = true;
			this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "Exile a creature card from your graveyard"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.SPIRIT);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public MoorlandHaunt(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (W)(U), (T), Exile a creature card from your graveyard: Put a 1/1
		// white Spirit creature token with flying onto the battlefield.
		this.addAbility(new MoorlandHauntAbility1(state));
	}
}
