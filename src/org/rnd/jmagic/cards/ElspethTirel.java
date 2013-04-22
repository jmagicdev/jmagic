package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elspeth Tirel")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.ELSPETH})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class ElspethTirel extends Card
{
	public static final class ElspethTirelAbility0 extends LoyaltyAbility
	{
		public ElspethTirelAbility0(GameState state)
		{
			super(state, +2, "You gain 1 life for each creature you control.");
			this.addEffect(gainLife(You.instance(), Count.instance(CREATURES_YOU_CONTROL), "You gain 1 life for each creature you control."));
		}
	}

	public static final class ElspethTirelAbility1 extends LoyaltyAbility
	{
		public ElspethTirelAbility1(GameState state)
		{
			super(state, -2, "Put three 1/1 white Soldier creature tokens onto the battlefield.");

			CreateTokensFactory factory = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Soldier creature tokens onto the battlefield.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.SOLDIER);
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class ElspethTirelAbility2 extends LoyaltyAbility
	{
		public ElspethTirelAbility2(GameState state)
		{
			super(state, -5, "Destroy all other permanents except for lands and tokens.");
			SetGenerator destroy = RelativeComplement.instance(Permanents.instance(), Union.instance(ABILITY_SOURCE_OF_THIS, LandPermanents.instance(), Tokens.instance()));
			this.addEffect(destroy(destroy, "Destroy all other permanents except for lands and tokens."));
		}
	}

	public ElspethTirel(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +2: You gain 1 life for each creature you control.
		this.addAbility(new ElspethTirelAbility0(state));

		// -2: Put three 1/1 white Soldier creature tokens onto the battlefield.
		this.addAbility(new ElspethTirelAbility1(state));

		// -5: Destroy all other permanents except for lands and tokens.
		this.addAbility(new ElspethTirelAbility2(state));
	}
}
