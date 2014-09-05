package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Garruk, Primal Hunter")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GARRUK})
@ManaCost("2GGG")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Magic2012.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class GarrukPrimalHunter extends Card
{
	public static final class GarrukPrimalHunterAbility0 extends LoyaltyAbility
	{
		public GarrukPrimalHunterAbility0(GameState state)
		{
			super(state, +1, "Put a 3/3 green Beast creature token onto the battlefield.");

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Beast creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.BEAST);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class GarrukPrimalHunterAbility1 extends LoyaltyAbility
	{
		public GarrukPrimalHunterAbility1(GameState state)
		{
			super(state, -3, "Draw cards equal to the greatest power among creatures you control.");

			SetGenerator number = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
			this.addEffect(drawCards(You.instance(), number, "Draw cards equal to the greatest power among creatures you control."));
		}
	}

	public static final class GarrukPrimalHunterAbility2 extends LoyaltyAbility
	{
		public GarrukPrimalHunterAbility2(GameState state)
		{
			super(state, -6, "Put a 6/6 green Wurm creature token onto the battlefield for each land you control.");

			SetGenerator number = Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));

			CreateTokensFactory f = new CreateTokensFactory(number, "Put a 6/6 green Wurm creature token onto the battlefield for each land you control.");
			f.addCreature(6, 6);
			f.setColors(Color.GREEN);
			f.setSubTypes(SubType.WURM);
			this.addEffect(f.getEventFactory());
		}
	}

	public GarrukPrimalHunter(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Put a 3/3 green Beast creature token onto the battlefield.
		this.addAbility(new GarrukPrimalHunterAbility0(state));

		// -3: Draw cards equal to the greatest power among creatures you
		// control.
		this.addAbility(new GarrukPrimalHunterAbility1(state));

		// -6: Put a 6/6 green Wurm creature token onto the battlefield for each
		// land you control.
		this.addAbility(new GarrukPrimalHunterAbility2(state));
	}
}
