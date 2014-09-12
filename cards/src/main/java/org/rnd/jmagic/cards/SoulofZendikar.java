package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Soul of Zendikar")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class SoulofZendikar extends Card
{
	public static final class SoulofZendikarAbility1 extends ActivatedAbility
	{
		public SoulofZendikarAbility1(GameState state)
		{
			super(state, "(3)(G)(G): Put a 3/3 green Beast creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(3)(G)(G)"));

			CreateTokensFactory beast = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Beast creature token onto the battlefield.");
			beast.setColors(Color.GREEN);
			beast.setSubTypes(SubType.BEAST);
			this.addEffect(beast.getEventFactory());
		}
	}

	public static final class SoulofZendikarAbility2 extends ActivatedAbility
	{
		public SoulofZendikarAbility2(GameState state)
		{
			super(state, "(3)(G)(G), Exile Soul of Zendikar from your graveyard: Put a 3/3 green Beast creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(3)(G)(G)"));
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Soul of Zendikar from your graveyard"));
			this.activateOnlyFromGraveyard();

			CreateTokensFactory beast = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Beast creature token onto the battlefield.");
			beast.setColors(Color.GREEN);
			beast.setSubTypes(SubType.BEAST);
			this.addEffect(beast.getEventFactory());
		}
	}

	public SoulofZendikar(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// (3)(G)(G): Put a 3/3 green Beast creature token onto the battlefield.
		this.addAbility(new SoulofZendikarAbility1(state));

		// (3)(G)(G), Exile Soul of Zendikar from your graveyard: Put a 3/3
		// green Beast creature token onto the battlefield.
		this.addAbility(new SoulofZendikarAbility2(state));
	}
}
