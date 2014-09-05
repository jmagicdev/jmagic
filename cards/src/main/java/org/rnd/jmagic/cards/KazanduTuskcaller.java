package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kazandu Tuskcaller")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class KazanduTuskcaller extends Card
{
	public static final class KazanduTuskcallerAbility3 extends ActivatedAbility
	{
		public KazanduTuskcallerAbility3(GameState state)
		{
			super(state, "(T): Put a 3/3 green Elephant creature token onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Elephant creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.ELEPHANT);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class KazanduTuskcallerAbility6 extends ActivatedAbility
	{
		public KazanduTuskcallerAbility6(GameState state)
		{
			super(state, "(T): Put two 3/3 green Elephant creature tokens onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory tokens = new CreateTokensFactory(2, 3, 3, "Put two 3/3 green Elephant creature tokens onto the battlefield.");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.ELEPHANT);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public KazanduTuskcaller(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Level up (1)(G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)(G)"));

		// LEVEL 2-5
		// 1/1
		// (T): Put a 3/3 green Elephant creature token onto the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 2, 5, 1, 1, "\"(T): Put a 3/3 green Elephant creature token onto the battlefield.\"", KazanduTuskcallerAbility3.class));

		// LEVEL 6+
		// 1/1
		// (T): Put two 3/3 green Elephant creature tokens onto the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 6, 1, 1, "\"(T): Put two 3/3 green Elephant creature tokens onto the battlefield.\"", KazanduTuskcallerAbility6.class));
	}
}
