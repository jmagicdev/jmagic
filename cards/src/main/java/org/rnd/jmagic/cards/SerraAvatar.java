package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Serra Avatar")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("4WWW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SerraAvatar extends Card
{
	public static final class SerraAvatarAbility0 extends CharacteristicDefiningAbility
	{
		public SerraAvatarAbility0(GameState state)
		{
			super(state, "Serra Avatar's power and toughness are each equal to your life total.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator num = LifeTotalOf.instance(You.instance());
			this.addEffectPart(setPowerAndToughness(This.instance(), num, num));
		}
	}

	public SerraAvatar(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Serra Avatar's power and toughness are each equal to your life total.
		this.addAbility(new SerraAvatarAbility0(state));

		// When Serra Avatar is put into a graveyard from anywhere, shuffle it
		// into its owner's library.
		this.addAbility(new org.rnd.jmagic.abilities.LorwynIncarnationTrigger(state, this.getName()));
	}
}
