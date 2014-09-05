package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Godsire")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4RGGW")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class Godsire extends Card
{
	public static final class Respawn extends ActivatedAbility
	{
		public Respawn(GameState state)
		{
			super(state, "(T): Put an 8/8 Beast creature token that's red, green, and white onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory token = new CreateTokensFactory(1, 8, 8, "Put an 8/8 Beast creature token that's red, green, and white onto the battlefield.");
			token.setColors(Color.RED, Color.GREEN, Color.WHITE);
			token.setSubTypes(SubType.BEAST);
			this.addEffect(token.getEventFactory());
		}
	}

	public Godsire(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new Respawn(state));
	}
}
