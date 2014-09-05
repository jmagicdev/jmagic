package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ajani Goldmane")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.AJANI})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Magic2010.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Lorwyn.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AjaniGoldmane extends Card
{
	// +1:
	public static final class GainLife extends LoyaltyAbility
	{
		public GainLife(GameState state)
		{
			super(state, +1, "You gain 2 life.");

			// You gain 2 life.
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	// -1:
	public static final class PutCounters extends LoyaltyAbility
	{
		public PutCounters(GameState state)
		{
			super(state, -1, "Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.");

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator creaturesYouControl = Intersect.instance(CreaturePermanents.instance(), youControl);

			// Put a +1/+1 counter on each creature you control.
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, creaturesYouControl, "Put a +1/+1 counter on each creature you control."));

			// Those creatures gain vigilance until end of turn.
			this.addEffect(addAbilityUntilEndOfTurn(creaturesYouControl, org.rnd.jmagic.abilities.keywords.Vigilance.class, "Those creatures gain vigilance until end of turn."));
		}
	}

	public static final class SetPowerAndToughness extends CharacteristicDefiningAbility
	{
		public SetPowerAndToughness(GameState state)
		{
			super(state, "This creature's power and toughness are each equal to your life total.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator life = LifeTotalOf.instance(You.instance());

			ContinuousEffect.Part part = setPowerAndToughness(This.instance(), life, life);

			this.addEffectPart(part);
		}
	}

	public static final class MakeAvatar extends LoyaltyAbility
	{
		// -6:
		public MakeAvatar(GameState state)
		{
			super(state, -6, "Put a white Avatar creature token onto the battlefield. It has \"This creature's power and toughness are each equal to your life total.\"");

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 0, "Put a white Avatar creature token onto the battlefield.");
			token.addAbility(SetPowerAndToughness.class);
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.AVATAR);
			this.addEffect(token.getEventFactory());
		}
	}

	public AjaniGoldmane(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		this.addAbility(new GainLife(state));
		this.addAbility(new PutCounters(state));
		this.addAbility(new MakeAvatar(state));
	}
}
