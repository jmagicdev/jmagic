package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Jade Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class JadeMage extends Card
{
	public static final class JadeMageAbility0 extends ActivatedAbility
	{
		public JadeMageAbility0(GameState state)
		{
			super(state, "(2)(G): Put a 1/1 green Saproling creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(2)(G)"));

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Saproling creature token onto the battlefield.");
			f.setColors(Color.GREEN);
			f.setSubTypes(SubType.SAPROLING);
			this.addEffect(f.getEventFactory());
		}
	}

	public JadeMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (2)(G): Put a 1/1 green Saproling creature token onto the
		// battlefield.
		this.addAbility(new JadeMageAbility0(state));
	}
}
