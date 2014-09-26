package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Pheres-Band Raiders")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class PheresBandRaiders extends Card
{
	public static final class PheresBandRaidersAbility0 extends EventTriggeredAbility
	{
		public PheresBandRaidersAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Pheres-Band Raiders becomes untapped, you may pay (2)(G). If you do, put a 3/3 green Centaur enchantment creature token onto the battlefield.");
			this.addPattern(inspired());

			EventFactory mayPay = youMayPay("(2)(G)");

			CreateTokensFactory centaur = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Centaur enchantment creature token onto the battlefield.");
			centaur.setColors(Color.GREEN);
			centaur.setSubTypes(SubType.CENTAUR);
			centaur.setEnchantment();
			this.addEffect(ifThen(mayPay, centaur.getEventFactory(), "You may pay (2)(G). If you do, put a 3/3 green Centaur enchantment creature token onto the battlefield."));
		}
	}

	public PheresBandRaiders(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Inspired \u2014 Whenever Pheres-Band Raiders becomes untapped, you
		// may pay (2)(G). If you do, put a 3/3 green Centaur enchantment
		// creature token onto the battlefield.
		this.addAbility(new PheresBandRaidersAbility0(state));
	}
}
