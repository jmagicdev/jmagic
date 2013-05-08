package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Gargoyle Castle")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({})
public final class GargoyleCastle extends Card
{
	public static final class MakeGargoyle extends ActivatedAbility
	{
		public MakeGargoyle(GameState state)
		{
			super(state, "(5), (T), Sacrifice Gargoyle Castle: Put a 3/4 colorless Gargoyle artifact creature token with flying onto the battlefield.");

			// {5}, {T}, Sacrifice Gargoyle Castle:
			this.setManaCost(new ManaPool("5"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Gargoyle Castle"));

			// Put a 3/4 colorless Gargoyle artifact creature token with flying
			// onto the battlefield.
			CreateTokensFactory token = new CreateTokensFactory(1, 3, 4, "Put a 3/4 colorless Gargoyle artifact creature token with flying onto the battlefield.");
			token.setSubTypes(SubType.GARGOYLE);
			token.setArtifact();
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());

		}
	}

	public GargoyleCastle(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new MakeGargoyle(state));
	}
}
